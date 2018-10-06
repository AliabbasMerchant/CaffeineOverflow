package com.example.sanidhya.m_xpress;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFeedActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    LatLngBounds.Builder builder;

    JSONArray feedData;
    {
        try {
            feedData = new JSONObject("{\"cards\":[{ \"card_id\": 1, \"category\": \"MISC\", \"comment_count\": 2, \"image\": \"image\", \"lat\": 19.098361, \"lng\": 72.840984, \"timestamp\": \"2018-10-05 22:47:11\", \"title\": \"Card1\", \"upvotes\": 0, \"ward\": \"Fort\" }, { \"card_id\": 2, \"category\": \"SERVICES\", \"comment_count\": 0, \"image\": \"image\", \"lat\": 19.078246, \"lng\": 72.898425, \"timestamp\": \"2018-10-05 23:35:20\", \"title\": \"Card2\", \"upvotes\": 0, \"ward\": \"Chira Bazar-Kalbadevi\" }]}").getJSONArray("cards");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GoogleMap mMap;
    double lat=19.0213, lng=72.8424;
    private SupportMapFragment mapFragment;
    ListView listView;
    LocationManager locationManager;
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
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
//        getLocation();
        getData(lat, lng);
    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MapFeedActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
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
            try {
                jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("cards");
                if(jsonArray!=null)
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
        String response = "{\"cards\":[{ \"card_id\": 1, \"category\": \"ROAD\", \"comment_count\": 25, \"image\": \"https://s4.scoopwhoop.com/anj/images/8e9dac43-9312-40c2-91f1-a2a8a16130b7.jpg\", \"lat\": 19.098361, \"lng\": 72.840984, \"timestamp\": \"2018-10-05 22:47:11\", \"title\": \"Potholes near Fort\", \"upvotes\": 42, \"ward\": \"Fort\" }, { \"card_id\": 2, \"category\": \"SERVICES\", \"comment_count\": 88, \"image\": \"https://www.india.com/wp-content/uploads/2017/10/mumbai-local.jpg\", \"lat\": 19.078246, \"lng\": 72.898425, \"timestamp\": \"2018-10-05 23:35:20\", \"title\": \"Train Service Disruption\", \"upvotes\": 89, \"ward\": \"Chira Bazar-Kalbadevi\" }]}";
        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONArray("cards");
            Log.e(TAG, "getData: "+jsonObject );
            Log.e(TAG, "getData: "+jsonArray );
            if(jsonArray!=null)
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(feedData == null) {
            {
                try {
                    JSONObject jsonObject = new JSONObject("{\"cards\":[{ \"card_id\": 1, \"category\": \"MISC\", \"comment_count\": 2, \"image\": \"image\", \"lat\": 19.098361, \"lng\": 72.840984, \"timestamp\": \"2018-10-05 22:47:11\", \"title\": \"Card1\", \"upvotes\": 0, \"ward\": \"Fort\" }, { \"card_id\": 2, \"category\": \"SERVICES\", \"comment_count\": 0, \"image\": \"image\", \"lat\": 19.078246, \"lng\": 72.898425, \"timestamp\": \"2018-10-05 23:35:20\", \"title\": \"Card2\", \"upvotes\": 0, \"ward\": \"Chira Bazar-Kalbadevi\" }]}");;
                    feedData = jsonObject.getJSONArray("cards");
                } catch (JSONException e) {
                    Log.e(TAG, "onMapReady");
                    e.printStackTrace();
                }
            }

        }

        builder = new LatLngBounds.Builder();
        for(int i = 0; i<feedData.length(); i++){
            try {
                markALocation((JSONObject) feedData.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25);
        mMap.moveCamera(cu);


        mMap.setOnMapClickListener(latLng -> {
            lat = latLng.latitude;
            lng = latLng.longitude;
            getData(lat, lng);
            builder = new LatLngBounds.Builder();
                for(int i = 0; i<feedData.length(); i++){
                try {
                    markALocation((JSONObject) feedData.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            LatLngBounds bound = builder.build();
            CameraUpdate c = CameraUpdateFactory.newLatLngBounds(bound, 25);
            mMap.moveCamera(c);

        });
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
            builder.include(location);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            CameraUpdateFactory.zoomBy(100);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void clickedACard() {

    }
    void clickedALocation() {

    }
}
