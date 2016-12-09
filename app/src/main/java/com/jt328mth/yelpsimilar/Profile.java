package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends Activity implements View.OnClickListener {
    private Button buttonUpdate;
    private EditText editTextBirthday;
    private EditText editTextGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonUpdate= (Button) findViewById(R.id.buttonUpdate);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextGender= (EditText) findViewById(R.id.editTextGender);

        buttonUpdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Event handler that handles the click event
        //needs database code
    }


}
