package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddClub extends Activity implements View.OnClickListener {
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

    }
}
