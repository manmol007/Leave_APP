package com.example.anmol.leaveapp;

import android.app.FragmentTransaction;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppReceiveFinal extends AppCompatActivity {

    TextView t1;
    TextView t2;
    TextView t3,t5;
    TextView t4;
    ImageView accept;
    ImageView reject;
    RelativeLayout r1;
    String sub;
    CardView card;
    Button submit;
    EditText remark;
    String name=null,to;
    CreateApplicationDatabase database;
    DeleteDataDatabase deletedData;
    Intent intent;
    ArrayList<String> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_receive_final);


        accept=(ImageView)findViewById(R.id.accept);
        reject=(ImageView)findViewById(R.id.reject);
        r1=(RelativeLayout)findViewById(R.id.r1);

        card=(CardView)findViewById(R.id.card);

        database=new CreateApplicationDatabase(getApplicationContext());
        deletedData=new DeleteDataDatabase(getApplicationContext());

        intent=getIntent();
        t1=(TextView)findViewById(R.id.t1);

        t2=(TextView)findViewById(R.id.t2);

        t3=(TextView)findViewById(R.id.t3);

        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);

        String designation,currentdate,subject,countdays,fromdate,todate,category;

        to=intent.getStringExtra("to");
        designation=intent.getStringExtra("designation");
        currentdate=intent.getStringExtra("currentdate");
        subject=intent.getStringExtra("subject");
        countdays=intent.getStringExtra("countdate");
        fromdate=intent.getStringExtra("fromdate");
        todate=intent.getStringExtra("todate");
        category=intent.getStringExtra("category");

        LeaveDatabase db=new LeaveDatabase(getApplicationContext());
        data=db.getData();

        for(int i=0;i<data.size();++i){

            if(data.get(i).equals(intent.getStringExtra("from"))){
                --i;
                name=data.get(i);
                break;
            }
        }


        t1.setText("To,\nThe "+designation+",\nCompany Name.\nCompany address\n\nDate:"+currentdate+"\n");


        t3.setText("Respected Sir/Madam,\n");

        t4.setText(subject);

        t5.setText("\nThanking you\nYours Sincerely\n " + name);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletedData.WriteDeletedData("Accept",intent.getStringExtra("category"),intent.getStringExtra("from"));
                database.DeleteLeave(intent.getStringExtra("to"),intent.getStringExtra("subject"));

                Intent intent=new Intent(getApplicationContext(),thread.class);
                intent.putExtra("to",to);
                startActivity(intent);


            }
        });
    reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(AppReceiveFinal.this, R.style.MyAppTheme));
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.status, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.remark);

                dialogBuilder.setTitle("Why??");
                dialogBuilder.setMessage("Enter text below");
                dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //do something with edt.getText().toString();


                        deletedData.WriteDeletedData("Reject due to "+edt.getText(),intent.getStringExtra("category"),intent.getStringExtra("from"));
                        database.DeleteLeave(intent.getStringExtra("to"),intent.getStringExtra("subject"));
                        Intent intent=new Intent(getApplicationContext(),thread.class);
                        intent.putExtra("to",to);
                        startActivity(intent);
                    }
                });

                AlertDialog dialog=dialogBuilder.create();
                dialog.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
