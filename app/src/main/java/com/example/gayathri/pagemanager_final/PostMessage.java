package com.example.gayathri.pagemanager_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookGraphResponseException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostMessage extends AppCompatActivity {

    public enum post_type {SCHEDULED, DRAFT, ADS_POST};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);

        //Intent myIntent = getIntent();
        //AccessToken accessToken=null;
        //Bundle bundle = this.getIntent().getExtras();
        //if(bundle != null)
        //    accessToken = bundle.getParcelable("AccessToken");


    }

    public void shareFacebook(View v) {
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
                            new GraphRequest(accessToken_obj, "/1248380195195131/feed", params, HttpMethod.POST,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            if (response.getError() == null)
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

                            new GraphRequest(accessToken_obj, "/1248380195195131/feed", params, HttpMethod.POST,
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
