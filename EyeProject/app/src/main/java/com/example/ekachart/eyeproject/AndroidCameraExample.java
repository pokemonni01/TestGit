package com.example.ekachart.eyeproject;



import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.opencv.core.Mat;

public class AndroidCameraExample extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private static final String TAG = "AndroidCameraExample";
    private TextView tvPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_camera);
        tvPosition = (TextView) findViewById(R.id.position);

        // Create an instance of Camera

        mCamera = getCameraInstance();

        mCamera.setDisplayOrientation(90);



        mPreview = new CameraPreview(this, mCamera);
        final FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);


        preview.addView(mPreview);

        mCamera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                if (faces.length > 0) {
                    Log.d("FaceDetection", "face detected: " + faces.length +
                            " Face 1 Location X: " + faces[0].rect.left +
                            "Y: " + faces[0].rect.right);


                    int face[] = { faces[0].rect.left,faces[0].rect.top,faces[0].rect.right,faces[0].rect.bottom };
                    //int face[] = { faces[0].leftEye.x,faces[0].leftEye.y,faces[0].rightEye.x,faces[0].rightEye.y };
                    tvPosition.setText("Left : "+face[0]+"Top : "+face[1]+"Right : "+face[2]+"Bottom : "+face[3]);
                    mPreview.drawFaceAndEye(face);
                    preview.invalidate();
                    mPreview.invalidate();
                }



            }
        });


    }

    public static Camera getCameraInstance(){
        Camera c = null;

        try {
            c = Camera.open(1); // attempt to get a Camera instance

        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.e(TAG,"Camera Exception");
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera != null){
           try {
               mCamera.reconnect();        // release the camera for other applications
           }
           catch (Exception e){}
            mCamera = null;
        }
    }
}
