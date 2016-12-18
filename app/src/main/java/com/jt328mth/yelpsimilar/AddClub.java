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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddClub extends Activity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private EditText editTextHours;
    private EditText editTextCategory;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_club);

        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextHours = (EditText) findViewById(R.id.editTextHours);
        editTextCategory = (EditText) findViewById(R.id.editTextCategory);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(AddClub.this, "User logged in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Toast.makeText(AddClub.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

    @Override
    public void onClick(View v) {

        String name = editTextName.getText().toString();
        String contact = editTextPhone.getText().toString();
        String hours = editTextHours.getText().toString();
        String category = editTextCategory.getText().toString();
        String address = editTextAddress.getText().toString();

        Club club = new Club(name, category, address, contact, hours);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Clubs");
        DatabaseReference ref2 = ref.push();
        ref2.setValue(club);
        Toast.makeText(this, "updated ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuProfile) {
            Intent gotoProfile = new Intent(AddClub.this, Profile.class);
            AddClub.this.startActivity(gotoProfile);
        } else if (item.getItemId() == R.id.menuClubList) {
            Intent gotoClubList = new Intent(AddClub.this, ClubList.class);
            AddClub.this.startActivity(gotoClubList);
        } else if (item.getItemId() == R.id.menuSupport) {
            Intent gotoSupport = new Intent(AddClub.this, Support.class);
            AddClub.this.startActivity(gotoSupport);
        } else if (item.getItemId() == R.id.menuLogout) {
            mAuth.signOut();
            Intent gotoMain = new Intent(AddClub.this, MainActivity.class);
            AddClub.this.startActivity(gotoMain);
        } else if (item.getItemId() == R.id.menuMap) {
            Intent intent = new Intent(AddClub.this, MapsActivity.class);
            AddClub.this.startActivity(intent);
        } else if (item.getItemId() == R.id.menuAddClub) {
            Intent intent2 = new Intent(AddClub.this, AddClub.class);
            AddClub.this.startActivity(intent2);
        }

        return super.onOptionsItemSelected(item);
    }
}
