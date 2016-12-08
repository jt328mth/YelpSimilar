package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_Logout){
            Intent gotoMainActivity = new Intent(Profile.this, MainActivity.class);
            Profile.this.startActivity(gotoMainActivity);
        } else if (item.getItemId() == R.id.menu_Profile) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        //Event handler that handles the click event
        //needs database code
    }
}
