package com.example.ekachart.eyeproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ekachart on 31/12/2557.
 */
public class bodsee2 extends Activity{
    private Button check,back,next,home;
    private Button[] input;
    private TextView test;
    private ImageButton delete;
    private String answer;
    private ImageView pic;
    private int[] ans = new int[]{0,0,29, 8, 16, 7, 74, 45, 15, 9, 97, 5, 3, 73};
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodsee2);
        init();
        i=2;

        while(i<=3) {

            if (i == 2) {
                pic.setImageResource(R.drawable.image2);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), bodsee.class);
                        startActivity(i);
                    }
                });

                checkans(ans[i]);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i = i + 1;
                        //Toast.makeText(getApplicationContext(),Integer.toString(i),Toast.LENGTH_SHORT).show();

                    }
                });
            }


            if (i == 3) {
                Toast.makeText(getApplicationContext(), "three", Toast.LENGTH_SHORT).show();
                pic.setImageResource(R.drawable.image3);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i = i - 1;
                    }
                });

                checkans(ans[i]);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i = i + 1;
                    }
                });
            }

            button();
        }

    }

    private void init() {
        input = new Button[10];
        input[0] = (Button)findViewById(R.id.b0);
        input[1] = (Button)findViewById(R.id.b1);
        input[2] = (Button)findViewById(R.id.b2);
        input[3] = (Button)findViewById(R.id.b3);
        input[4] = (Button)findViewById(R.id.b4);
        input[5] = (Button)findViewById(R.id.b5);
        input[6] = (Button)findViewById(R.id.b6);
        input[7] = (Button)findViewById(R.id.b7);
        input[8] = (Button)findViewById(R.id.b8);
        input[9] = (Button)findViewById(R.id.b9);
        test = (TextView)findViewById(R.id.num);
        check = (Button)findViewById(R.id.check);
        back = (Button)findViewById(R.id.back);
        next = (Button)findViewById(R.id.next);
        home = (Button)findViewById(R.id.home);
        delete = (ImageButton)findViewById(R.id.delete);
        pic = (ImageView) findViewById(R.id.imageView);

    }//end init

    public static int getResourceByFilename(Context context, String filename) {
        return context.getResources().getIdentifier(filename, "drawable", context.getPackageName());
    }

    private void button(){


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),First.class);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int len = test.getText().length();
                if (len > 0)
                    test.setText(test.getText().toString().substring(0, len - 1));
            }
        });

        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                test.setText("");
                return false;
            }
        });

        input[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[0].getText());
            }
        });

        input[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[1].getText());
            }
        });

        input[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[2].getText());
            }
        });

        input[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[3].getText());
            }
        });

        input[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[4].getText());
            }
        });

        input[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[5].getText());
            }
        });

        input[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[6].getText());
            }
        });

        input[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[7].getText());
            }
        });

        input[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[8].getText());
            }
        });

        input[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString() + input[9].getText());
            }
        });
    }//end button

    private void checkans(final int j){
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(bodsee2.this);
                builder.setTitle("ตรวจสอบ");
                if(test.getText().toString().equals(Integer.toString(j)))
                    answer = "Yes";
                else
                    answer = "No";
                builder.setMessage(answer);
                builder.setNegativeButton("ตกลง", null);
                builder.create();
                builder.show();
            }
        });
    }//end checkans

}//end class
