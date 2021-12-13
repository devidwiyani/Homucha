package com.example.homucha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {
    static DbHelper dbHelper;
    String mainUser;
    protected Cursor cursor;
    String name, email, address, phone;
    public Bundle bundleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle login = getIntent().getExtras();
        mainUser = login.getString("UserNameLogin");

        dbHelper = new DbHelper(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        DbHelper dbHelper = new DbHelper(DashboardActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_user WHERE username = '" +
                getIntent().getStringExtra("UserNameLogin") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            name = cursor.getString(3).toString();
            email = cursor.getString(6).toString();
            address = cursor.getString(4).toString();
            phone = cursor.getString(5).toString();
        }

        bundleFragment = new Bundle();
        bundleFragment.putString("sendName", "name");
        bundleFragment.putString("sendEmail", "email");
        bundleFragment.putString("sendAddress", "address");
        bundleFragment.putString("sendPhone", "phone");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_profil:
                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(bundleFragment);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public String getNameFragment(){
        return name;
    }

    public String getMailFragment(){
        return email;
    }

    public String getAdressFragment(){
        return address;
    }

    public String getPhoneFragment(){
        return phone;
    }

}

