package com.example.ekachart.eyeproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Ekachart on 2/1/2558.
 */
public class bodsee6 extends Activity {
    private Button show,next,back,first;
    private EditText test;
    private int recieve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodsee6);
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        init();
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieve = Integer.parseInt(test.getText().toString());
                pop();
            }
            private void pop() {
                popDialog.setTitle("ตรวจสอบ");
                if(recieve==74)
                    popDialog.setMessage("ถูก");
                else
                    popDialog.setMessage("ผิด");
                popDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                popDialog.create();
                popDialog.show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),bodsee7.class);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),bodsee5.class);
                startActivity(i);
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),First.class);
                startActivity(i);
            }
        });
    }

    private void init() {
        show = (Button)findViewById(R.id.result);
        next = (Button)findViewById(R.id.go);
        back = (Button)findViewById(R.id.back);
        first = (Button)findViewById(R.id.first);
        test = (EditText)findViewById(R.id.num);
    }
}
