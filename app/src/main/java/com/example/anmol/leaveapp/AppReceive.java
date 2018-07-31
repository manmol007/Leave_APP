package com.example.anmol.leaveapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AppReceive extends android.support.v4.app.Fragment{

    RecyclerView list;
    private ArrayList<String> Headsubject=new ArrayList<>();
    private ArrayList<String> Headtowhom=new ArrayList<>();
    private ArrayList<String> Headfrom=new ArrayList<>();
    private ArrayList<String> data=new ArrayList<>();
    private ArrayList<String> fromdate = new ArrayList<>();
    private ArrayList<String> todate = new ArrayList<>();
    private ArrayList<String> countdate = new ArrayList<>();
    private ArrayList<String> currentdate = new ArrayList<>();
    private ArrayList<String> category = new ArrayList<>();
    private CreateApplicationDatabase database;
    private myAdapter adapter;
    private String email,toemail;
    private String name,name2;
    private ArrayList<String> data1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.appreceive,container,false);

        list=(RecyclerView)view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setHasFixedSize(true);

        email=null;

        email=getArguments().getString("from");

        toemail=getActivity().getIntent().getStringExtra("to");

        LeaveDatabase lleave=new LeaveDatabase(getActivity());
        data1=lleave.getData();

      database=new CreateApplicationDatabase(getActivity());

      if(email!=null) {
          for (int i=0;i<data1.size();++i){
              if(data1.get(i).equals(email)){
                  --i;
                  name=data1.get(i);
                  break;
              }
          }
       }
      else
          {
          name=toemail;
          Log.i("to",name);
      }


        Log.i("frommm",name);


        data = database.getReceive(name);

        Log.i("database",data.toString());

        for (int i=0;i<data.size();++i){

            Headfrom.add(data.get(i));
            ++i;
            Headtowhom.add(data.get(i));
            ++i;
            Headsubject.add(data.get(i));
            ++i;
            fromdate.add(data.get(i));
            ++i;
            todate.add(data.get(i));
            ++i;
            countdate.add(data.get(i));
            ++i;
            currentdate.add(data.get(i));
            ++i;
            category.add(data.get(i));
        }
        adapter=new myAdapter(Headtowhom,Headsubject,Headfrom,fromdate,todate,countdate,currentdate,category);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
        }


    public class myAdapter extends RecyclerView.Adapter<myAdapter.myHolder>{


        private ArrayList<String> subject=new ArrayList<>();
        private ArrayList<String> towhom=new ArrayList<>();
        private ArrayList<String> from=new ArrayList<>();
        private ArrayList<String> fromdate = new ArrayList<>();
        private ArrayList<String> todate = new ArrayList<>();
        private ArrayList<String> countdate = new ArrayList<>();
        private ArrayList<String> currentdate = new ArrayList<>();
        private ArrayList<String> category = new ArrayList<>();

        public myAdapter(ArrayList<String> towhom,ArrayList<String> subject,ArrayList<String> from, ArrayList<String> fromdate, ArrayList<String> todate, ArrayList<String> countdate,ArrayList<String> currentdate,ArrayList<String> category) {
            this.subject = subject;
            this.towhom  = towhom;
            this.from=from;
            this.fromdate=fromdate;
            this.todate=todate;
            this.countdate=countdate;
            this.currentdate=currentdate;
            this.category=category;
            //Toast.makeText(getActivity(),Integer.toString(this.subject.size()),Toast.LENGTH_LONG).show();
        }

        @NonNull
        @Override
        public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.sentlayout,parent,false);
            return new myHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull myHolder holder, final int position) {


            holder.to.setText(category.get(position));
            holder.subject.setText(from.get(position));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(getActivity(),AppReceiveFinal.class);
                    intent.putExtra("from",from.get(position));
                    intent.putExtra("to",towhom.get(position));
                    intent.putExtra("subject",subject.get(position));
                    intent.putExtra("fromdate", fromdate.get(position));
                    intent.putExtra("todate", todate.get(position));
                    intent.putExtra("countdate", countdate.get(position));
                    intent.putExtra("designation",getArguments().getString("designation"));
                    intent.putExtra("currentdate",currentdate.get(position));
                    intent.putExtra("category",category.get(position));
                    startActivity(intent);
                }
            });



        }

        @Override
        public int getItemCount() {
            return subject.size();
        }

        public class myHolder extends RecyclerView.ViewHolder{

            View view;
            TextView to;
            TextView subject;

            public myHolder(View itemView) {
                super(itemView);
                view=itemView;
                to=(TextView)itemView.findViewById(R.id.towhom);
                subject=(TextView)itemView.findViewById(R.id.title);
            }
        }
    }
}
