package com.example.ekachart.eyeproject;
import android.app.Activity;
import android.content.Context;

import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;



import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sunyao extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "OCVSample::Activity";
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
    public static final int JAVA_DETECTOR = 0;

    private Mat mRgba;
    private Mat mGray;
    private Mat mRgbaT;
    private Mat mGrayT;
    // matrix for zooming
    private Mat mZoomWindow;
    private Mat mZoomWindow2;

    private File mCascadeFile;
    private CascadeClassifier mJavaDetector;
    private CascadeClassifier mJavaDetectorEye;


    private int mDetectorType = JAVA_DETECTOR;
    private String[] mDetectorName;

    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;

    private CameraBridgeViewBase mOpenCvCameraView;

    private SeekBar mMethodSeekbar;
    private TextView mValue;

    double xCenter = -1;
    double yCenter = -1;

    private Button switchCamera;
    private int camIndex = 1;

    private Camera mCamera;


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");


                    try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(
                                R.raw.haarcascade_frontalface_alt);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir,
                                "haarcascade_frontalface_alt.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        // --------------------------------- load left eye
                        // classificator -----------------------------------
                        InputStream iser = getResources().openRawResource(
                                R.raw.haarcascade_eye_tree_eyeglasses);
                        File cascadeDirER = getDir("cascade",
                                Context.MODE_PRIVATE);
                        File cascadeFileER = new File(cascadeDirER,
                                "haarcascade_eye_tree_eyeglasses.xml");
                        FileOutputStream oser = new FileOutputStream(cascadeFileER);

                        byte[] bufferER = new byte[4096];
                        int bytesReadER;
                        while ((bytesReadER = iser.read(bufferER)) != -1) {
                            oser.write(bufferER, 0, bytesReadER);
                        }
                        iser.close();
                        oser.close();

                        mJavaDetector = new CascadeClassifier(
                                mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from "
                                    + mCascadeFile.getAbsolutePath());

                        mJavaDetectorEye = new CascadeClassifier(
                                cascadeFileER.getAbsolutePath());
                        if (mJavaDetectorEye.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetectorEye = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from "
                                    + mCascadeFile.getAbsolutePath());



                        cascadeDir.delete();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }
                    mOpenCvCameraView.setCameraIndex(camIndex);
                    mOpenCvCameraView.enableFpsMeter();
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public Sunyao() {
        mDetectorName = new String[2];
        mDetectorName[JAVA_DETECTOR] = "Java";


        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        setContentView(R.layout.sunyao);



        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.fd_activity_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);


    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this,mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        Log.e("width:height",width+":"+height);
        mGray = new Mat();
        mGrayT = new Mat();

        mRgba = new Mat();
        mRgbaT = new Mat();
    }

    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
        while( mZoomWindow == null);
        mZoomWindow.release();
        mZoomWindow2.release();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();
        //แก้
        if( getResources().getConfiguration().orientation  == 1  ) {
            Core.transpose(mGray, mGrayT);
            Core.flip(mGrayT, mGrayT, -1);
            Imgproc.resize(mGrayT, mGrayT, mGray.size());
            //Log.e("mGray width:height",mGray.size().width+":"+mGray.size().height);
            Core.transpose(mRgba, mRgbaT);
            Core.flip(mRgbaT, mRgbaT, -1);
            Imgproc.resize(mRgbaT, mRgbaT, mRgba.size());
        }
        else{
            Core.transpose(mGray, mGrayT);
            Core.flip(mGray, mGrayT, 1);
            Imgproc.resize(mGrayT, mGrayT, mGray.size());

            Core.transpose(mRgba, mRgbaT);
            Core.flip(mRgba, mRgbaT, 1);
            Imgproc.resize(mRgbaT, mRgbaT, mRgba.size());
        }

        if (mAbsoluteFaceSize == 0) {
            int height = mGray.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
        }

        if (mZoomWindow == null || mZoomWindow2 == null)
            CreateAuxiliaryMats();
        MatOfRect faces = new MatOfRect();

        if (mJavaDetector != null)
            mJavaDetector.detectMultiScale(mGrayT, faces, 1.1, 2,
                    0, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                    new Size(mAbsoluteFaceSize, mAbsoluteFaceSize),
                    new Size());
        //mJavaDetector.de

        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++) {
            Core.rectangle(mRgbaT, facesArray[i].tl(), facesArray[i].br(),FACE_RECT_COLOR, 3);
            xCenter = facesArray[i].x + facesArray[i].width*.5;
            yCenter = facesArray[i].y + facesArray[i].height*.5;
            Point center = new Point(xCenter, yCenter);

            //Core.circle(mRgba, center, 10, new Scalar(255, 0, 0, 255), 3);

            //Core.putText(mRgba, "[" + center.x + "," + center.y + "]",new Point(center.x + 20, center.y + 20),Core.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(255, 255, 255,255));
            /*
            Rect r = facesArray[i];
            // compute the eye area
            Rect eyearea = new Rect(r.x + r.width / 8,
                    (int) (r.y + (r.height / 4.5)), r.width - 2 * r.width / 8,
                    (int) (r.height / 3.0));
            // split it
            Rect eyearea_right = new Rect(r.x + r.width / 16,
                    (int) (r.y + (r.height / 4.5)),
                    (r.width - 2 * r.width / 16) / 2, (int) (r.height / 3.0));
            Rect eyearea_left = new Rect(r.x + r.width / 16
                    + (r.width - 2 * r.width / 16) / 2,
                    (int) (r.y + (r.height / 4.5)),
                    (r.width - 2 * r.width / 16) / 2, (int) (r.height / 3.0));
            // draw the area - mGray is working grayscale mat, if you want to
            // see area in rgb preview, change mGray to mRgba
            Core.rectangle(mRgba, eyearea_left.tl(), eyearea_left.br(),
                    new Scalar(255, 0, 0, 255), 2);
            Core.rectangle(mRgba, eyearea_right.tl(), eyearea_right.br(),
                    new Scalar(255, 0, 0, 255), 2);

            if (learn_frames < 5) {
                teplateR = get_template(mJavaDetectorEye, eyearea_right, 24);
                teplateL = get_template(mJavaDetectorEye, eyearea_left, 24);
                learn_frames++;
            } else {
                // Learning finished, use the new templates for template
                // matching
                match_eye(eyearea_right, teplateR, method);
                match_eye(eyearea_left, teplateL, method);

            }
            */
            MatOfRect eyes = new MatOfRect();


            if (mJavaDetectorEye != null)
                mJavaDetectorEye.detectMultiScale(mGrayT, eyes, 1.1, 2,0, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                        new Size(mAbsoluteFaceSize, mAbsoluteFaceSize),
                        new Size());
            Rect[] eyeArray = eyes.toArray();
            Log.d("Eye Array : ",eyeArray.length+"");
            for(int j=0;j<eyeArray.length;j++){
                Point centerEye = new Point(0 + eyeArray[j].x + eyeArray[j].width*0.5, 0 + eyeArray[j].y + eyeArray[j].height*0.5);
                //int radius = cvRound( (eyes[j].width + eyes[j].height)*0.25 );
                Core.circle(mRgbaT, centerEye, 10, new Scalar(255, 0, 0), 4,8,0);
            }


            // cut eye areas and put them to zoom windows
            //Imgproc.resize(mRgba.submat(eyearea_left), mZoomWindow2,
                    //mZoomWindow2.size());
            //Imgproc.resize(mRgba.submat(eyearea_right), mZoomWindow,
                    //mZoomWindow.size());


        }

        //return mRgba;


        return mRgbaT;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemFace50 = menu.add("Face size 50%");
        mItemFace40 = menu.add("Face size 40%");
        mItemFace30 = menu.add("Face size 30%");
        mItemFace20 = menu.add("Face size 20%");
        mItemType = menu.add(mDetectorName[mDetectorType]);
        return true;
    }*/

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item == mItemFace50)
            setMinFaceSize(0.5f);
        else if (item == mItemFace40)
            setMinFaceSize(0.4f);
        else if (item == mItemFace30)
            setMinFaceSize(0.3f);
        else if (item == mItemFace20)
            setMinFaceSize(0.2f);
        else if (item == mItemType) {
            int tmpDetectorType = (mDetectorType + 1) % mDetectorName.length;
            item.setTitle(mDetectorName[tmpDetectorType]);
        }
        return true;
    }*/

    private void setMinFaceSize(float faceSize) {
        mRelativeFaceSize = faceSize;
        mAbsoluteFaceSize = 0;
    }




    private void CreateAuxiliaryMats() {
        if (mGray.empty())
            return;
        mRgba.t();
        int rows = mGray.rows();
        int cols = mGray.cols();

        if (mZoomWindow == null) {
            mZoomWindow = mRgba.submat(rows / 2 + rows / 10, rows, cols / 2
                    + cols / 10, cols);
            mZoomWindow2 = mRgba.submat(0, rows / 2 - rows / 10, cols / 2
                    + cols / 10, cols);
        }
    }

    private int getWidthOfScreen(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return displaymetrics.widthPixels;
    }

    private int getHeightOfScreen(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return displaymetrics.heightPixels;
    }



}