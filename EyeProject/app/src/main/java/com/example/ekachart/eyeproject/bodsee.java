package com.example.ekachart.eyeproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by Ekachart on 28/12/2557.
 */
public class bodsee extends Activity{
    private Button show,next,back,clr;
    private Button[] input;
    private EditText test;
    private int recieve;
    public int show1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodsee1);
        init();
        //initButton();

    }

    private void init() {

        input = new Button[10];
        input[0] = (Button)findViewById(R.id.b0);
        //input[1] = (Button)findViewById(R.id.b1);
        //input[2] = (Button)findViewById(R.id.b2);
        //input[3] = (Button)findViewById(R.id.b3);
        input[4] = (Button)findViewById(R.id.b4);
        input[5] = (Button)findViewById(R.id.b5);
        input[6] = (Button)findViewById(R.id.b6);
        input[7] = (Button)findViewById(R.id.b7);
        input[8] = (Button)findViewById(R.id.b8);
        input[9] = (Button)findViewById(R.id.b9);


    }
    /*
    private void initButton(){
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    recieve = Integer.parseInt(test.getText().toString());
                }
                catch (NumberFormatException e){

                }

            }

        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),bodsee2.class);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),First.class);
                startActivity(i);
            }
        });

        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int len = test.getText().length();
                if(len>0)
                 test.setText(test.getText().toString().substring(0,len-1));
            }
        });

        clr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                test.setText("");
                return false;
            }
        });

        input[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setText(test.getText().toString()+input[0].getText());
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

    }
*/

}//end class
