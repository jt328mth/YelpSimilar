package com.jt328mth.yelpsimilar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng michigan = new LatLng(44.3148, -85.6024);
        mMap.addMarker(new MarkerOptions().position(michigan).title("Michigan"));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(michigan));
    }

    public void onSearch( View view) {
        //Fetch search value entered by user
        EditText locationSearch = (EditText) findViewById (R.id.editTextSearchAddress );
        String location = locationSearch.getText().toString();


        Location mapLocation = new Location(location);

        List<Address> addressList = null; //not using List<Address> cause geocoder isn't working

        if(location != null || !location.equals(""))
        {

            Geocoder geocoder = new Geocoder(this);

            try {

                addressList = geocoder.getFromLocationName(location, 1); //Does not return a value.
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        //Address address = addressList.get(0);  //geocoder doesn't work
        LatLng latLng = new LatLng(mapLocation.latitude, mapLocation.longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(mapLocation.name));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuProfile) {
            Intent gotoProfile = new Intent(MapsActivity.this, Profile.class);
            MapsActivity.this.startActivity(gotoProfile);
        } else if (item.getItemId() == R.id.menuClubList) {
            Intent gotoClubList = new Intent(MapsActivity.this, ClubList.class);
            MapsActivity.this.startActivity(gotoClubList);
        } else if (item.getItemId() == R.id.menuSupport) {
            Intent gotoSupport = new Intent(MapsActivity.this, Support.class);
            MapsActivity.this.startActivity(gotoSupport);
        } else if (item.getItemId() == R.id.menuLogout) {
            Intent gotoMain = new Intent(MapsActivity.this, MainActivity.class);
            MapsActivity.this.startActivity(gotoMain);
        }
        return super.onOptionsItemSelected(item);
    }

}
