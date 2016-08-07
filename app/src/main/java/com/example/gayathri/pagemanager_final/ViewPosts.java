package com.example.gayathri.pagemanager_final;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewPosts extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public StringBuilder getPublishedPosts()
    {
        final StringBuilder allPublishedPosts = new StringBuilder();

        Log.d(allPublishedPosts.toString(),"All Posts");
        return allPublishedPosts;
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String PAGE_ID = "1248380195195131";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_posts, container, false);
            //final TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setTextSize(20);
            //textView.setMovementMethod(new ScrollingMovementMethod());
            final TableLayout tl = (TableLayout) rootView.findViewById(R.id.maintable);
            //TextView tv = new TextView(getActivity());
            //tv.setText(message);
            //TableRow tr = new TableRow(getActivity());
            //tr.addView(tv);


            final StringBuilder allPublishedPosts = new StringBuilder();
            final StringBuilder allUnPublishedPosts = new StringBuilder();

            if(getArguments().getInt(ARG_SECTION_NUMBER)==1)
            {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/accounts",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                JSONObject JsonObject = response.getJSONObject();
                                try {
                                    JSONArray data = JsonObject.getJSONArray("data");
                                    JSONObject objectdata = data.getJSONObject(0);
                                    String accessToken = objectdata.getString("access_token");
                                    Log.d("Access Token :", AccessToken.getCurrentAccessToken().getToken());
                                    //Bundle params = new Bundle();
                                   // params.putString("fields","message,likes.summary(true)");
                                    final AccessToken accessToken_obj = new AccessToken(accessToken, AccessToken.getCurrentAccessToken().getApplicationId(),
                                            AccessToken.getCurrentAccessToken().getUserId(), AccessToken.getCurrentAccessToken().getPermissions(), AccessToken.getCurrentAccessToken().getDeclinedPermissions(), AccessToken.getCurrentAccessToken().getSource(), AccessToken.getCurrentAccessToken().getExpires(), AccessToken.getCurrentAccessToken().getLastRefresh());
                                    new GraphRequest(accessToken_obj, "/" + PAGE_ID + "/feed", null, HttpMethod.GET,
                                            new GraphRequest.Callback() {
                                                public void onCompleted(GraphResponse response) {

                                                    if (response.getError() == null) {

                                                        try {
                                                            JSONArray data = response.getJSONObject().getJSONArray("data");
                                                         //   final List<String> likes = new ArrayList<String>();
                                                            for(int i=0;i<data.length();i++)
                                                            {
                                                                JSONObject obj = data.getJSONObject(i);
                                                               // JSONObject obj_likes=obj.getJSONObject("likes").getJSONObject("summary");
                                                                //String like_count=obj_likes.get("total_count").toString();
                                                                String message = obj.getString("message");
                                                                String createdTime = obj.getString("created_time");
                                                                // allPublishedPosts.append(obj.getString("message")+"\n");
                                                                //Log.d("Message:",message);
                                                                //Log.d("Like count:",like_count);
                                                                //textView.append(message+"\n\n");

                                                                TextView tv1 = new TextView(getActivity());
                                                                tv1.setText(message);
                                                                tv1.setTextSize(20);
                                                                tv1.setSingleLine(false);

                                                                try {
                                                                    createdTime = new SimpleDateFormat("MM/dd/yyyy KK:mm:ss a").format(
                                                                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(obj.getString("created_time"))).toString();
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }

                                                                TextView tv2 = new TextView(getActivity());
                                                                tv2.setText("(Created time: " + createdTime + ")");
                                                                tv2.setTextColor(Color.parseColor("#B03060"));
                                                                tv2.setTextSize(15);

                                                                TableRow tr1 = new TableRow(getActivity());
                                                                tr1.addView(tv1);
                                                                tr1.setPadding(0, 0, 0, 1);
                                                                TableRow tr2 = new TableRow(getActivity());
                                                                tr2.addView(tv2);
                                                                tr2.setPadding(0, 0, 0, 15);
                                                                tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                                                tl.addView(tr2, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                                                            }


                                                        }catch(JSONException e)
                                                        {
                                                            Log.d("JSON Exception",e.getMessage());
                                                        }
                                                    }
                                                    else {
                                                        //Toast.makeText(this,response.getError().getErrorMessage())
                                                        //Toast.makeText(this,response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
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
                //textView.setText(allPublishedPosts.toString());
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==2)
            {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/accounts",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                JSONObject JsonObject = response.getJSONObject();
                                try {
                                    Bundle params = new Bundle();
                                    params.putBoolean("is_published",false);
                                    JSONArray data = JsonObject.getJSONArray("data");
                                    JSONObject objectdata = data.getJSONObject(0);
                                    String accessToken = objectdata.getString("access_token");
                                    Log.d("Access Token :", AccessToken.getCurrentAccessToken().getToken());
                                    AccessToken accessToken_obj = new AccessToken(accessToken, AccessToken.getCurrentAccessToken().getApplicationId(),
                                            AccessToken.getCurrentAccessToken().getUserId(), AccessToken.getCurrentAccessToken().getPermissions(), AccessToken.getCurrentAccessToken().getDeclinedPermissions(), AccessToken.getCurrentAccessToken().getSource(), AccessToken.getCurrentAccessToken().getExpires(), AccessToken.getCurrentAccessToken().getLastRefresh());

                                    new GraphRequest(accessToken_obj, "/" + PAGE_ID + "/promotable_posts", params, HttpMethod.GET,
                                            new GraphRequest.Callback() {
                                                public void onCompleted(GraphResponse response) {
                                                    if (response.getError() == null) {
                                                        try {
                                                            JSONArray data = response.getJSONObject().getJSONArray("data");
                                                            for(int i=0;i<data.length();i++)
                                                            {
                                                                JSONObject obj = data.getJSONObject(i);
                                                                String message = obj.getString("message");
                                                                String createdTime = obj.getString("created_time");
                                                                allPublishedPosts.append(message);
                                                                //textView.append(message);

                                                                TextView tv1 = new TextView(getActivity());
                                                                tv1.setText(message);
                                                                tv1.setTextSize(20);
                                                                tv1.setSingleLine(false);

                                                                try {
                                                                    createdTime = new SimpleDateFormat("MM/dd/yyyy KK:mm:ss a").format(
                                                                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(obj.getString("created_time"))).toString();
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }

                                                                TextView tv2 = new TextView(getActivity());
                                                                tv2.setText("(Created time: " + createdTime + ")");
                                                                tv2.setTextColor(Color.parseColor("#B03060"));
                                                                tv2.setTextSize(15);

                                                                TableRow tr1 = new TableRow(getActivity());
                                                                tr1.addView(tv1);
                                                                tr1.setPadding(0, 0, 0, 1);
                                                                TableRow tr2 = new TableRow(getActivity());
                                                                tr2.addView(tv2);
                                                                tr2.setPadding(0, 0, 0, 15);
                                                                tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                                                tl.addView(tr2, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                                            }

                                                        }catch(JSONException e)
                                                        {
                                                            Log.d("JSON Exception",e.getMessage());
                                                        }
                                                    }
                                                    else {
                                                        //Toast.makeText(this,response.getError().getErrorMessage())
                                                        //Toast.makeText(this,response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
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

                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==3)
            {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/accounts",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                JSONObject JsonObject = response.getJSONObject();
                                try {
                                    JSONArray data = JsonObject.getJSONArray("data");
                                    JSONObject objectdata = data.getJSONObject(0);
                                    String accessToken = objectdata.getString("access_token");
                                    Log.d("Access Token :", AccessToken.getCurrentAccessToken().getToken());
                                    Bundle params = new Bundle();
                                    params.putString("fields","message,insights.metric(post_impressions,post_reactions_like_total)");
                                    final AccessToken accessToken_obj = new AccessToken(accessToken, AccessToken.getCurrentAccessToken().getApplicationId(),
                                            AccessToken.getCurrentAccessToken().getUserId(), AccessToken.getCurrentAccessToken().getPermissions(), AccessToken.getCurrentAccessToken().getDeclinedPermissions(), AccessToken.getCurrentAccessToken().getSource(), AccessToken.getCurrentAccessToken().getExpires(), AccessToken.getCurrentAccessToken().getLastRefresh());
                                    new GraphRequest(accessToken_obj, "/" + PAGE_ID + "/posts", params, HttpMethod.GET,
                                            new GraphRequest.Callback() {
                                                public void onCompleted(GraphResponse response) {

                                                    if (response.getError() == null) {

                                                        try {
                                                            JSONArray data = response.getJSONObject().getJSONArray("data");
                                                            Log.d("data",data.toString());
                                                            for(int i=0;i<data.length();i++) {
                                                                JSONObject obj = data.getJSONObject(i);
                                                                String message=obj.getString("message");
                                                                Log.d("Message :",message);
                                                                JSONObject insights = obj.getJSONObject("insights");
                                                                String num_views=insights.getJSONArray("data").getJSONObject(0).getJSONArray("values").getJSONObject(0).getString("value");
                                                                String num_likes=insights.getJSONArray("data").getJSONObject(1).getJSONArray("values").getJSONObject(0).getString("value");
                                                                //textView.append(message+"\n"+"Views: "+num_views+"\n"+"Likes: "+num_likes+"\n\n");

                                                                TextView tv1 = new TextView(getActivity());
                                                                tv1.setText(message);
                                                                tv1.setTextSize(20);
                                                                tv1.setSingleLine(false);

                                                                TextView tv2 = new TextView(getActivity());
                                                                tv2.setText("(Views: " + num_views + ", Likes: " + num_likes + ")");
                                                                tv2.setTextColor(Color.parseColor("#B03060"));
                                                                tv2.setTextSize(15);

                                                                TableRow tr1 = new TableRow(getActivity());
                                                                tr1.addView(tv1);
                                                                tr1.setPadding(0, 0, 0, 1);
                                                                TableRow tr2 = new TableRow(getActivity());
                                                                tr2.addView(tv2);
                                                                tr2.setPadding(0, 0, 0, 15);
                                                                tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                                                tl.addView(tr2, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                                                            }
                                                        }catch(JSONException e)
                                                        {
                                                            Log.d("JSON Exception",e.getMessage());
                                                        }
                                                    }
                                                    else {
                                                        //Toast.makeText(this,response.getError().getErrorMessage())
                                                        //Toast.makeText(this,response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
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

            return rootView;
        }
        public void getLikes(AccessToken accessToken_obj,String postID,GraphRequest.Callback callback)
        {
            Bundle params = new Bundle();
            params.putBoolean("summary",true);
            GraphRequest likesRequest=new GraphRequest(accessToken_obj, "/"+postID+"/likes", params, HttpMethod.GET,callback);
            likesRequest.executeAsync();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Published Posts";
                case 1:
                    return "Unpublished Posts";
                case 2:
                    return "Page Statistics";
            }
            return null;
        }
    }
}
