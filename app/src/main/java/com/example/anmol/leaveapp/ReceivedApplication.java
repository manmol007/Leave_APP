package com.example.anmol.leaveapp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ReceivedApplication extends android.support.v4.app.Fragment {

    private RecyclerView list;
    ArrayList<String> notific;
    ArrayList<String> status;
    ArrayList<String> sub;
    DeleteDataDatabase db;
    private String from;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.receiveapplication,container,false);

        list=(RecyclerView)view.findViewById(R.id.list);

        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        list.setHasFixedSize(true);

        from=getArguments().getString("from");

        notific=new ArrayList<>();
        sub=new ArrayList<>();
        status=new ArrayList<>();

        db=new DeleteDataDatabase(getActivity());
        if(from!=null) {

            notific = db.getDeletedData(from);
        }
        else{
            SharedPreferences preferences=getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            String usr=preferences.getString("from",null);
            notific=db.getDeletedData(usr);
        }
        for (int i=0;i<notific.size();++i){

            status.add(notific.get(i));
            ++i;
            sub.add(notific.get(i));

        }

        Myadapter adapter=new Myadapter(status,sub);

        list.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return view;
    }

    public class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder>{

        ArrayList<String> status=new ArrayList<>();

        ArrayList<String> sub=new ArrayList<>();

        public Myadapter(ArrayList<String> status,ArrayList<String> sub){

            this.status=status;
            this.sub=sub;

        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.receivelayout,parent,false);

            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {


            String str=status.get(position).substring(0,6);

            Log.i("String",str);

            if(str.equals("Reject")){

                holder.status.setTextColor(Color.RED);
                holder.subject.setTextColor(Color.RED);
            }
            else{

                holder.status.setTextColor(Color.CYAN);
                holder.subject.setTextColor(Color.CYAN);
            }
            holder.status.setText(status.get(position));
            holder.subject.setText(sub.get(position));




        }

        @Override
        public int getItemCount() {
            return sub.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder{

            TextView subject;
            TextView status;
            View mView;
            CardView card;
            public MyHolder(View itemView) {
                super(itemView);

                subject=(TextView)itemView.findViewById(R.id.yy);
                status=(TextView)itemView.findViewById(R.id.xx);
                card=(CardView) itemView.findViewById(R.id.card);
                mView=itemView;
            }
        }

    }

}
