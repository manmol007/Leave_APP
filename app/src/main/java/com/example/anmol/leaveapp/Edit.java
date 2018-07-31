package com.example.anmol.leaveapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Edit extends AppCompatActivity {

    Spinner towhom;
    LeaveDatabase db;
    private int mYear,mMonth,mDay;
    TextView date,fromdate,todate,subject,last;
    ArrayList<String> emails=new ArrayList<>();
    ArrayList<String> Towhom=new ArrayList<>();
    CreateApplicationDatabase database;
    String time4,time3;
    EditText main;
    Long countdays;
    Long time2,time1;
    String name;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

    towhom=(Spinner)findViewById(R.id.towhom);
    date=(TextView)findViewById(R.id.date);
    main=(EditText)findViewById(R.id.main);
        fromdate=(TextView)findViewById(R.id.fromdate);
        todate=(TextView)findViewById(R.id.todate);
    last=(TextView)findViewById(R.id.last);
    submit=(Button)findViewById(R.id.Submit);

    final Intent intent=getIntent();

database=new CreateApplicationDatabase(getApplicationContext());

        db=new LeaveDatabase(getApplicationContext());

        emails=db.getData();

        for(int i=0;i<emails.size();++i){

            if(emails.get(i).equals("Head of Department")||emails.get(i).equals("Administrative")){

                --i;--i;--i;
                Towhom.add(emails.get(i));
                ++i;++i;++i;

            }

        }


        for(int i=0;i<emails.size();++i){

            if(emails.get(i).equals(intent.getStringExtra("from"))){
                --i;
                name=emails.get(i);
                break;
            }

        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.spin,Towhom);

        towhom.setAdapter(arrayAdapter);


        date.setText(intent.getStringExtra("current"));
        fromdate.setText(intent.getStringExtra("fromdate"));
        todate.setText(intent.getStringExtra("todate"));

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog=new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        c.set(i,i1,i2);
                        time3=i2 + "-" + (i1 + 1) + "-" + i;
                        fromdate.setText(time3);

                        time1=c.getTimeInMillis();

                    }
                },mYear,mMonth,mDay);

                dialog.getDatePicker().setMinDate(c.getTimeInMillis());

                dialog.show();

            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog=new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        c.set(i,i1,i2);
                        time4=i2 + "-" + (i1 + 1) + "-" + i;
                        todate.setText(time4);

                        time2=c.getTimeInMillis();

                    }
                },mYear,mMonth,mDay);

                dialog.getDatePicker().setMinDate(c.getTimeInMillis());

                dialog.show();
            }
        });

        last.setText("Thanking you\nYours Sincerely\n " + name);

        if(intent.getStringExtra("category").equals("Sick Leave")) {

            main.setText(intent.getStringExtra("subject"));
        }
        else if(intent.getStringExtra("category").equals("Casual Leave")) {


            main.setText(intent.getStringExtra("subject"));}

        else if(intent.getStringExtra("category").equals("Privilege Leave")) {


            main.setText(intent.getStringExtra("subject"));        }

        else if(intent.getStringExtra("category").equals("Festival Leave")) {


            main.setText(intent.getStringExtra("subject"));   }

        else if(intent.getStringExtra("category").equals("Maternity Leave")) {


            main.setText(intent.getStringExtra("subject"));}

        else {


            main.setText(intent.getStringExtra("subject"));   }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content=main.getText().toString();

                String to0whom=towhom.getSelectedItem().toString();

                String fromwhom=intent.getStringExtra("from");

                countdays=((time2-time1)/(24*60*60*1000))+1;

                String categoRY=intent.getStringExtra("category");

                if(TextUtils.isEmpty(content)||time3==null||time4==null||TextUtils.isEmpty(to0whom)) {

               Toast.makeText(getApplicationContext(),"Incomplete fields",Toast.LENGTH_LONG).show();

                }
                else{

                    database.deleteLeave(intent.getStringExtra("from"),intent.getStringExtra("subject"));

                        database.WriteLeave(fromwhom, to0whom, content, time3, time4, Long.toString(countdays), intent.getStringExtra("current"), categoRY);

                        startActivity(new Intent(getApplicationContext(), FinalThread.class));
                    }

            }
        });


    }
}
