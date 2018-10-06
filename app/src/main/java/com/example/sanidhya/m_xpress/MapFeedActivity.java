package com.example.sanidhya.m_xpress;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanidhya.m_xpress.Adapters.FeedListAdapter;
import com.example.sanidhya.m_xpress.Constants;
import com.example.sanidhya.m_xpress.IssueCard;
import com.example.sanidhya.m_xpress.MainActivity;
import com.example.sanidhya.m_xpress.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapFeedActivity extends AppCompatActivity implements OnMapReadyCallback {
    JSONArray feedData;
    private GoogleMap mMap;
    double lat=19.0213, lng=72.8424;
    private SupportMapFragment mapFragment;
    ListView listView;
    private static final String TAG = "MapFeedFragment";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_feed);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listView = findViewById(R.id.feed_recycler_view);
        getCurrentLocationAndData();
    }
    void getCurrentLocationAndData() {
        // TODO Set current lat lng
        getData(lat, lng);
    }

    void getData(double lat, double lng) {
        String URL = Constants.FEED_URL+"?field=nearby&lat="+lat+"&lng="+lng;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, URL, response -> {
            Log.e(TAG, "onResponse: " + response);
            List<IssueCard> issueCardList;
            JSONObject jsonObject;
            JSONArray jsonArray;
            FeedListAdapter feedListAdapter;
            issueCardList = new ArrayList<>();
            IssueCard card;
            //test only
            response = "{\"cards\":[{ \"card_id\": 1, \"category\": \"MISC\", \"comment_count\": 2, \"image\": \"image\", \"lat\": 72.8403, \"lng\": 18.9488, \"timestamp\": \"2018-10-05 22:47:11\", \"title\": \"Card1\", \"upvotes\": 0, \"ward\": \"Fort\" }, { \"card_id\": 2, \"category\": \"SERVICES\", \"comment_count\": 0, \"image\": \"image\", \"lat\": 27.8403, \"lng\": 18.9488, \"timestamp\": \"2018-10-05 23:35:20\", \"title\": \"Card2\", \"upvotes\": 0, \"ward\": \"Chira Bazar-Kalbadevi\" }]}";
            try {
                jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("cards");
                feedData = jsonArray;
                for(int i = 0; i<jsonArray.length(); i++){
                    card = new IssueCard(this, (JSONObject) jsonArray.get(i));
                    issueCardList.add(card);
                }
                feedListAdapter = new FeedListAdapter(this, issueCardList);
                listView.setAdapter(feedListAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e(TAG, "onResponse: " + "No didnt work"));

        // test
        List<IssueCard> issueCardList;
        JSONObject jsonObject;
        JSONArray jsonArray = null;
        FeedListAdapter feedListAdapter;
        issueCardList = new ArrayList<>();
        IssueCard card;
        String response = "{\"cards\":[{ \"card_id\": 1, \"category\": \"MISC\", \"comment_count\": 2, \"image\": \"image\", \"lat\": 72.8403, \"lng\": 18.9488, \"timestamp\": \"2018-10-05 22:47:11\", \"title\": \"Card1\", \"upvotes\": 0, \"ward\": \"Fort\" }, { \"card_id\": 2, \"category\": \"SERVICES\", \"comment_count\": 0, \"image\": \"image\", \"lat\": 27.8403, \"lng\": 18.9488, \"timestamp\": \"2018-10-05 23:35:20\", \"title\": \"Card2\", \"upvotes\": 0, \"ward\": \"Chira Bazar-Kalbadevi\" }]}";
        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONArray("cards");
            Log.e(TAG, "getData: "+jsonObject );
            Log.e(TAG, "getData: "+jsonArray );

            feedData = jsonArray;
            for(int i = 0; i<jsonArray.length(); i++){
                card = new IssueCard(this, (JSONObject) jsonArray.get(i));
                issueCardList.add(card);
            }
            feedListAdapter = new FeedListAdapter(this, issueCardList);
            listView.setAdapter(feedListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        queue.add(sr);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int i = 0; i<feedData.length(); i++){
            try {
                markALocation((JSONObject) feedData.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(mumbai));
    }
    public void markALocation(JSONObject data) {
        try {
            double lat = data.getDouble("lat");
            double lng = data.getDouble("lng");
            String category = data.getString("category"); // ROAD, CRIME, SERVICES, MISC
            String title = data.getString("title");
            BitmapDescriptor icon = null;
            LatLng location = new LatLng(lat, lng);
            switch (category) {
                case "ROAD" : icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED); break;
                case "SERVICES" : icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE); break;
                case "CRIME" : icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN); break;
                default: icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW); break;
            }
            mMap.addMarker(new MarkerOptions().position(location).title(title).icon(icon));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void clickedACard() {

    }
    void clickedALocation() {

    }
}
