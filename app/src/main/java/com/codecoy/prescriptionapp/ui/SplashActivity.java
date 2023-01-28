package com.codecoy.prescriptionapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.codecoy.prescriptionapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
      //  getSupportActionBar().hide();

        // Fetching the stored data
        // from the SharedPreference
        SharedPreferences sp = getSharedPreferences("RCS", MODE_PRIVATE);

        String userid = sp.getString("CUID", "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              if(userid.isEmpty() || userid.equals("")){
                  startActivity(new Intent(SplashActivity.this , LogInActivity.class));
                  finish();
              }else{
                  startActivity(new Intent(SplashActivity.this , MainActivity.class).putExtra("CUID",userid));
                  finish();
              }
            }
        },3000);

    }
}