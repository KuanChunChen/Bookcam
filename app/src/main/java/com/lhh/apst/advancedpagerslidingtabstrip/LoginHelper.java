package com.lhh.apst.advancedpagerslidingtabstrip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "LoginMember";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Logins";
    private static final String COL_id = "id";
    //private static final String COL_nick = "nick";
    private static final String COL_name = "name";
    //private static final String COL_password = "password";
    private Cursor cursor;

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COL_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_name + " TEXT NOT NULL)" ;
    // COL_password + " TEXT NOT NULL, " +
    // COL_nick + " TEXT NOT NULL)" ;

    public LoginHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

        /*Spot spot1= new Spot("Pony", "TW", "123", "NCU 300",null);
        Spot spot2 = new Spot("Kevin", "HK", "456", "NCU 310",null);
        Spot spot3 = new Spot("Andy", "HK", "789", "NCU 320",null);
        Spot spot4 = new Spot("Abbas", "IRAN", "012", "NCU 330",null);

        insert(spot1);
        insert(spot2);
        insert(spot3);
        insert(spot4);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /*public void insertIfEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                COL_id, COL_name, COL_web, COL_phone, COL_address, COL_image
        };
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null,
                null);
        if (cursor.getCount()<5) {
            Spot spot1= new Spot("Pony", "TW", "123", "NCU 300",null);
            Spot spot2 = new Spot("Kevin", "HK", "456", "NCU 310",null);
            Spot spot3 = new Spot("Andy", "HK", "789", "NCU 320",null);
            Spot spot4 = new Spot("Abbas", "IRAN", "012", "NCU 330",null);

            cursor.close();
            insert(spot1);
            insert(spot2);
            insert(spot3);
            insert(spot4);

        }
        cursor.close();
    }*/

    public List<Logins> getAllLogins() {
        //insertIfEmpty();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                COL_id, COL_name
        };
        Cursor cursor = db.query(TABLE_NAME, columns, null,null,null,null,null);
        List<Logins> loginList = new ArrayList<>();
        System.out.println("GetAllSpots" + cursor.getCount());
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            //String password = cursor.getString(2);
            // String nick = cursor.getString(3);
            Logins login = new Logins(id,name);
            loginList.add(login);
        }
        cursor.close();
        return loginList;
    }

    public Logins findById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {
                COL_id,COL_name
        };
        String selection = COL_id + " = ?;";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs,
                null, null,null);
        Logins login = null;
        if (cursor.moveToNext()) {
            String name = cursor.getString(0);
            //String password = cursor.getString(1);
            // String nick = cursor.getString(2);

            login = new Logins(id,name);
        }
        cursor.close();
        return login;
    }

    public long insert(Logins login) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_name, login.getName());
        return db.insert(TABLE_NAME, null, values);
    }

    public int update(Logins login) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_name, login.getName());
        String whereClause = COL_id + " ";
        String[] whereArgs = {Integer.toString(login.getId())};
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }


    public Cursor getAll() { // 查詢所有資料，取出所有的欄位
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public int deleteById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COL_id + " = ?;";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public Cursor newMember() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +  TABLE_NAME  ;
        System.out.println(query);
        Cursor cur = db.rawQuery(query, null);
        cur.moveToLast();
        return cur;

    }

    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(LoginHelper.TABLE_NAME, null, null);
        db.close();
    }

}