package com.example.resepikita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
    public void Search(View v){
        Intent intent = new Intent(Dashboard.this ,Search.class);
        startActivity(intent);
        finish();
    }
    public void Profile(View v){
        Intent intent = new Intent(Dashboard.this ,Profile.class);
        startActivity(intent);
        finish();
    }
}
