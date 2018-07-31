package com.example.anmol.leaveapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager manager;
    private LoginFragment loginFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    toolbar=(Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent=getIntent();
    String str=intent.getStringExtra("LoginDesig");

    SharedPreferences preferences=getApplicationContext().getSharedPreferences("xxx",MODE_PRIVATE);
    SharedPreferences.Editor editor=preferences.edit();
    editor.putString("xxx",str).apply();


     loginFragment=new LoginFragment();
         manager=getFragmentManager();
         transaction=manager.beginTransaction();
        transaction.add(R.id.layout,loginFragment,"Login");
        transaction.commit();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
