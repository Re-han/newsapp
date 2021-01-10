package com.r.newsapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "saved_articles";
    public static final int VERSION = 6;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "IMAGE";
    public static final String COL_4 = "DESCRIPTION";
    public static final String COL_5 = "URL";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + DATABASE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT NOT NULL UNIQUE,"
                + COL_3 + " TEXT," + COL_4 + " TEXT," + COL_5 + " TEXT )";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public boolean addItem(String title, String image, String desc, String url) {
        SQLiteDatabase sqLiteOpenHelper = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, image);
        contentValues.put(COL_4, desc);
        contentValues.put(COL_5, url);
        long output = sqLiteOpenHelper.insert(DATABASE_NAME, null, contentValues);
        return output != -1;
    }

    @SuppressLint("Recycle")
    public Cursor getAllArticles() {
        String query = "SELECT * FROM " + DATABASE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean deleteItem(String title) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long res = sqLiteDatabase.delete(DATABASE_NAME, "TITLE=?", new String[]{title});
        return res != -1;

    }

    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + DATABASE_NAME);
    }
}
