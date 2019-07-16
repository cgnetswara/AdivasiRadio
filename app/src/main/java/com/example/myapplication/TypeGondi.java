package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TypeGondi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_gondi);

        final EditText editText = findViewById(R.id.typeGondiEditText);
        Button button = findViewById(R.id.boloButton);

        final Dashboard dashboard_activity = Dashboard.getDashboard();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboard_activity.sayText(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH);
            }
        });

    }
}
