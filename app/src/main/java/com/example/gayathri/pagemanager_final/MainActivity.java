package com.example.gayathri.pagemanager_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.util.*;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private static final List<String> PERMISSIONS = Arrays.asList("manage_pages,publish_actions,read_insights");
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private TextView info;
    protected String pageID="1248380195195131";
    protected LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        serialize_accesstoken s_at= new serialize_accesstoken();
        s_at.accessToken=AccessToken.getCurrentAccessToken();
        if(AccessToken.getCurrentAccessToken()!=null)
        {
            Intent myIntent = new Intent(this,PostMessage.class);
            startActivity(myIntent);
        }


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult result) {
                Intent myIntent = new Intent(MainActivity.this, PostMessage.class);
                startActivity(myIntent);

            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException error) {
                info.setText("Login attempt failed.");
            }
        });
        if (loginButton.getFragment() != null) {
            LoginManager.getInstance().logInWithPublishPermissions(loginButton.getFragment(), PERMISSIONS);
        } else {
            LoginManager.getInstance().logInWithPublishPermissions(MainActivity.this, PERMISSIONS);
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    class serialize_accesstoken implements Serializable
    {
        AccessToken accessToken;
    }
}
