package com.example.anmol.leaveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.graphics.BlurMaskFilter.Blur.INNER;


public class CreateApplicationDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME="CreateDatabase";
    public static final String DB_TABLE="CreateLeave";
    public static final int Version=1;

    Context ctx;
    SQLiteDatabase db;

    public CreateApplicationDatabase(Context context) {
        super(context,DB_NAME, null, Version);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query="CREATE TABLE " + DB_TABLE+  "(fromwhom TEXT NOT NULL,towhom TEXT NOT NULL,subject TEXT NOT NULL,fromdate TEXT NOT NULL,todate TEXT NOT NULL,countdate TEXT NOT NULL,currentdate TEXT NOT NULL,category TEXT NOT NULL);";

        sqLiteDatabase.execSQL(query);

        Log.i("Table ","created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop TABLE if EXISTS "+DB_TABLE);
        onCreate(sqLiteDatabase);
    }


    public void WriteLeave(String fromwhom,String towhom,String subject,String fromdate,String todate,String countdate,String currentdate,String category){

        db=getWritableDatabase();
        String query="INSERT INTO " + DB_TABLE +"(fromwhom,towhom,subject,fromdate,todate,countdate,currentdate,category)values ('"+fromwhom+"','"+towhom+"','"+subject+"','"+fromdate+"','"+todate+"','"+countdate+"','"+currentdate+"','"+category+"');";
        db.execSQL(query);
        Toast.makeText(ctx,"Data added successfully",Toast.LENGTH_LONG).show();
       // Toast.makeText(ctx,"Data added successfully",Toast.LENGTH_LONG).show();
    }
    public ArrayList<String> getData(String email){

        db=getReadableDatabase();
        Cursor cr=db.rawQuery("SELECT * FROM "+ DB_TABLE + " where fromwhom = "+" '" +email+ "';",null);
        ArrayList<String> data=new ArrayList<>();
        while (cr.moveToNext()){
            String from=cr.getString(0);
            String to=cr.getString(1);
            String subject=cr.getString(2);
            String fromdate=cr.getString(3);
            String todate=cr.getString(4);
            String countdate=cr.getString(5);
            String currentdate=cr.getString(6);
            String category=cr.getString(7);
            data.add(from);
            data.add(to);
            data.add(subject);
            data.add(fromdate);
            data.add(todate);
            data.add(countdate);
            data.add(currentdate);
            data.add(category);
        }
        return data;
    }


    public ArrayList<String> getReceive(String email){

        db=getReadableDatabase();
        Cursor cr=db.rawQuery("SELECT * FROM "+ DB_TABLE + " where towhom = "+" '" +email+ "';",null);
        ArrayList<String> data=new ArrayList<>();
        while (cr.moveToNext()){
            String from=cr.getString(0);
            String to=cr.getString(1);
            String subject=cr.getString(2);
            String fromdate=cr.getString(3);
            String todate=cr.getString(4);
            String countdate=cr.getString(5);
            String currentdate=cr.getString(6);
            String category=cr.getString(7);
            data.add(from);
            data.add(to);
            data.add(subject);
            data.add(fromdate);
            data.add(todate);
            data.add(countdate);
            data.add(currentdate);
            data.add(category);
        }
        return data;
    }



    public void DeleteLeave(String email,String subject){

        db=getWritableDatabase();
        String query="Delete from " + DB_TABLE +" where towhom = "+" '"+email+"' and subject ="+"'"+subject+"'; " ;
        db.execSQL(query);
        Toast.makeText(ctx,"Data removed successfully",Toast.LENGTH_LONG).show();
    }

    public void deleteLeave(String email,String subject){

        db=getWritableDatabase();
        String query="Delete from " + DB_TABLE +" where fromwhom = "+" '"+email+"' and subject ="+"'"+subject+"'; " ;
        db.execSQL(query);
        Toast.makeText(ctx,"Data removed successfully",Toast.LENGTH_LONG).show();
    }
}
