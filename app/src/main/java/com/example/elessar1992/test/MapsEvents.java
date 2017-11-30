package com.example.elessar1992.test;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.PopupMenuCompat;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;


import com.example.elessar1992.test.Model.Explore;
import com.example.elessar1992.test.Service.FourSquareService;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import android.widget.PopupMenu.OnMenuItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by elessar1992 on 11/22/17.
 */

public class MapsEvents extends FragmentActivity implements OnMapReadyCallback
{

    private static final String TAG = MapsEvents.class.getSimpleName();

    private GoogleMap mMap;
    private List<Marker> locations = new ArrayList<>();
    //private MarkerOptions markerOptions = new MarkerOptions();

    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    protected FusedLocationProviderClient mFusedLocationProviderClient;


    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;
    static LatLng latilong;

    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    String Client_ID = "BSQDX22EU4YYHOGSSPF2NHFC4QWNM1XWYNRUTTWRB3DJTBPW";
    String Client_Secret = "IRP4Y2X0CENP0QDV24ASTRG3M4WTYUPPPE2YMKO2CVVSXRJV";
    String apiVersion = "20130815";
    //String geoLocation = "40.7,-74";

    String ll;
    //Double lat = mLastKnownLocation.getLatitude();
    //Double lng = mLastKnownLocation.getLongitude();

    String query;
    String radius = "1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //View view;
        //PopupMenu popupMenu = new PopupMenu(MapsEvents.this, view);
        //popupMenu.setOnMenuItemClickListener(MapsEvents.this);
        //popupMenu.inflate(R.menu.popup_menu);
        //popupMenu.show();

