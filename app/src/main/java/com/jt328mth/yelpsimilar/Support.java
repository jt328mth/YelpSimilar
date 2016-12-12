package com.jt328mth.yelpsimilar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Support extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuProfile) {
            Intent gotoProfile = new Intent(Support.this, Profile.class);
            Support.this.startActivity(gotoProfile);
        } else if (item.getItemId() == R.id.menuClubList) {
            Intent gotoClubList = new Intent(Support.this, ClubList.class);
            Support.this.startActivity(gotoClubList);
        } else if (item.getItemId() == R.id.menuSupport) {
            Intent gotoSupport = new Intent(Support.this, Support.class);
            Support.this.startActivity(gotoSupport);
        } else if (item.getItemId() == R.id.menuLogout) {
            Intent gotoMain = new Intent(Support.this, MainActivity.class);
            Support.this.startActivity(gotoMain);
        }
        return super.onOptionsItemSelected(item);
    }


}
