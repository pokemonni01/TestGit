package com.example.ekachart.eyeproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ekachart on 2/1/2558.
 */
public class bodsee14 extends Activity {
    private Button back,first;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodsee14);
        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),bodsee13.class);
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
        back = (Button)findViewById(R.id.back);
        first = (Button)findViewById(R.id.first);
    }
}