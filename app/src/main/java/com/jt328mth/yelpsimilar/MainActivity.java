package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button buttonLogIn;
    private Button buttonSignUp;
    private EditText editTextUser;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LInk Java objects to widgets in UI
        buttonLogIn= (Button) findViewById(R.id.buttonLogIn);
        buttonSignUp= (Button) findViewById(R.id.buttonSignUp);
        editTextUser= (EditText) findViewById(R.id.editTextUser);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);

        //buttonLogIn.setOnClickListener(this);
        //buttonSignUp.setOnClickListener(this);
        // I dont think we can set more than 1 button on listener mode, so ...

    }

    @Override
    public void onClick(View v) {
        //handles click events
    }



}
