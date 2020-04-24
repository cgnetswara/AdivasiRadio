package com.shuklaAnurag0006.AdivasiRadio;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shuklaAnurag0006.myapplication.R;

import data.DatabaseContract;
import data.DatabaseContract.Question_Download_QUEUE;
import data.DatabaseContract.Translation_Upload_QUEUE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Offline_Translation extends AppCompatActivity {

    TextView questionTextView;
    TextView qNumTextView;
    TextView ansNumTextView;
    EditText transEditText;
    Button downloadButton;
    Button uploadButton;
    Button submitButton;
    String phone;
    ContentResolver contentResolver;
    SharedPreferences sharedPref;
    RequestQueue requestQueue;
    int currQId = -1;
    String TEMP_URL = TransDataCollectionActivity.BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline__translation);

        questionTextView = findViewById(R.id.questionTextView);
        qNumTextView = findViewById(R.id.qNumTextView);
        ansNumTextView = findViewById(R.id.ansNumTextView);
        transEditText = findViewById(R.id.translationEditText);
        downloadButton = findViewById(R.id.downloadButton);
        uploadButton = findViewById(R.id.uploadButton);
        submitButton = findViewById(R.id.submitButton);
        phone = TransDataCollectionActivity.phone;
        contentResolver = getContentResolver();
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        updateDisplayAndQuery();

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Offline_Translation.this, "Downloading...", Toast.LENGTH_SHORT).show();
                // TODO: Allow custom number of sentences download.
                downloadAndSave();
            }
        });



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAnswer();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    uploadAnswers();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Offline_Translation.this, "Something wrong with JSON!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void updateDisplayAndQuery() {
        Uri uri1 = Uri.withAppendedPath(DatabaseContract.BASE_CONTENT_URI, DatabaseContract.PATH_DOWNLOAD_QUESTIONS);
        Uri uri2 = Uri.withAppendedPath(DatabaseContract.BASE_CONTENT_URI, DatabaseContract.PATH_UPLOAD_QUESTIONS);

        Cursor c1 = contentResolver.query(uri1, null, null, null, null);
        Cursor c2 = contentResolver.query(uri2, null, null, null, null);

        qNumTextView.setText(getResources().getString(R.string.qNumTextView) +c1.getCount());
        ansNumTextView.setText(getResources().getString(R.string.ansNumTextView) + c2.getCount());

        if (c1.getCount() == 0) {
            currQId = -1;
            questionTextView.setText("No Data Downloaded");
        } else {
            c1.moveToFirst();
            currQId = c1.getInt(c1.getColumnIndex(Question_Download_QUEUE.QUESTION_ID));
            String qText = c1.getString(c1.getColumnIndex(Question_Download_QUEUE.QUESTION_TEXT));

            questionTextView.setText(qText);
            transEditText.setText("");

        }

        c1.close();
        c2.close();

    }

    private void uploadAnswers() throws JSONException {
        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.withAppendedPath(DatabaseContract.BASE_CONTENT_URI, DatabaseContract.PATH_UPLOAD_QUESTIONS);
        Cursor c1 = contentResolver.query(uri, null, null, null, null, null);
        if(c1.getCount() == 0) {
            Toast.makeText(this, "No Translations to upload", Toast.LENGTH_SHORT).show();
        } else {
            final JSONArray translations = new JSONArray();

            while (c1.moveToNext()) {
                JSONObject object = new JSONObject();
                int qId = c1.getInt(c1.getColumnIndex(Translation_Upload_QUEUE.QUESTION_ID));
                String Translation = c1.getString(c1.getColumnIndex(Translation_Upload_QUEUE.TRANSLATION));
                object.put("qId", qId);
                object.put("translation", Translation);
                object.put("regionId", 0); //TODO: Add spinner and change the default
                translations.put(object);
            }
             String url = TEMP_URL + "submitAnswerOffline/";
            StringRequest uploadStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equals("SUCCESS")) {
                        Toast.makeText(Offline_Translation.this, "Uploaded translations successfully", Toast.LENGTH_SHORT).show();
                        Uri uri = Uri.withAppendedPath(DatabaseContract.BASE_CONTENT_URI, DatabaseContract.PATH_UPLOAD_QUESTIONS);
                        contentResolver.delete(uri, null, null);
                        updateDisplayAndQuery();
                    } else {
                        Toast.makeText(Offline_Translation.this, "Wrong response from the server!", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Offline_Translation.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("answers", translations.toString());
                    params.put("phone", phone);
                    return params;
                }
            };

            requestQueue.add(uploadStringRequest);

        }

    }

    private void downloadAndSave() {

        int download_count = 5;
        final Uri uri = Uri.withAppendedPath(DatabaseContract.BASE_CONTENT_URI, DatabaseContract.PATH_DOWNLOAD_QUESTIONS);
        int space = Question_Download_QUEUE.QUEUE_LIMIT-contentResolver.query(uri, null,null,null,null).getCount();
        if (space <= 0) return;
        String url = TEMP_URL + "fetchQuestionOffline/" + download_count;
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json_reponse = new JSONObject(response);
                    JSONArray qArr = json_reponse.getJSONArray("response");

                    for(int i=0; i<qArr.length(); i++) {

                        JSONObject object = qArr.getJSONObject(i);
                        int qId = object.getInt("qId");
                        String question = object.getString("question");

                        ContentValues values = new ContentValues();
                        values.put(Question_Download_QUEUE.QUESTION_ID, qId);
                        values.put(Question_Download_QUEUE.QUESTION_TEXT, question);
                        Uri res_uri = contentResolver.insert(uri, values);
                        if (res_uri == null) {
                            throw new IllegalArgumentException("Cannot insert row");
                        }

                    }
                    updateDisplayAndQuery();
                    Toast.makeText(Offline_Translation.this, "Finished Downloading", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Offline_Translation.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(MyStringRequest);


    }

    private void submitAnswer() {
        String answer = transEditText.getText().toString();
        ContentValues values = new ContentValues();

        if (answer.isEmpty()) {
            Toast.makeText(this, "Please Enter an input", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currQId < 0) {
            Toast.makeText(this, "No Data available for translation", Toast.LENGTH_SHORT).show();;
            return;
        }

        values.put(Translation_Upload_QUEUE.QUESTION_ID, currQId);
        values.put(Translation_Upload_QUEUE.TRANSLATION, answer);

        Uri uri = Uri.withAppendedPath(DatabaseContract.BASE_CONTENT_URI, DatabaseContract.PATH_UPLOAD_QUESTIONS);
        Uri res = contentResolver.insert(uri, values);

        Uri del_uri = Uri.withAppendedPath(DatabaseContract.BASE_CONTENT_URI, DatabaseContract.PATH_DOWNLOAD_QUESTIONS + "/" + currQId);
        contentResolver.delete(del_uri, null, null);

        if (res == null) {
            Toast.makeText(this, "Not able to insert data. Try Again.", Toast.LENGTH_SHORT).show();
        } else {
            updateDisplayAndQuery();
        }

    }


}
