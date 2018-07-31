package com.example.anmol.leaveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailedActivity extends AppCompatActivity {

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4,t5;
    String name,to;
    String from;
    String currentdate;
    String subject;
    String countdays;
    String fromdate;
    String todate;
    String category;
    Button edit;
    Intent intent;
    ArrayList<String> data=new ArrayList<>();
    private LeaveDatabase db;
    private String designation=null;
    private CreateApplicationDatabase database;
    private ArrayList<String> leave=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        t1=(TextView)findViewById(R.id.t1);

        t2=(TextView)findViewById(R.id.t2);

        t3=(TextView)findViewById(R.id.t3);

        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);

        edit=(Button)findViewById(R.id.edit);

        database=new CreateApplicationDatabase(getApplicationContext());

         intent=getIntent();


        from=intent.getStringExtra("from");
        to=intent.getStringExtra("to");
        currentdate=intent.getStringExtra("currentdate");
        subject=intent.getStringExtra("subject");
        countdays=intent.getStringExtra("countdate");
        fromdate=intent.getStringExtra("fromdate");
        todate=intent.getStringExtra("todate");
        category=intent.getStringExtra("category");

        Toast.makeText(getApplicationContext(),to,Toast.LENGTH_LONG).show();

        db=new LeaveDatabase(getApplicationContext());
        leave=db.getData();

        for(int i=0;i<leave.size();++i){

            if(leave.get(i).equals(to))
            {
            ++i;++i;++i;
            designation=leave.get(i);
            break;
            }
        }


        LeaveDatabase db=new LeaveDatabase(getApplicationContext());
        data=db.getData();

        for(int i=0;i<data.size();++i){

            if(data.get(i).equals(intent.getStringExtra("from"))){
                --i;
                name=data.get(i);
                break;
            }

        }
        t1.setText("To,\n "+to+",\nCompany Name.\nCompany address\n\nDate:"+currentdate+"\n");

        t3.setText("Respected Sir/Madam,\n");

        t4.setText(subject);
        t5.setText("\nThanking you\nYours Sincerely\n " + name);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              SharedPreferences preferences=getSharedPreferences("pref",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("from",from);
                editor.apply();

                Intent intent=new Intent(getApplicationContext(),Edit.class);
                intent.putExtra("from",from);
                intent.putExtra("to",to);
                intent.putExtra("fromdate",fromdate);
                intent.putExtra("current",currentdate);
                intent.putExtra("todate",todate);
                intent.putExtra("subject",subject);
                intent.putExtra("category",category);
                intent.putExtra("countdays",countdays);
                //database.deleteLeave(from,subject);
                startActivity(intent);
            }
        });

    }
}
