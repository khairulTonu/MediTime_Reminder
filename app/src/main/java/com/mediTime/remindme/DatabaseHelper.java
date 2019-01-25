package com.blanyal.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TS on 10/21/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Medicine.db";
    public static final String TABLE_NAME = "medicine_alarm_table";
    public static final String col_1 = "medicine_Name";
    public static final String col_2 = "morning";
    public static final String col_3 = "noon";
    public static final String col_4 = "afternoon";
    public static final String col_5 = "night";
    //public static final String col_6 = "before_meal";
    //public static final String col_7 = "after_meal";
    public static final String col_8 = "hourly";
    public static final String col_9 = "days";



    public DatabaseHelper(Context context){

        super(context,DATABASE_NAME,null,1);

        //SQLiteDatabase db= this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (medicine_name TEXT,morning TEXT,noon TEXT,afternoon TEXT,night TEXT,hourly TEXT,days TEXT)");

    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        onCreate(db);

    }



    public boolean insertData(String medicine_name, String morning, String noon, String afternoon, String night, String hourly, String days){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1,medicine_name);
        contentValues.put(col_2,morning);
        contentValues.put(col_3,noon);
        contentValues.put(col_4,afternoon);
        contentValues.put(col_5,night);
        //contentValues.put(col_6,before_meal);
        //contentValues.put(col_7,after_meal);
        contentValues.put(col_8,hourly);
        contentValues.put(col_9,days);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }



    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}
