package com.example.gayathri.pagemanager_final;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookGraphResponseException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class PostMessage extends AppCompatActivity implements
        View.OnClickListener{

    public enum post_type {SCHEDULED, DRAFT, ADS_POST};
    private static final String PAGE_ID = "1248380195195131";

    private Button btnDatePicker, btnTimePicker;
    private EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private long unixTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);

        //Intent myIntent = getIntent();
        //AccessToken accessToken=null;
        //Bundle bundle = this.getIntent().getExtras();
        //if(bundle != null)
        //    accessToken = bundle.getParcelable("AccessToken");

        btnDatePicker=(Button)findViewById(R.id.datebutton);
        btnTimePicker=(Button)findViewById(R.id.timebutton);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay, mHour, mMinute);
        unixTimeStamp = c.getTimeInMillis()/1000;
        Log.d("UNIX TIME STAMP", String.valueOf(unixTimeStamp));
    }


    public void schedulePost(View v) {
        //final EditText unixTimeBox = (EditText) findViewById(R.id.datebutton);
        //unixTimeBox.setFocusable(true);
        //unixTimeBox.setEnabled(true);
        //unixTimeBox.setClickable(true);
        //unixTimeBox.requestFocus();
    }

    public void shareFacebook(View v) {
        final EditText text = (EditText) findViewById(R.id.MessageBox);
        final CheckBox schedulePost = (CheckBox) findViewById(R.id.scheduleChkBox);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/accounts",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Bundle params = new Bundle();
                        params.putString("message", text.getText().toString());

                        if(schedulePost.isChecked()){
                            params.putString("published", "false");
                            params.putLong("scheduled_publish_time", unixTimeStamp);
                        }
                        JSONObject JsonObject = response.getJSONObject();
                        try {
                            JSONArray data = JsonObject.getJSONArray("data");
                            JSONObject objectdata = data.getJSONObject(0);
                            String accessToken = objectdata.getString("access_token");
                            Log.d("AccessToken: ", accessToken);
                            AccessToken accessToken_obj = new AccessToken(accessToken, AccessToken.getCurrentAccessToken().getApplicationId(),
                                    AccessToken.getCurrentAccessToken().getUserId(), AccessToken.getCurrentAccessToken().getPermissions(),
                                    AccessToken.getCurrentAccessToken().getDeclinedPermissions(), AccessToken.getCurrentAccessToken().getSource(),
                                    AccessToken.getCurrentAccessToken().getExpires(), AccessToken.getCurrentAccessToken().getLastRefresh());
                            new GraphRequest(accessToken_obj, "/"  + PAGE_ID + "/feed", params, HttpMethod.POST,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            if (response.getError() == null)
                                                if(schedulePost.isChecked())
                                                    Toast.makeText(PostMessage.this, "Scheduled for " + txtDate.getText().toString() + ", " + txtTime.getText().toString(), Toast.LENGTH_LONG).show();
                                                else
                                                    Toast.makeText(PostMessage.this, "Shared on facebook page.", Toast.LENGTH_LONG).show();
                                            else {
                                                Toast.makeText(PostMessage.this, response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                                                Log.d(response.getError().getErrorMessage(), "Error Message :");
                                            }
                                        }
                                    }).executeAsync();
                        } catch (JSONException j) {
                            Log.d(j.getMessage(), "Error Message");
                        }

                    }
                }
        ).executeAsync();

    }

    public void saveFacebook(View v) {
        final EditText text = (EditText) findViewById(R.id.MessageBox);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/accounts",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Bundle params = new Bundle();
                        params.putString("message", text.getText().toString());
                        params.putSerializable("unpublished_content_type", post_type.DRAFT);
                        params.putString("published", "false");
                        JSONObject JsonObject = response.getJSONObject();
                        try {
                            JSONArray data = JsonObject.getJSONArray("data");
                            JSONObject objectdata = data.getJSONObject(0);
                            String accessToken = objectdata.getString("access_token");


                            Log.d("Access Token :", AccessToken.getCurrentAccessToken().getToken());
                            AccessToken accessToken_obj = new AccessToken(accessToken, AccessToken.getCurrentAccessToken().getApplicationId(),
                                    AccessToken.getCurrentAccessToken().getUserId(), AccessToken.getCurrentAccessToken().getPermissions(), AccessToken.getCurrentAccessToken().getDeclinedPermissions(), AccessToken.getCurrentAccessToken().getSource(), AccessToken.getCurrentAccessToken().getExpires(), AccessToken.getCurrentAccessToken().getLastRefresh());

                            new GraphRequest(accessToken_obj, "/" + PAGE_ID + "/feed", params, HttpMethod.POST,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            if (response.getError() == null)
                                                Toast.makeText(PostMessage.this, "Saved on facebook page.", Toast.LENGTH_LONG).show();
                                            else {
                                                Toast.makeText(PostMessage.this,response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                                                Log.d(response.getError().getErrorMessage(), "Error Message :");
                                            }
                                        }
                                    }).executeAsync();

                        } catch (JSONException j) {
                            Log.d(j.getMessage(), "Error Message");
                        }
                    }
                }
        ).executeAsync();
    }

    public void viewPosts(View v) {
        Intent myIntent = new Intent(PostMessage.this,ViewPosts.class);
        startActivity(myIntent);
    }

}
