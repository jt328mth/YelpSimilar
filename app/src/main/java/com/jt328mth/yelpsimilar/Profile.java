package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Activity implements View.OnClickListener {
    private Button buttonUpdate;
    private EditText editTextBirthday;
    private EditText editTextGender;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextGender = (EditText) findViewById(R.id.editTextGender);

        buttonUpdate.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(Profile.this, "User logged in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(Profile.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                }

            }
        };

        //populate current gender and birthday

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference("users").child(mAuth.getCurrentUser().getUid());
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{editTextGender.setText(dataSnapshot.child("gender").getValue().toString());
                    editTextBirthday.setText("" + dataSnapshot.child("birthday").getValue().toString());}
                catch(Exception ex){}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        String birthday = editTextBirthday.getText().toString();
        String gender = editTextGender.getText().toString();

        if (v == buttonUpdate) {

            Toast.makeText(this, "updating ", Toast.LENGTH_SHORT).show();
            updateAccount(birthday, gender);
        }
    }

    public void updateAccount(String email, String password) {
        // Write  to the database
        String birthday = editTextBirthday.getText().toString();
        String gender = editTextGender.getText().toString();

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        //set birthday
        DatabaseReference refbirthday = db.getReference("users").child(mAuth.getCurrentUser().getUid()).child("birthday");
        refbirthday.setValue(editTextBirthday.getText().toString());

        //set gender
        DatabaseReference refgender = db.getReference("users").child(mAuth.getCurrentUser().getUid()).child("gender");
        refgender.setValue(editTextGender.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuProfile) {
            Intent gotoProfile = new Intent(Profile.this, Profile.class);
            Profile.this.startActivity(gotoProfile);
        } else if (item.getItemId() == R.id.menuClubList) {
            Intent gotoClubList = new Intent(Profile.this, ClubList.class);
            Profile.this.startActivity(gotoClubList);
        } else if (item.getItemId() == R.id.menuSupport) {
            Intent gotoSupport = new Intent(Profile.this, Support.class);
            Profile.this.startActivity(gotoSupport);
        } else if (item.getItemId() == R.id.menuLogout) {
            mAuth.signOut();
            Intent gotoMain = new Intent(Profile.this, MainActivity.class);
            Profile.this.startActivity(gotoMain);
        }
        return super.onOptionsItemSelected(item);
    }
}
