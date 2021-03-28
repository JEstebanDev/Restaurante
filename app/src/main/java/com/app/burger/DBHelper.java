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
        db.execSQL("DROP TABLE IF EXISTS order_data");
        db.execSQL("DROP TABLE IF EXISTS user_order");
        db.execSQL("DROP TABLE IF EXISTS plate");

        db.execSQL("CREATE TABLE IF NOT EXISTS order_data" +
                "(id_order_data TEXT PRIMARY KEY,id_user TEXT,state TEXT,date TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS user_order" +
                "(id_order_data TEXT PRIMARY KEY,id_plate TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS plate" +
                "(id_plate TEXT PRIMARY KEY,image TEXT,name TEXT,description TEXT,amount INTEGER,price INTEGER,state TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS order_data");
        db.execSQL("DROP TABLE IF EXISTS user_order");
        db.execSQL("DROP TABLE IF EXISTS plate");
    }
    public Boolean INSERT_PLATE(String id_pate,String image,String name,String description,int amount,int price){

        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM plate WHERE id_plate='"+id_pate+"' and state='activo'",null);

        if (cursor.getCount()>0){
            //Existe ya este plato asi que no haga nada
            return false;
        }else{
            ContentValues contentValues=new ContentValues();
            contentValues.put("id_plate",id_pate);
            contentValues.put("image",image);
            contentValues.put("name",name);
            contentValues.put("description",description);
            contentValues.put("amount",amount);
            contentValues.put("price",price);
            contentValues.put("state","activo");
            long result=db.insert("plate",null,contentValues);
            return result != -1;
        }
    }

    public void UPDATE_PLATE(String id_plate, int amount){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("amount",amount);

        Cursor cursor=db.rawQuery("SELECT * FROM plate WHERE id_plate= ?",new String[]{id_plate});

        if(cursor.getCount()>0){
            long result=db.update("plate",contentValues,"id_plate=?",new String[]{id_plate});
        }

    }

    public Cursor GET_PLATE_DATA(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM plate", null);
    }

    public Boolean DELETE_PLATE(String id_plate){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM plate WHERE id_plate= ?",new String[]{id_plate});

        if(cursor.getCount()>0){
            long result=db.delete("plate","id_plate=?",new String[]{id_plate});
            return result != -1;
        }else{
            return false;
        }
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
