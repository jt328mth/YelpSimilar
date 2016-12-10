package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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

import static android.content.ContentValues.TAG;


public class SignUp extends Activity implements View.OnClickListener{
    private Button buttonSignUp;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignUp= (Button) findViewById(R.id.buttonSignUp);
        editTextName= (EditText) findViewById(R.id.editTextName);
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);

        buttonSignUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(SignUp.this, "User logged in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(SignUp.this, "User Signed Out", Toast.LENGTH_SHORT).show();
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
        //handles click events
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        //String name = editTextFirst.getText().toString() + " " + editTextLast.getText().toString();

        if (v == buttonSignUp) {
            Toast.makeText(this, "registering: " + email + password, Toast.LENGTH_SHORT).show();
            createAccount(email, password);
            signIn(email, password);
        }

    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUp.this, "Login Successful - moving to Profile page", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUp.this, Profile.class);
                            startActivity(intent);
                        }

                    }
                });
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            // Email Set
                            DatabaseReference ref = db.getReference("users").child(mAuth.getCurrentUser().getUid()).child("email");
                            ref.setValue(mAuth.getCurrentUser().getEmail());
                            // Name Set
                            DatabaseReference refname = db.getReference("users").child(mAuth.getCurrentUser().getUid()).child("name");
                            refname.setValue(editTextName.getText().toString());
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "createUserWithEmailAndPassword:failed", task.getException());
                            Log.w(TAG, " "+ task.getException().getMessage());
                            Toast.makeText(SignUp.this,"auth error:"+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            //showProgress(false);

                        }
                    }
                });
    }




}
