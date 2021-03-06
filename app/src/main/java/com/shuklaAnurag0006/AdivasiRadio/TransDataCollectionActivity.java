package com.shuklaAnurag0006.AdivasiRadio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shuklaAnurag0006.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TransDataCollectionActivity extends AppCompatActivity {

    static String BASE_URL = "http://gondi-data-collection.52kajbsigc.ap-south-1.elasticbeanstalk.com/";
    Button submitButton;
    TextView questionTextView;
    EditText transEditText;
    RelativeLayout relativeLayout;
    TextView progressTextView;
    TextView pointsTextView;
    TextView phoneTextView;
    TextView rankTextView; // Implement rank system
    Spinner regionSpinner;
    static String phone;
    int numberTranslated = 0;
    int regionId;
    int points;
    int progress;
    int numberForStreak = 10;
    boolean isSteak = false;
    SharedPreferences sharedPref;
    TextView streakBarTextView;
    TextView rankBarTextView;
    RequestQueue MyRequestQueue;
    String QUESTION_URL = BASE_URL + "fetchQuestion/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_data_collection);

        questionTextView = findViewById(R.id.questionTextView);
        ;
        submitButton = findViewById(R.id.submitButton);
        transEditText = findViewById(R.id.translationEditText);
        relativeLayout = findViewById(R.id.relativeLayout);
        pointsTextView = findViewById(R.id.pointsTextView);
        progressTextView = findViewById(R.id.progressTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        streakBarTextView = findViewById(R.id.streakBarTextView);
        rankBarTextView = findViewById(R.id.rankBarTextView);
        rankTextView = findViewById(R.id.rankTextView);
        regionSpinner = findViewById(R.id.regionSpinner);
        MyRequestQueue = Volley.newRequestQueue(getApplicationContext());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        // Apply the adapter to the spinner
        regionSpinner.setAdapter(adapter);
        regionSpinner.setSelection(sharedPref.getInt(getString(R.string.selected_region_id), 0)); // get selected region or default region
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getApplicationContext().getString(R.string.selected_region_id), i);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        streakBarTextView.setText(0 + "/" + numberForStreak);


        phone = sharedPref.getString(getString(R.string.phone_number), "");

        if (phone.isEmpty()) {
            showphoneInputDialog();
        } else {
            startRegistrationAndLoad();
        }
        // Set up the input
//        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//        // Get the layout inflater
//        LayoutInflater inflater = getLayoutInflater();
//
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        builder.setView(inflater.inflate(R.layout.phone_dialog, relativeLayout))
//                // Add action buttons
//                .setPositiveButton("Resgiter", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        RegisterPhone registerPhone = new RegisterPhone();
//                        registerPhone.execute(BASE_URL + "verifyOrRegister/" + "9319048339");
//                    }
//                });
//        builder.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String translation = transEditText.getText().toString();
                if (translation.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please provide an input", Toast.LENGTH_LONG).show();
                } else {
                    numberTranslated += 1;
                    int toAdd = 1;
                    if (numberTranslated != 0 && numberTranslated % numberForStreak == 0) {
                        toAdd = 5;
                        isSteak = true; //making a streak builder
                    }
                    regionId = regionSpinner.getSelectedItemPosition();
                    submitTranslation(BASE_URL + "submitAnswer/", phone, translation, toAdd, regionId);
                }
            }
        });

    }

    void setRank(int points) {
        String text = "";
        int diff;
        if (points < 15) {
            text = getString(R.string.level0);
            diff = 15 - points;
        } else if (points < 40) {
            text = getString(R.string.level1);
            diff = 40 - points;
        } else if (points < 125) {
            text = getString(R.string.level2);
            diff = 125 - points;
        } else if (points < 170) {
            text = getString(R.string.level3);
            diff = 170 - points;
        } else {
            text = getString(R.string.level4);
            diff = -1;
        }

        rankTextView.setText(getString(R.string.rankTextViewStart) + " " + text);

        if (diff != -1) {
            rankBarTextView.setText(diff + " points.");
        } else {
            rankBarTextView.setText("You are at top!");
        }

    }

    void requestQuestion(String url, final String phone) {
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                questionTextView.setText(response);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("requestQuestionError", error + "");
                Toast.makeText(TransDataCollectionActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone); //Add the data you'd like to send to the server.
                return params;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    void submitTranslation(String url, final String phone, final String translation, final int toAdd, final int regionId) {
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    points = jsonObject.getInt("points");
                    progress = jsonObject.getInt("progress");

                    pointsTextView.setText(getResources().getString(R.string.pointsTextViewStart) + " " + points);
                    progressTextView.setText(getResources().getString(R.string.progressTextViewStart) + " " + progress);

                    setRank(points);


                    streakBarTextView.setText(numberTranslated % numberForStreak + "/" + numberForStreak);

                    if (isSteak) {
                        buildStreakDialog();
                        isSteak = false;
                    }

                    QuestionFetch questionFetch = new QuestionFetch();
                    Toast.makeText(getApplicationContext(), "Successfully submitted", Toast.LENGTH_SHORT).show();
//                    questionFetch.execute(BASE_URL+ "fetchQuestion/" + phone);
                    requestQuestion(QUESTION_URL, phone);
                    transEditText.setText("");


                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("TranslationError", error + "");
                Toast.makeText(TransDataCollectionActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone); //Add the data you'd like to send to the server.
                params.put("answer", translation);
                params.put("addPoint", toAdd + "");
                params.put("regionId", regionId + "");
                return params;
            }
        };

        MyRequestQueue.add(MyStringRequest);
        // SignIn Logic here

    }

    private void showphoneInputDialog() {

        final EditText phoneInput = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        phoneInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Enter Phone Number (10 Digits)");


        builder.setView(phoneInput);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing here because we override this button later to change the close behaviour.
                //However, we still need this because on older versions of Android unless we
                //pass a handler the button doesn't get instantiated
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        // Overriding the button handler to let validate the data.

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPhone = phoneInput.getText().toString();
                if (inputPhone.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Please enter valid number", Toast.LENGTH_LONG).show();
                } else {
                    phone = inputPhone;
                    startRegistrationAndLoad();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getApplicationContext().getString(R.string.phone_number), phone);
                    editor.apply();
                    dialog.dismiss();
                }

                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }

    private void startRegistrationAndLoad() {
        RegisterPhone registerPhone = new RegisterPhone();

        registerPhone.execute(BASE_URL + "verifyOrRegister/" + phone);
    }

    private class SubmitTrans extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(urls[0]);
                Log.i("URL_Built", urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();

                while (data != -1) {
                    result.append((char) data);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("after_submit_trans", result.toString());
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                points = jsonObject.getInt("points");
                progress = jsonObject.getInt("progress");

                pointsTextView.setText(getResources().getString(R.string.pointsTextViewStart) + " " + points);
                progressTextView.setText(getResources().getString(R.string.progressTextViewStart) + " " + progress);

                setRank(points);


                streakBarTextView.setText(numberTranslated % numberForStreak + "/" + numberForStreak);

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                numberTranslated -= 1; //decrease count, since answer is not submitted
                e.printStackTrace();
            }

            if (isSteak) {
                buildStreakDialog();
                isSteak = false;
            }

            QuestionFetch questionFetch = new QuestionFetch();
            Toast.makeText(getApplicationContext(), "Successfully submitted", Toast.LENGTH_SHORT).show();
//            questionFetch.execute(BASE_URL+ "fetchQuestion/" + phone);

            requestQuestion(QUESTION_URL, phone);
            transEditText.setText("");

        }
    }

    public void buildStreakDialog() {

        new AlertDialog.Builder(TransDataCollectionActivity.this)
                .setIcon(R.drawable.thumbsup)
                .setTitle("Well Done!")
                .setMessage("You have just completed a streak of " + numberForStreak + " quesetions an earned extra 5 points!! Way to go!")
                .setPositiveButton("Ok!", null)
                .show();

    }

    private class RegisterPhone extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(urls[0]);
                Log.i("URL_Built", urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();

                while (data != -1) {
                    result.append((char) data);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("Resister_Result", result.toString());
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                points = jsonObject.getInt("points");
                progress = jsonObject.getInt("progress");

                pointsTextView.setText(getResources().getString(R.string.pointsTextViewStart) + " " + points);
                progressTextView.setText(getResources().getString(R.string.progressTextViewStart) + " " + progress);
                phoneTextView.setText(getString(R.string.phoneTextViewStart) + " " + phone);
                setRank(points);

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                numberTranslated -= 1; //decrease count, since answer is not submitted
                e.printStackTrace();
            }

//            questionFetch.execute(BASE_URL+ "fetchQuestion/" + phone);

            requestQuestion(QUESTION_URL, phone);
//            questionFetch.execute(BASE_URL+ "fetchQuestion/" + phone);
        }
    }


    private class QuestionFetch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(urls[0]);
                Log.i("URL_Built", urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();

                while (data != -1) {
                    result.append((char) data);
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("resultQ", s);
            // Cover all failure cases here
            questionTextView.setText(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_translation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Offline_trans_button:
                Intent intent = new Intent(getApplicationContext(), Offline_Translation.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
