package com.buddy.bytequote;

import static android.provider.MediaStore.Images.Media.insertImage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="quotes.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_QUOTES="quotes";
    private static final String TABLE_IMAGES="images";

    //Quotes table Columns
    private static final String COLUMN_ID="id";
    private static final String COLUMN_QUOTE="quote";
    private static final String COLUMN_AUTHOR="author";
    private static final String COLUMN_IMAGE_ID="image_id";
    private static final String COLUMN_IS_FAVORITE="is_favorite";
    private static final String COLUMN_DATE_ADDED="date_added";

    //image Table Columns


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
          //create Qoutes table
        String createQuotesTable="CREATE TABLE " + TABLE_QUOTES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_QUOTE + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_IMAGE_ID + " INTEGER, "
                + COLUMN_IS_FAVORITE + " BOOLEAN DEFAULT 0, "
                + COLUMN_DATE_ADDED + " DATE DEFAULT CURRENT_DATE);";

        db.execSQL(createQuotesTable);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_QUOTES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGES);

    }

    //Method to insert a quotes into the database

    //new logic

    public void insertQuote(String quote,String author)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_QUOTE,quote);
        values.put(COLUMN_AUTHOR,author);

        db.insert(TABLE_QUOTES,null,values);
    }


    //fetch a random qoutes
     public Cursor getRandomQuote()
     {
         SQLiteDatabase db=this.getWritableDatabase();

         return db.rawQuery("SELECT * FROM " + TABLE_QUOTES + " ORDER BY RANDOM() LIMIT 1", null);
     
     }
    // Mark quote as favorite

    public void markAsFavorite(int quoteId)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_IS_FAVORITE,1);
        db.update(TABLE_QUOTES,values,COLUMN_ID+"=?" ,new String[]{String.valueOf(quoteId)});

    }

    //Method to get all favorite quotes
    public Cursor getAllFavorites() {
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_QUOTES + " WHERE " + COLUMN_IS_FAVORITE + " = 1";
        return db.rawQuery(query,null);

    }
}
