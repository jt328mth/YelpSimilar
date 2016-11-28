package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Activity implements View.OnClickListener{
    private Button buttonSignUp;
    private EditText editTextFirst;
    private EditText editTextLast;
    private EditText editTextEmail;
    private EditText editTextPassword;

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
    }
}
