package com.lhh.apst.advancedpagerslidingtabstrip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BookHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Book";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Book";
    private static final String COL_Id = "id";
    private static final String COL_BookName = "BookName";
    private static final String COL_BookCover = "BookCover";
    private static final String COL_Author = "Author";
    private static final String COL_Publisher = "Publisher";
    private static final String COL_Location = "Location";
    private static final String COL_Picture = "Picture";
    private static final String COL_Rating = "Rating";
    private static final String COL_Like = "Like";
    private static final String COL_Sell = "Sell";

    private Cursor cursor;

    /*
     書ID  		Id		        int
    書名		BookName      string
    書封		BookCover     string
    作者		Author	       string
    出版社	Publisher	       string
    所在位置	Location	       string
    實體相片    Picture            string
    打分		Rating	       int
    喜歡與否	Like		       boolean
    賣書與否	Sell		       boolean
    */

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COL_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_BookName + " TEXT NOT NULL, " +
                    COL_BookCover + " TEXT, " +
                    COL_Author + " TEXT,"+
                    COL_Publisher + " TEXT, " +
                    COL_Location + " TEXT, " +
                    COL_Picture + " TEXT," +
                    COL_Rating + " TEXT NOT NULL, " +
                    COL_Like + " TEXT, " +
                    COL_Sell + " TEXT)";

    public BookHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public List<Book> getAllBooks() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                COL_Id, COL_BookName, COL_BookCover, COL_Author,COL_Publisher,COL_Location,COL_Picture,COL_Rating,COL_Like,COL_Sell
        };
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        List<Book> BookList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String cover = cursor.getString(2);
            String author = cursor.getString(3);
            String publisher = cursor.getString(4);
            String location = cursor.getString(5);
            String picture = cursor.getString(6);
            int rate = cursor.getInt(7);
            int like = cursor.getInt(8);
            int sell = cursor.getInt(9);
            Book book = new Book(id,name, cover, author,publisher,location,picture,rate,like,sell);
            BookList.add(book);
        }
        cursor.close();
        return BookList;
    }


    public List<Book> getSellBooks() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                COL_Id, COL_BookName, COL_BookCover, COL_Author,COL_Publisher,COL_Location,COL_Picture,COL_Rating,COL_Like,COL_Sell
        };
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        List<Book> BookList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String cover = cursor.getString(2);
            String author = cursor.getString(3);
            String publisher = cursor.getString(4);
            String location = cursor.getString(5);
            String picture = cursor.getString(6);
            int rate = cursor.getInt(7);
            int like = cursor.getInt(8);
            int sell = cursor.getInt(9);
            if (sell==0){
            Book book = new Book(id,name, cover, author,publisher,location,picture,rate,like,sell);
            BookList.add(book);}
        }
        cursor.close();
        return BookList;
    }

    public List<Book> getLikeBooks() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                COL_Id, COL_BookName, COL_BookCover, COL_Author,COL_Publisher,COL_Location,COL_Picture,COL_Rating,COL_Like,COL_Sell
        };
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        List<Book> BookList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String cover = cursor.getString(2);
            String author = cursor.getString(3);
            String publisher = cursor.getString(4);
            String location = cursor.getString(5);
            String picture = cursor.getString(6);
            int rate = cursor.getInt(7);
            int like = cursor.getInt(8);
            int sell = cursor.getInt(9);
            if (like==0){
                Book book = new Book(id,name, cover, author,publisher,location,picture,rate,like,sell);
                BookList.add(book);}
        }
        cursor.close();
        return BookList;
    }

    public Book findById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {
                COL_Id, COL_BookName, COL_BookCover, COL_Author,COL_Publisher,COL_Location,COL_Picture,COL_Rating,COL_Like,COL_Sell
        };
        String selection = COL_Id + " = ?;";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);
        Book book = null;
        if (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String cover = cursor.getString(1);
            String author = cursor.getString(2);
            String publisher = cursor.getString(3);
            String location = cursor.getString(4);
            String picture = cursor.getString(5);
            int rate = cursor.getInt(6);
            int like = cursor.getInt(7);
            int sell = cursor.getInt(8);

            book = new Book(id,name, cover, author,publisher,location,picture,rate,like,sell);
        }
        cursor.close();
        return book;
    }

    public long insert(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BookName, book.getName());
        values.put(COL_BookCover, book.getCover());
        values.put(COL_Author, book.getAuthor());
        values.put(COL_Publisher, book.getPublisher());
        values.put(COL_Location, book.getLocation());
        values.put(COL_Picture, book.getPicture());
        values.put(COL_Rating, book.getRate());
        values.put(COL_Like, book.getLike());
        values.put(COL_Sell, book.getSell());
        return db.insert(TABLE_NAME, null, values);
    }

    public int update(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_BookName, book.getName());
        values.put(COL_BookCover, book.getCover());
        values.put(COL_Author, book.getAuthor());
        values.put(COL_Publisher, book.getPublisher());
        values.put(COL_Location, book.getLocation());
        values.put(COL_Picture, book.getPicture());
        values.put(COL_Rating, book.getRate());
        values.put(COL_Like, book.getLike());
        values.put(COL_Sell, book.getSell());
        String whereClause = COL_Id + " = ?;";
        String[] whereArgs = {Integer.toString(book.getId())};
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public int sell(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_Sell, book.getSell());
        String whereClause = COL_Id + " = ?;";
        String[] whereArgs = {Integer.toString(book.getId())};
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }


    public Cursor getAll() { // 查詢所有資料，取出所有的欄位
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public int deleteById(int id) {//刪除單一筆資料
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COL_Id + " = ?;";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public Cursor newMember() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +  TABLE_NAME  ;
        System.out.println(query);
        Cursor cur = db.rawQuery(query, null);
        cur.moveToNext();

        return cur;
    }

    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(BookHelper.TABLE_NAME, null, null);
        db.close();
    }

}

