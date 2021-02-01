package com.example.texttospeech;


import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private EditText editText;
    private ImageView micButton;

    RelativeLayout voiceRelativeLayout, graphRelativeLayout, tableRelativeLayout;

    private TextView textView;

    public static Context contextOfApplication;

    private static ProgressDialog pd;
    private static ProgressDialog pd2;

    String createNewJobApiURL = "http://192.168.1.27:8081/api/main/v1/create_job";

    // HARDCODED JOB ID (MUST CHANGE)
    int jobId = 0;
    int measurementId = 0;
    String graphPictureLocation = "";

    ImageView mImageView;


    //Bluetooth connection to raspy pi----------------------------------------------
    /*BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice = null;

    if(!mBluetoothAdapter.isEnabled())
    {
        Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetooth, 0);
    }*/
    //----------------------------------------------------------------------------------


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        textView = findViewById(R.id.timer);
        editText = findViewById(R.id.text);
        micButton = findViewById(R.id.button);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mImageView = (ImageView) findViewById(R.id.image1);

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        voiceRelativeLayout = (RelativeLayout) findViewById(R.id.voice_relative_layout);
        graphRelativeLayout = (RelativeLayout) findViewById(R.id.graph_relative_layout);

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                editText.setText("");
                editText.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }
            private boolean iscancel= true;
            @SuppressLint("SetTextI18n")
            @Override
            public void onResults(Bundle bundle) {
                micButton.setImageResource(R.drawable.ic_mic_black_off);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                editText.setText(data.get(0));

                if(data.get(0).contentEquals("show me a table")) {

                    voiceRelativeLayout.setVisibility(View.GONE);
                    graphRelativeLayout.setVisibility(View.GONE);
                    tableRelativeLayout.setVisibility(View.VISIBLE);

                }

                if(data.get(0).contentEquals("show me a graph")) {

                    voiceRelativeLayout.setVisibility(View.GONE);
                    graphRelativeLayout.setVisibility(View.VISIBLE);
                    tableRelativeLayout.setVisibility(View.GONE);
                }

                if(data.get(0).contentEquals("show me all my data")) {

                    //jump to url with all data

                }

                if(data.get(0).contentEquals("home")) {

                    voiceRelativeLayout.setVisibility(View.VISIBLE);
                    graphRelativeLayout.setVisibility(View.GONE);
                    tableRelativeLayout.setVisibility(View.GONE);

                }

                if(data.get(0).contentEquals("start recording")) {

                    //Raspy pi comms-----------------------------------------------

                    // TODO: Create a job within the database
                    new JsonTask().execute(createNewJobApiURL);

                    //-------------------------------------------------------------


                    iscancel = false;

                    Timer myTimer = new Timer();

                    myTimer.schedule(new TimerTask() {
                        int time = 0;
                        int min = 0;

                        @Override
                        public void run() {
                            if (!iscancel) {
                                if(time==60){
                                    time=0;
                                    min=min +1;
                                }
                                textView.setText("Recording Time: "+ min+"m, "+time+"s");
                                time = time + 1;
                            }
                        }
                    }, 0, 1000);




                }


                if(data.get(0).contentEquals("stop recording")){

                    pd2 = new ProgressDialog(MainActivity.this);
                    pd2.setMessage("Please wait");
                    pd2.setCancelable(false);
                    pd2.show();

                    iscancel=true;

                    // FIRST: Changing the job status to completed so that the raspberry pi stops measuring and uploads the measurement data to the server
                    if (jobId != 0) {
                        changeJobStatus(jobId, "Completed");

    //                        getJobDataById(int jobId)

                        final Timer jobCompletionTimer = new Timer();

                        jobCompletionTimer.schedule(new TimerTask() {
                            int time = 0;
                            int min = 0;

                            @Override
                            public void run() {
                                getJobDataById(jobId);
                                if (measurementId != 0) {
                                    Log.e("1", "Measurement id = " + Integer.toString(measurementId));
                                    jobCompletionTimer.cancel();
                                    getMeasurementDataById(measurementId);
                                }
                            }
                        }, 0, 4000);


                    }



                }


            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        micButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    micButton.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });


    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = null;
                try {
                    url = new URL(params[0]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                String jobIdString = jsonObject.getString("insertId");
                jobId = Integer.parseInt(jobIdString);
//                Toast.makeText(MainActivity.this, "Job Id is " + Integer.toString(jobId), Toast.LENGTH_SHORT).show();

                removeSimpleProgressDialog();  //will remove progress dialog

            } catch (JSONException e) {
                e.printStackTrace();
            }

            removeSimpleProgressDialog();

        }
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (pd != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                    pd = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void changeJobStatus(int jobId, String newJobStatus) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        HttpRequestService httpRequestService = retrofit.create(HttpRequestService.class);

        ChangeJobStatusPostRequest changeJobStatusPostRequest = new ChangeJobStatusPostRequest(jobId, newJobStatus);

        Call<ServerPostResponse> call = httpRequestService.changeJobStatus(changeJobStatusPostRequest);

        call.enqueue(new Callback<ServerPostResponse>() {
            @Override
            public void onResponse(Call<ServerPostResponse> call, Response<ServerPostResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("Main", "Job status change was successful.");
//                    Toast.makeText(MainActivity.this, "Job status change was successful.", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Log.e("POST", response.toString());
                }
            }

            @Override
            public void onFailure(Call<ServerPostResponse> call, Throwable t) {
                if (t.toString().equals("com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $")) {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unable to reach servers", Toast.LENGTH_SHORT).show();
                    Log.e("Main", "Unable to submit post to API.");
                    Log.e("Main", call.toString());
                    Log.e("Main", t.toString());
                }

            }
        });
    }

    private void getJobDataById(int jobId) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        HttpRequestService httpRequestService = retrofit.create(HttpRequestService.class);

        GetJobDataByIdPostRequest getJobDataByIdPostRequest = new GetJobDataByIdPostRequest(jobId);

        Call<List<GetJobDataByIdPostResponse>> call = httpRequestService.getJobDataById(getJobDataByIdPostRequest);

        call.enqueue(new Callback<List<GetJobDataByIdPostResponse>>() {
            @Override
            public void onResponse(Call<List<GetJobDataByIdPostResponse>> call, Response<List<GetJobDataByIdPostResponse>> response) {
                if(response.isSuccessful()) {
                    Log.e("MEAS", Integer.toString(response.body().get(0).getMeasurementEntryId()));
                    measurementId = response.body().get(0).getMeasurementEntryId();
                }
                else
                {
                    Log.e("POST", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<GetJobDataByIdPostResponse>> call, Throwable t) {
                if (t.toString().equals("com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $")) {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unable to reach servers", Toast.LENGTH_SHORT).show();
                    Log.e("Main", "Unable to submit post to API.");
                    Log.e("Main", call.toString());
                    Log.e("Main", t.toString());
                }

            }
        });
    }


    private void getMeasurementDataById(int newMeasurementId) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        HttpRequestService httpRequestService = retrofit.create(HttpRequestService.class);

        GetMeasurementDataByIdPostRequest getMeasurementDataByIdPostRequest = new GetMeasurementDataByIdPostRequest(newMeasurementId);

        Call<List<GetMeasurementDataByIdPostResponse>> call = httpRequestService.getMeasurementDataById(getMeasurementDataByIdPostRequest);

        call.enqueue(new Callback<List<GetMeasurementDataByIdPostResponse>>() {
            @Override
            public void onResponse(Call<List<GetMeasurementDataByIdPostResponse>> call, Response<List<GetMeasurementDataByIdPostResponse>> response) {
                if(response.isSuccessful()) {
                    graphPictureLocation = response.body().get(0).getGraphPictureLocation();
                    Log.e("LINK", graphPictureLocation);

                    Glide.with(MainActivity.this).load(graphPictureLocation).placeholder(R.drawable.image_progress).into(mImageView);

                    if (pd2.isShowing()){
                        pd2.dismiss();
                    }

                    removeSimpleProgressDialog();

                }
                else
                {
                    Log.e("POST", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<GetMeasurementDataByIdPostResponse>> call, Throwable t) {
                if (t.toString().equals("com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $")) {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unable to reach servers", Toast.LENGTH_SHORT).show();
                    Log.e("Main", "Unable to submit post to API.");
                    Log.e("Main", call.toString());
                    Log.e("Main", t.toString());
                }

            }
        });
    }

}