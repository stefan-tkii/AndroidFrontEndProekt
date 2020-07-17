package com.example.androidfrontendapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements MyAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<Item> myList;
    private RequestQueue mRequestQueue;
    private NotificationManager myManager;
    public static final String Channel_ID = "MyChannel";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_home, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(getContext() != null) {
            myManager = (NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            createNotificationChannel();
        }
        myList = new ArrayList<Item>();
        if(super.getActivity() != null)
        {
            mRequestQueue = Volley.newRequestQueue(getActivity());
        }
        else {
            throw new RuntimeException("null returned from getActivity.");
        }
        getJsonData();
        return rootView;
    }

    public void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    Channel_ID, "Channel one", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("You are viewing channel one description.");
           myManager.createNotificationChannel(channel);
        }
    }

    private void getJsonData()
    {
        String Url = "https://pixabay.com/api/?key=17385339-3f20627485439d717ce3e6e1f&q=dogs&image_type=photo&pretty=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        String creatorName = hit.getString("user");
                        String imageUrl = hit.getString("webformatURL");
                        int likeCount = hit.getInt("downloads");
                        myList.add(new Item(imageUrl, creatorName, likeCount));
                    }
                    mAdapter = new MyAdapter(getActivity(), myList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(HomeFragment.this);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void OnItemClick(int position, String text) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(getActivity(), Channel_ID)
                    .setSmallIcon(R.drawable.ic_star)
                    .setContentTitle("Special information")
                    .setContentText("You clicked on " + text + "'s post.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build();
            myManager.notify(position, notification);
            Toast toast = Toast.makeText(getActivity(), "You've been notified.", Toast.LENGTH_SHORT);
            View v = toast.getView();
            v.setBackgroundColor(Color.MAGENTA);
            TextView msg = (TextView) toast.getView().findViewById(android.R.id.message);
            msg.setTextColor(Color.CYAN);
            toast.show();
        }
        else {
            Notification notification = new NotificationCompat.Builder(getActivity())
                    .setSmallIcon(R.drawable.ic_star)
                    .setContentTitle("Special information")
                    .setContentText("You clicked on " + text + "'s post.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build();
            myManager.notify(position, notification);
            Toast toast = Toast.makeText(getActivity(), "You've been notified.", Toast.LENGTH_SHORT);
            View v = toast.getView();
            v.setBackgroundColor(Color.MAGENTA);
            TextView msg = (TextView) toast.getView().findViewById(android.R.id.message);
            msg.setTextColor(Color.CYAN);
            toast.show();
        }
    }
}
