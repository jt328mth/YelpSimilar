package com.jt328mth.yelpsimilar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.jt328mth.yelpsimilar.R.id.add;
import static com.jt328mth.yelpsimilar.R.id.textViewClubList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(MapsActivity.this, "User logged in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(MapsActivity.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                }

            }
        };

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
        LatLng annarbor = new LatLng(42.2808, -83.7430);
        mMap.addMarker(new MarkerOptions().position(annarbor).title("Ann Arbor"));
        addClubMarkers();

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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(annarbor));
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
        mMap.addMarker(new MarkerOptions().position(latLng).title(mapLocation.name).icon(BitmapDescriptorFactory.defaultMarker(0)));
        addClubMarkers();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


    }


    //Clubs have already been added to Firebase. This is just for illustrative purposes during our demo
    public void addClubMarkers()
    {

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2813188,-83.7537965))
                .title("The Last Word").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2696906,-83.7768647))
                .title("Wolverine State Brewing Co").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2780848,-83.7432318))
                .title("Ashley's").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2839674,-83.7494898))
                .title("The Bar At 327 Braun Court").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.3087262,-83.8890772))
                .title("The Beer Grotto").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2791129,-83.7440348))
                .title("Hopcat").icon(BitmapDescriptorFactory.defaultMarker(90)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2704209,-83.7513767))
                .title("VinBar").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2798109,-83.7518859))
                .title("Old Town").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2805582,-83.7489373))
                .title("Mash").icon(BitmapDescriptorFactory.defaultMarker(90)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2653934,-83.7488559))
                .title("Arbor Brewing Company").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2802052,-83.7506305))
                .title("The Ravens Club").icon(BitmapDescriptorFactory.defaultMarker(90)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.279797,-83.7515028))
                .title("Alley Bar").icon(BitmapDescriptorFactory.defaultMarker(90)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(41.658616,-91.5351737))
                .title("Share Wine Lounge and Small Plate Bistro").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.273149,-83.7408949))
                .title("Dominick's").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2571766,-83.7290534))
                .title("Fraser's Pub").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2748932,-83.7357198))
                .title("Blue Leprechaun").icon(BitmapDescriptorFactory.defaultMarker(90)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2748043,-83.7370198))
                .title("Good Time Charleys").icon(BitmapDescriptorFactory.defaultMarker(90)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2788905,-83.7445772))
                .title("Scorekeepers").icon(BitmapDescriptorFactory.defaultMarker(0)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2792076,-83.7445872))
                .title("Necto").icon(BitmapDescriptorFactory.defaultMarker(90)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2843922,-83.7588305))
                .title("LIVE").icon(BitmapDescriptorFactory.defaultMarker(90)));

        mMap.addMarker(new MarkerOptions().position( new LatLng(42.2790471,-83.7510093))
                .title("Rush").icon(BitmapDescriptorFactory.defaultMarker(90)));


        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataClubs = database.getReference();
        dataClubs.child("Clubs").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Club club = dataSnapshot.getValue(Club.class);
                Toast.makeText(MapsActivity.this, club.name, Toast.LENGTH_SHORT).show();
                //String val = textViewClubList.getText().toString();
                //val = val + "\n Club: " + club.name
                  //      + "\n Address: " + club.location
                    //    + "\n hours: " + club.hours + "\n";
                //textViewClubList.setText(val);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


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
            mAuth.signOut();
            Intent gotoMain = new Intent(MapsActivity.this, MainActivity.class);
            MapsActivity.this.startActivity(gotoMain);
        }
        return super.onOptionsItemSelected(item);
    }

}
