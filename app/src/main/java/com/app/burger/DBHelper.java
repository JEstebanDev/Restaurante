package com.app.burger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "burger.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS order_data" +
                "(id_order_data TEXT PRIMARY KEY,id_user TEXT,state TEXT,date TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS user_order" +
                "(id_order_data TEXT PRIMARY KEY,id_plate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS order_data");
        db.execSQL("DROP TABLE IF EXISTS user_order");
    }
    public Boolean INSERT_ORDER_DATA(String id_order_data,String id_user,String state,String date){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id_order_data",id_order_data);
        contentValues.put("id_user",id_user);
        contentValues.put("state",state);
        contentValues.put("date",date);
        long result=db.insert("order_data",null,contentValues);
        return result != -1;
    }

    public Boolean UPDATE_ORDER_DATA(String id_order_data,String id_user,String state,String date){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id_user",id_user);
        contentValues.put("state",state);
        contentValues.put("date",date);

        Cursor cursor=db.rawQuery("SELECT * FROM order_data WHERE id_order_data= ?",new String[]{id_order_data});

        if(cursor.getCount()>0){
            long result=db.update("order_data",contentValues,"id_order_data",new String[]{id_order_data});
            return result != -1;
        }else{
            return false;
        }
    }

    public Boolean DELETE_ORDER_DATA(String id_order_data){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM order_data WHERE id_order_data= ?",new String[]{id_order_data});

        if(cursor.getCount()>0){
            long result=db.delete("order_data","id_order_data=?",new String[]{id_order_data});
            return result != -1;
        }else{
            return false;
        }
    }

    public Cursor GET_ORDER_DATA(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM order_data WHERE id_order_data", null);
    }


}
