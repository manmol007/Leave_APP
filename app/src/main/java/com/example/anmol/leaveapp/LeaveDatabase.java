package com.example.anmol.leaveapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class LeaveDatabase extends SQLiteOpenHelper {

    public static final String DB_Name="LeaveDatabase";
    public static final String DB_Table="Register";
    public static final int DB_Version=1;

    Context ctx;
    SQLiteDatabase db;

    public LeaveDatabase(Context context) {
        super(context,DB_Name,null,DB_Version );

            ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query="CREATE TABLE " + DB_Table+  "(Name TEXT NOT NULL,Email TEXT PRIMARY KEY NOT NULL,Password TEXT NOT NULL,Designation TEXT NOT NULL);";

        sqLiteDatabase.execSQL(query);

        Log.i("Table ","created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("Drop TABLE if EXISTS "+DB_Table);
            onCreate(sqLiteDatabase);

    }

    public void WriteData(String name,String email,String password,String designation){

        db=getWritableDatabase();
        String query="INSERT INTO " + DB_Table +"(Name,Email,Password,Designation)values ('"+name+"','"+email+"','"+password+"','"+designation+"');";
            db.execSQL(query);
        Toast.makeText(ctx,"Data added successfully",Toast.LENGTH_LONG).show();
    }
    public ArrayList<String> getData(){

        db=getReadableDatabase();
        Cursor cr=db.rawQuery("SELECT * FROM "+ DB_Table,null);
       ArrayList<String> data=new ArrayList<>();
        while (cr.moveToNext()){
            String name=cr.getString(0);
            String email=cr.getString(1);
            String password=cr.getString(2);
            String designation=cr.getString(3);
            data.add(name);
            data.add(email);
            data.add(password);
            data.add(designation);
        }
        return data;
    }


}