        //Initialize Google Play Services
        Button Restaurant = (Button) findViewById(R.id.Restaurant);
        Restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                //exploreAsyncTask.execute();
                build_retrofit_and_get_response("restaurant");
            }
        });

        Button Art = (Button) findViewById(R.id.Art);
        Art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                //exploreAsyncTask.execute();
                build_retrofit_and_get_response("art");
            }
        });

        Button Tech = (Button) findViewById(R.id.Tech);
        Tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                //exploreAsyncTask.execute();
                build_retrofit_and_get_response("tech");
            }
        });

        Button Nightclub = (Button) findViewById(R.id.Nightclub);
        Nightclub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //ShowEvent.ExploreAsyncTask exploreAsyncTask = new ShowEvent.ExploreAsyncTask();
                //exploreAsyncTask.execute();
                build_retrofit_and_get_response("nightclub");
            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                LatLng placelatlng = place.getLatLng();
                String name = (String) place.getName();
                Marker marker = mMap.addMarker(new MarkerOptions().position(placelatlng).title(name));
                marker.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(placelatlng));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);

            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }




    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //LatLng sydney = new LatLng( );
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        updateLocationUI();

        getDeviceLocation();


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    private void getLocationPermission()
    {
        /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override


    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener()
                {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if (task.isSuccessful())
                        {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                            String lat = Double.toString(mLastKnownLocation.getLatitude());
                            String lng = Double.toString(mLastKnownLocation.getLongitude());

                            ll = lat + ',' +lng;
                        }
                        else
                        {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }

                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    /*public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_comedy:
                Toast.makeText(this, "Comedy Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_movies:
                Toast.makeText(this, "Movies Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_music:
                Toast.makeText(this, "Music Clicked", Toast.LENGTH_SHORT).show();
                return true;
        }

        return true;
    }*/



    List<LatLng> list = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<Double> ratingList = new ArrayList<>();
    List<String> priceList = new ArrayList<>();
    MarkerOptions markerOptions = new MarkerOptions();

    private void build_retrofit_and_get_response(String query)
    {
        FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
        Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, ll, query, radius);
        call.enqueue(new Callback<Explore>()
        {
            @Override
            //public void onResponse(Response<Explore> response, Retrofit retrofit)
            public void onResponse(Call<Explore> call, Response<Explore> response)
            {
                try
                {
                    list.clear();
                    nameList.clear();
                    ratingList.clear();
                    priceList.clear();
                    mMap.clear();
                    Log.i(TAG, "onResponse:"+ response.body().getResponse().getGroups().get(0).getItems().size());
                    Double rating = null;
                    int tier;
                    String tierr = null;
                    for (int i = 0; i < response.body().getResponse().getGroups().get(0).getItems().size(); i++)
                    {
                        double lat = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getLocation().getLat();
                        double lng = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getLocation().getLng();
                        String name = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getName();

                        if(response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getPrice() == null){
                            tier = 0;
                        } else{
                            tier = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getPrice().getTier();
                            Log.i(TAG, "onResponse: tier is " + response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getPrice().getTier());
                        }
                        //tier = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getPrice();
                        Log.i(TAG, "onResponse: tier is " + response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getPrice());


                        if(tier == 0)
                        {
                            tierr = "null";
                        }
                        else if(tier == 1)
                        {
                            tierr ="$";
                        }
                        else if(tier ==2)
                        {
                            tierr = "$$";
                        }
                        else if(tier == 3)
                        {
                            tierr = "$$$";
                        }
                        else if(tier ==4)
                        {
                            tierr = "$$$$";
                        }
                        else if(tier == 5)
                        {
                            tierr = "$$$$$";
                        }

                        //response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().setRating(2.0);
                        rating = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getRating();

                        Log.i(TAG, "onResponse: rating is " + rating);

                        ratingList.add(rating);
                        nameList.add(name);
                        LatLng latLng = new LatLng(lat, lng);
                        list.add(latLng);
                        priceList.add(tierr);
                    }

                    for(int i = 0; i < list.size(); i ++)
                    {

                        markerOptions.position(list.get(i));
                        markerOptions.title(nameList.get(i));
                        if(ratingList.get(i) == null)
                        {
                            if(priceList.get(i) == "null")
                            {
                                markerOptions.snippet("Is not rated yet" + "Rating is " + priceList.get(i).toString() + " stars " + "No price Yet");
                            }
                            else
                            {
                                markerOptions.snippet("Is not rated yet" + "Price is " + priceList.get(i).toString());
                            }

                        }
                        else
                        {
                            if(priceList.get(i) == "null")
                            {
                                markerOptions.snippet("Rating is " + ratingList.get(i).toString() + " stars " + "No price Yet");
                            }
                            else
                            {
                                markerOptions.snippet("Rating is " + ratingList.get(i).toString() + " stars " + "Price is " + priceList.get(i).toString());
                            }

                        }




                        mMap.addMarker(markerOptions);

                    }

//                    for(LatLng point : list) {
//                        markerOptions.position(point);
//                        markerOptions.title("");
//                        mMap.addMarker(markerOptions);
//                    }
//
//                    for (int i = 0; i < response.body().getResponse().getGroups().get(0).getItems().size(); i++)
//                    {
//
//                        //for(int j = 0; j < locations.size(); j++)
//                        //{
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        //double lat = response.body().getResponse().getGroups().get(i).getItems().get(i).getVenue().getLocation().getLat();
//                        //double lng = response.body().getResponse().getGroups().get(i).getItems().get(i).getVenue().getLocation().getLng();
//                        double lat = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getLocation().getLabeledLatLngs().get(i).getLat();
//                        double lng = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getLocation().getLabeledLatLngs().get(i).getLng();
//                        String name = response.body().getResponse().getGroups().get(0).getItems().get(i).getVenue().getName();
//                        //double rating = response.body().getResponse().getGroups().get(i).getItems().get(i).getVenue().getRating();
//                        //Integer tier = response.body().getResponse().getGroups().get(i).getItems().get(i).getVenue().getPrice().getTier();
//                        //String currency = response.body().getResponse().getGroups().get(i).getItems().get(i).getVenue().getPrice().getCurrency();
//                        LatLng latLng = new LatLng(lat, lng);
//                        //latlngs.add(new LatLng(lat, lng));
//                        //markerOptions.position(latLng) shows the location
//                        markerOptions.position(latLng);
//                        markerOptions.title(name);
//                        mMap.addMarker(markerOptions);
//                        //Marker m = mMap.addMarker(markerOptions);
//                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//                        // --------------------
//                        //}
//                    }

                }
                catch (Exception e)
                {
                    Log.d("onResponse", "There is an error " + e.getMessage() );
                    e.printStackTrace();
                }
            }



            @Override
            public void onFailure(Call<Explore> call, Throwable t)
            {
                Log.d("onFailure", t.toString());
            }
        });
    }


}
