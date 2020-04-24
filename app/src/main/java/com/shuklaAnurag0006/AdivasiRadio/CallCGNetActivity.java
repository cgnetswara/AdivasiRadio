package com.shuklaAnurag0006.AdivasiRadio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.shuklaAnurag0006.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CallCGNetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_cgnet);

        FloatingActionButton fb = findViewById(R.id.callActionFab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+918602008777"));
                startActivity(callIntent);
            }
        });

    }
}
