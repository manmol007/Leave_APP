package com.example.anmol.leaveapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SentApplication extends android.support.v4.app.Fragment {

    private RecyclerView list;
    private ArrayList<String> subject = new ArrayList<>();
    private ArrayList<String> towhom = new ArrayList<>();
    private ArrayList<String> from = new ArrayList<>();
    private ArrayList<String> fromdate = new ArrayList<>();
    private ArrayList<String> todate = new ArrayList<>();
    private ArrayList<String> countdate = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> currentdate = new ArrayList<>();
    private ArrayList<String> category = new ArrayList<>();
    private CreateApplicationDatabase database;
    private myAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sentapplication, container, false);

        list = (RecyclerView) view.findViewById(R.id.Recyclerview);

        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        database = new CreateApplicationDatabase(getActivity());
        data.clear();
        from.clear();
        towhom.clear();
        subject.clear();
        fromdate.clear();
        todate.clear();
        countdate.clear();
        currentdate.clear();
        category.clear();


        //Toast.makeText(getActivity(),getArguments().getString("from"),Toast.LENGTH_LONG).show();

        if(getArguments().getString("from")!=null) {
            data = database.getData(getArguments().getString("from"));
        }
        else{

            SharedPreferences preferences=getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            String usr=preferences.getString("from",null);
            data=database.getData(usr);
            Log.i("data",data.toString());
        }


        for (int i = 0; i < data.size(); ++i) {

            from.add(data.get(i));
            ++i;
            towhom.add(data.get(i));
            ++i;
            subject.add(data.get(i));
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

        adapter = new myAdapter(towhom, subject, from,fromdate,todate,countdate,currentdate,category);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        return view;

    }




    public class myAdapter extends RecyclerView.Adapter<myAdapter.myHolder> {

        private ArrayList<String> subject = new ArrayList<>();
        private ArrayList<String> towhom = new ArrayList<>();
        private ArrayList<String> from = new ArrayList<>();
        private ArrayList<String> fromdate = new ArrayList<>();
        private ArrayList<String> todate = new ArrayList<>();
        private ArrayList<String> countdate = new ArrayList<>();
        private ArrayList<String> currentdate = new ArrayList<>();
        private ArrayList<String> category = new ArrayList<>();

        public myAdapter(ArrayList<String> towhom, ArrayList<String> subject, ArrayList<String> from, ArrayList<String> fromdate, ArrayList<String> todate, ArrayList<String> countdate, ArrayList<String> currentdate,ArrayList<String> category) {

            this.subject = subject;
            this.towhom = towhom;
            this.from = from;
            this.fromdate=fromdate;
            this.todate=todate;
            this.countdate=countdate;
            this.currentdate=currentdate;
            this.category=category;
            }


        @NonNull
        @Override
        public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sentlayout, parent, false);
            return new myHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull myHolder holder, final int position) {

            holder.to.setText(towhom.get(position));
            holder.subject.setText(category.get(position));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), DetailedActivity.class);
                    intent.putExtra("from", from.get(position));
                    intent.putExtra("to", towhom.get(position));
                    intent.putExtra("subject", subject.get(position));
                    intent.putExtra("fromdate", fromdate.get(position));
                    intent.putExtra("todate", todate.get(position));
                    intent.putExtra("countdate", countdate.get(position));
                    intent.putExtra("currentdate", currentdate.get(position));
                    intent.putExtra("category",category.get(position));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return subject.size();
        }

        public class myHolder extends RecyclerView.ViewHolder {

            TextView to;
            TextView subject;
            View view;

            public myHolder(View itemView) {
                super(itemView);

                view = itemView;
                to = (TextView) itemView.findViewById(R.id.towhom);
                subject = (TextView) itemView.findViewById(R.id.title);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
