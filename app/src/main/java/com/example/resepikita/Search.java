package com.example.resepikita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    public void Home(View v){
        Intent intent = new Intent(Search.this ,Dashboard.class);
        startActivity(intent);
        finish();
    }
    public void Profile(View v){
        Intent intent = new Intent(Search.this ,Profile.class);
        startActivity(intent);
        finish();
    }
}