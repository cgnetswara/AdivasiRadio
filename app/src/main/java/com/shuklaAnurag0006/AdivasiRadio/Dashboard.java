package com.shuklaAnurag0006.AdivasiRadio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shuklaAnurag0006.myapplication.R;

import java.util.ArrayList;
import java.util.Locale;

//import edu.cmu.cs.speech.tts.flite.CheckVoiceData;
//import edu.cmu.cs.speech.tts.flite.DownloadVoiceData;
//import edu.cmu.cs.speech.tts.flite.Voice;


public class Dashboard extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private final static String LOG_TAG = "Flite_Java_" + Dashboard.class.getSimpleName();


    public static final int WRITE_REQUEST_CODE = 1;
    int TTS_DATA_CHECK_CODE = 2;
    int DATA_DOWNLOAD_CODE = 3;

    // Data variables
//    private ArrayList<Voice> mVoices;
    public static Dashboard dashboard_activity;

    private TextToSpeech mTts;
    private int mSelectedVoice;
    AlertDialog.Builder builder;

    public static Dashboard getDashboard() {
        return dashboard_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#37474F")));

        dashboard_activity = this;

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.INTERNET};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, WRITE_REQUEST_CODE);
        }

        CardView mediaSwaraCardView = findViewById(R.id.mediaSwaraCardView);
        ImageView typeinGondiCardView = findViewById(R.id.ttsActivityNav);
        CardView libraryCardView = findViewById(R.id.libraryCardView);
        ImageView callFb = findViewById(R.id.callActivityNav);


        callFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CallCGNetActivity.class);
                startActivity(intent);
            }
        });

        mediaSwaraCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MediaSwara.class);
                startActivity(intent);
            }
        });

        typeinGondiCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TransDataCollectionActivity.class);
                startActivity(intent);
            }
        });

        libraryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Library.class);
                startActivity(intent);
            }
        });

    }

    public void initVoices() {

        mTts = new TextToSpeech(dashboard_activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    mTts.setLanguage(Locale.forLanguageTag("hin"));
                    Log.i("succccess", "success");
                } else {
                    Log.e("TTS", "Initilization Failed!");
                }

            }
        });

//        ArrayList<Voice> allVoices = CheckVoiceData.getVoices();
//        mVoices = new ArrayList<Voice>();
//        for(Voice vox:allVoices) {
//            if (vox.isAvailable()) {
//                mVoices.add(vox);
//                System.out.println(vox.getVariant());
//            }
//        }
//
//        if (mVoices.isEmpty() && builder == null) {
//            // We can't demo anything if there are no voices installed.
////            builder = new AlertDialog.Builder(this);
////            builder.setMessage("Flite voices not installed. Please add voices in order to run the demo");
////            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    dialog.cancel();
////                    builder = null;
////                    Intent intent = new Intent(dashboard_activity, DownloadVoiceData.class);
////                    startActivity(intent);
////                }
////            });
////            AlertDialog alert = builder.create();
////            alert.show();
//            Toast.makeText(this, "Flite voices not installed. Please add voices in order to run the demo", Toast.LENGTH_LONG).show();
//        }
//        else {
//            // Initialize the TTS
//            if (android.os.Build.VERSION.SDK_INT >=
//                    android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                mTts = new TextToSpeech(dashboard_activity, dashboard_activity, "edu.cmu.cs.speech.tts.flite");
//            }
//            else {
//                mTts = new TextToSpeech(dashboard_activity, dashboard_activity);
//            }
//            mSelectedVoice = 0;

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (mTts != null)
//            mTts.shutdown();
//            initVoices();
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTts != null)
            mTts.shutdown();
    }

    public void stopSpeaking() {
        mTts.stop();
    }


    public void sayText(String text, int mode) {
        Log.v(LOG_TAG, "Speaking: " + text);


        //TODO: GET FROM SETTINGS LATER
        //int currentVoiceID = mVoiceSpinner.getSelectedItemPosition();
//        int currentVoiceID = 0;
//        Log.i("equality", String.valueOf(currentVoiceID) + " " + String.valueOf(mSelectedVoice));
//        if (currentVoiceID == mSelectedVoice) {
//            //mSelectedVoice = currentVoiceID;
//            Voice v = mVoices.get(currentVoiceID);
//            mTts.setLanguage(v.getLocale());
//        }

        //int currentRate = mRateSpinner.getSelectedItemPosition();
        int currentRate = 2;
        mTts.setSpeechRate((float) (currentRate + 1) / 3);

//        mTts.speak(text, mode, null, null);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.voices_list) {
//            Intent intent = new Intent(dashboard_activity, DownloadVoiceData.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        if (requestCode == TTS_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                Log.i("Dashboard_actRes:", "Inside resultCode success");
                initVoices();
            } else {
                // missing data, install it


                Log.i("Dashboard_actRes:", "Inside resultCode no luck       ");
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                ArrayList<String> languages = new ArrayList<String>();
                languages.add("hin-IND"); // hin - hindi, IND - INDIA
                installIntent.putStringArrayListExtra(
                        TextToSpeech.Engine.EXTRA_CHECK_VOICE_DATA_FOR, languages);
                startActivityForResult(installIntent, DATA_DOWNLOAD_CODE);
            }
        } else if (requestCode == DATA_DOWNLOAD_CODE) {
            if (requestCode == RESULT_OK) {
                // success, create the TTS instance
                initVoices();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent checkIntent = new Intent();
                    checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                    startActivityForResult(checkIntent, TTS_DATA_CHECK_CODE);
//                        mTts = new TextToSpeech(dashboard_activity, dashboard_activity);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onInit(int status) {
        boolean success = true;
        if (status == TextToSpeech.ERROR) {
            success = false;
        }

        if (success) {
            status = mTts.setEngineByPackageName("edu.cmu.cs.speech.tts.flite");
        }

        if (status == TextToSpeech.ERROR) {
            success = false;
        }

        // REALLY check that it is flite engine that has been initialized
        // This is done using a hack, for now, since for API < 14
        // there seems to be no way to check which engine is being used.

        if (mTts.isLanguageAvailable(new Locale("eng", "USA", "is_flite_available"))
                != TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE) {
            success = false;
        }

        if (!success) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Flite TTS Engine could not be initialized. Check that Flite is enabled on your phone!. In some cases, you may have to select flite as the default engine.");
            builder.setNegativeButton("Open TTS Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    dialog.cancel();
                    Intent intent = new Intent();
                    intent.setAction("com.android.settings.TTS_SETTINGS");
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("Already Did", null);
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            //buildUI();
        }
    }

}
