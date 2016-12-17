package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class RateClub extends Activity implements View.OnClickListener{
    private EditText editTextClub;
    private Button buttonRate;
    private RatingBar ratingBar;
    float stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_club);

        editTextClub = (EditText) findViewById(R.id.editTextClub);
        buttonRate= (Button) findViewById(R.id.buttonRate);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        buttonRate.setOnClickListener(this);


        ratingBar.setRating(ratingBar.getRating());
        stars = ratingBar.getNumStars();

    }

    @Override
    public void onClick(View v) {
        //this doesnt work yet
        stars = (stars + ratingBar.getNumStars())/2;

        //update rating ...need to link to an actual club database

        Toast.makeText(this, "rating updated to" + stars, Toast.LENGTH_SHORT).show();

    }
}
