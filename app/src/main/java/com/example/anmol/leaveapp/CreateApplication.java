package com.example.anmol.leaveapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class CreateApplication extends android.support.v4.app.Fragment {

    private Button submit;
    private TextInputEditText subject;
    private String sentto;
    private String sentsubject;
    private int f=0;
    private String from;
    private CreateApplicationDatabase database;
    private LeaveDatabase db;
    private ArrayList<String> emails;
    private int flag=0;
    private String to1,from1,fromDate1,toDate1,sub1,Category1;
    private TextView FromText;
    private String categoRY;
    private TextView ToText;
    private int mYear,mMonth,mDay;
    private Spinner category;
     long time1,time2,result,result1,result2;
     String time3,time4;
    private Date Currentdate;
    private Spinner towhom;
    private ArrayList<String> Towhom=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.createapplication,container,false);


        database = new CreateApplicationDatabase(getActivity());
        db=new LeaveDatabase(getActivity());

        category=(Spinner)v.findViewById(R.id.category);

        towhom=(Spinner)v.findViewById(R.id.towhom);

        String[] cat={"Sick Leave","Casual Leave","Privilege Leave","Festival Leave","Maternity Leave","Leave without pay"};

        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,cat);

        category.setAdapter(adapter);

        emails=db.getData();

        for(int i=0;i<emails.size();++i){

            if(emails.get(i).equals("Head of Department")||emails.get(i).equals("Administrative")){

                --i;--i;--i;
                Towhom.add(emails.get(i));
                ++i;++i;++i;

            }

        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,Towhom);

        towhom.setAdapter(arrayAdapter);

        submit=(Button)v.findViewById(R.id.Submit);
        subject=(TextInputEditText)v.findViewById(R.id.Subject);

        FromText=(TextView)v.findViewById(R.id.fromtext);
        ToText=(TextView) v.findViewById(R.id.totext);


        FromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        time3=i2 + "-" + (i1 + 1) + "-" + i;

                        FromText.setText(time3);
                        c.set(i,i1,i2);

                        time1=c.getTimeInMillis();


                    }
                },mYear,mMonth,mDay);

                dialog.getDatePicker().setMinDate(c.getTimeInMillis());

                dialog.show();

            }
        });


        ToText.setOnClickListener(new View.OnClickListener() {
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
                        ToText.setText(time4);

                        time2=c.getTimeInMillis();

                    }
                },mYear,mMonth,mDay);

                dialog.getDatePicker().setMinDate(c.getTimeInMillis());

                dialog.show();

            }
        });

        Intent intent=getActivity().getIntent();
        from=intent.getStringExtra("From");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sentto=towhom.getSelectedItem().toString();
                sentsubject=subject.getText().toString();
                categoRY=category.getSelectedItem().toString();

                String sub;

                result=((time2-time1)/(24*60*60*1000))+1;

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
                String s= mdformat.format(calendar.getTime());

                if(category.equals("Sick Leave")) {

                    sub="It is to notify you that I am suffering from "+sentsubject+" and currently facing health issues.On these grounds of being sick, I am not able to join office for "+result+" days. I want you to kindly consider my please and grant me leave from"+time3+" to"+time4+". I shall be utterly obliged.";
                }
                else if(category.equals("Casual Leave")) {

                    sub="This is to request you to kindly grant me a casual leave for "+result+" days i.e. "+time3+" to"+time4+". I need this leave because of "+sentsubject+" which is unavoidable.\n\nI request you to grant me leave for " +result + " days.";
                }

                else if(category.equals("Privilege Leave")) {

                    sub="With dues respect I would like to inform you that from past so many months I havnt completed my leaves so I want you to give me leave for " + sentsubject + " from my left leaves .Hence I require leave for " + result + " days i.e. from " + time3 + " to " + time4 + ".\n\nI request you to grant me leave for " + result+ " days.";
                }

                else if(category.equals("Festival Leave")) {

                    sub="I would like to state that our festival of " +sentsubject+" is coming. In our culture it is celebrated with great anticipation. Due to which I wont be able to come to job.\n\nKindly grant "+result+" days leave to be a part of this festival i.e." +time3+" to "+ time4+".";
                }

                else if(category.equals("Maternity Leave")) {

                    sub="Most respectfully I am requesting you to leave for "+ result +" days from "+ time3+" to " + time4+"Because I am pregnent and my family doctor suggests me bed rest as for few days before and after delivery because of "+sentsubject+".During my absense my colleague will handle all the routine tasks as I have already given him the details.\n\nI request you to grant me leave for " +result + " days.";
                }

                else {

                    sub="I must regretfully request a "+result+" days leave starting from " +time3+" to "+time4+" because of the "+sentsubject+".During my absense my colleague will handle all the routine tasks as I have already given him the details.\n\nI request you to grant me leave for " + result + " days.";
                }


                if(TextUtils.isEmpty(sentsubject)){
                    Toast.makeText(getActivity(),"Fields are empty",Toast.LENGTH_LONG).show();
                }
                else if(result<1){
                    Toast.makeText(getActivity(),"Invalid Date",Toast.LENGTH_LONG).show();

                }
                    else {
                        subject.setText("");
                        database.WriteLeave(from, sentto, sub, time3, time4, Long.toString(result), s, categoRY);
                    }
                        }
        });
        return v;
    }

}
