package com.example.anmol.leaveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

public class thread extends AppCompatActivity {

    private CreateApplicationDatabase database;
    private ArrayList<String> data=new ArrayList<>();
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager pager;
    private String desig;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Intent intent=getIntent();

        if(intent.getStringExtra("From")!=null) {
            from = intent.getStringExtra("From");
        }

        desig=intent.getStringExtra("Designation");

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabs=(TabLayout)findViewById(R.id.tabs);

        pager=(ViewPager)findViewById(R.id.pager);

        setPager(pager);

        tabs.setupWithViewPager(pager);
    }

    public void setPager(ViewPager pager){

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());

        AppReceive sent = new AppReceive();
        Bundle bndl = new Bundle();
        bndl.putString("from", from);
        bndl.putString("designation",desig);
        sent.setArguments(bndl);
        adapter.addFragment(sent, "Application Received");

        adapter.notifyDataSetChanged();
        pager.setAdapter(adapter);


    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> FragmntList=new ArrayList<>();
        private ArrayList<String> FragmentContent=new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return FragmntList.get(position);
        }

        @Override
        public int getCount() {
            return FragmntList.size();
        }

        public void addFragment(Fragment fragment, String title){
            FragmntList.add(fragment);
            FragmentContent.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return FragmentContent.get(position);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }


}
