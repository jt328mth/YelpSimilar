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
import static android.content.ContentValues.TAG;

public class SignUp extends Activity implements View.OnClickListener{
    private Button buttonSignUp;
    private EditText editTextFirst;
    private EditText editTextLast;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignUp= (Button) findViewById(R.id.buttonSignUp);
        editTextFirst= (EditText) findViewById(R.id.editTextFirst);
        editTextLast= (EditText) findViewById(R.id.editTextLast);
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);

        buttonSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //handles click events
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (v == buttonSignUp) {
            Toast.makeText(this, "registering: " + email + password, Toast.LENGTH_SHORT).show();
            createAccount(email, password);
        }

    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
