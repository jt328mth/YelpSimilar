package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        buttonUpdate= (Button) findViewById(R.id.buttonUpdate);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextGender= (EditText) findViewById(R.id.editTextGender);

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
        String birthday = editTextBirthday.getText().toString();
        String gender = editTextGender.getText().toString();

        if (v == buttonUpdate) {
            Toast.makeText(this, "updating ", Toast.LENGTH_SHORT).show();
            updateAccount(birthday, gender);
        }
    }

    public void updateAccount(String email, String password) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        //set birthday
        DatabaseReference refbirthday = db.getReference("users").child(mAuth.getCurrentUser().getUid()).child("birthday");
        refbirthday.setValue(editTextBirthday.getText().toString());

        //set gender
        DatabaseReference refgender = db.getReference("users").child(mAuth.getCurrentUser().getUid()).child("gender");
        refgender.setValue(editTextGender.getText().toString());
    }


}
