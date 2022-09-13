package com.yorvoration.workmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlData extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "onewin.db";
    private static final String  TABLE_NAME = "malumotlar";
    public static final String Col_0 = "ID";
    public static final String Col_1 = "UID";
    public static final String Col_2 = "TIL";
    public static final String Col_3 = "REJIM";
    public static final String Col_4 = "KALIT";
    public static final String Col_5 = "PAROL";
    public SqlData(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, UID TEXT,TIL TEXT,REJIM TEXT,KALIT TEXT,PAROL TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }
    public Cursor oqish(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("Select * from " + TABLE_NAME + " ORDER BY ID DESC LIMIT 1",null);
        return cursor;
    }
    public Boolean ozgartir(String id,String uid,String til,String rejim,String kalit,String parol){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1,uid);
        contentValues.put(Col_2,til);
        contentValues.put(Col_3,rejim);
        contentValues.put(Col_4,kalit);
        contentValues.put(Col_5,parol);
        int result = sqLiteDatabase.update(TABLE_NAME,contentValues,"ID =?",new String[]{id});
        if (result>0){
            return true;
        }
        else {
            return false;
        }
    }
    /*public Integer ochir(String id){
        SQLiteDatabase ddb = this.getWritableDatabase();
        int i = ddb.delete(TABLE_NAME,"ID =?",new String[]{id});
        return i;
    }*/
    public Boolean kiritish(String uid, String til,String rejim,String kalit,String parol) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_1,uid);
            contentValues.put(Col_2,til);
            contentValues.put(Col_3,rejim);
            contentValues.put(Col_4,kalit);
            contentValues.put(Col_5,parol);
            long result = db.insert(TABLE_NAME,null,contentValues);
            db.close();

            if (result==-1){
                return false;
            }
            else {
                return true;
            }
    }
}
