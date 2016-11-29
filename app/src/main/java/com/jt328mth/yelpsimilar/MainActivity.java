package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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

        buttonLogIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
        // I dont think we can set more than 1 button on listener mode, so ...

    }

    @Override
    public void onClick(View v)
    {
        //handles click events
        switch  (v.getId())
        {
            //Login Event
            case R.id.buttonLogIn:
            {
                //Login Validation check
                if (editTextUser.getText().toString().equals("user") & editTextPassword.getText().toString().equals("1234"))
                {
                    //go to profile page for now
                    Intent intent = new Intent(this, Profile.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "Login Denied", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            //Sign-Up event
            case R.id.buttonSignUp:
            {
                //go to sign_up page
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
                break;
            }

        }

    }



}
