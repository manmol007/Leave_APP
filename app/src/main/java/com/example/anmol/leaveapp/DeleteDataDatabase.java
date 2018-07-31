package com.example.anmol.leaveapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteDataDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME="DeleteDataDatabase";
    public static final String DB_TABLE="DeletedData";
    public static final int Version=1;

    Context ctx;
    SQLiteDatabase db;

    public DeleteDataDatabase(Context context) {
        super(context,DB_NAME, null, Version);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query="CREATE TABLE " + DB_TABLE+  "(status TEXT NOT NULL,category TEXT NOT NULL,fromwhom TEXT NOT NULL);";

        sqLiteDatabase.execSQL(query);

        Log.i("Table ","created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop TABLE if EXISTS "+DB_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void WriteDeletedData(String status,String subject,String fromwhom){

        db=getWritableDatabase();
        String query="INSERT INTO " + DB_TABLE +"(status,category,fromwhom)values ('"+status+"','"+subject+"','"+fromwhom+"');";
        db.execSQL(query);
        Toast.makeText(ctx,"Data added successfully",Toast.LENGTH_LONG).show();
    }

    public ArrayList<String> getDeletedData(String email){

        db=getReadableDatabase();
        Cursor cr=db.rawQuery("SELECT * FROM "+ DB_TABLE+" where fromwhom = " + "'"+email+"';",null);
        ArrayList<String> data=new ArrayList<>();
        while (cr.moveToNext()){

            String status=cr.getString(0);
            String subject=cr.getString(1);
            data.add(status);
            data.add(subject);
        }
        return data;
    }
}
