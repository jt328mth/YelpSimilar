package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ChildEventListener;

public class ClubList extends Activity implements View.OnClickListener{
    private TextView textViewClubList;
    private Button buttonUpdate;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        textViewClubList = (TextView) findViewById(R.id.textViewClubList);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(ClubList.this, "User logged in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(ClubList.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public void onClick(View v) {
        Toast.makeText(ClubList.this, "clicked ", Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataClubs = database.getReference();
        dataClubs.child("Clubs").orderByKey().limitToLast(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Club club = dataSnapshot.getValue(Club.class);
                String val = textViewClubList.getText().toString();
                val = val + "\n Club: " + club.name
                        + "\n Address: " + club.location
                        + "\n hours: " + club.hours + "\n";
                textViewClubList.setText(val);
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuProfile) {
            Intent gotoProfile = new Intent(ClubList.this, Profile.class);
            ClubList.this.startActivity(gotoProfile);
        } else if (item.getItemId() == R.id.menuClubList) {
            Intent gotoClubList = new Intent(ClubList.this, ClubList.class);
            ClubList.this.startActivity(gotoClubList);
        } else if (item.getItemId() == R.id.menuSupport) {
            Intent gotoSupport = new Intent(ClubList.this, Support.class);
            ClubList.this.startActivity(gotoSupport);
        } else if (item.getItemId() == R.id.menuLogout) {
            mAuth.signOut();
            Intent gotoMain = new Intent(ClubList.this, MainActivity.class);
            ClubList.this.startActivity(gotoMain);
        }
        return super.onOptionsItemSelected(item);
    }

}
