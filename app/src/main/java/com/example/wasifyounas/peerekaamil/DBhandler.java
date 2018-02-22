package com.example.wasifyounas.peerekaamil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Wasif Younas on 2/8/2018.
 */

public class DBhandler extends SQLiteOpenHelper {




    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "NovelsDatabase";
    // Table name by Ali Imaran Bangish
    private static final String Table_Bookmarks = "Bookmarks";
    private static final String BookMark_ID="BookMark_ID";
    private static final String BookMark_PageNo="BookMark_PageNo";



    private DBhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BOOKMARKS_TABLE = "CREATE TABLE " + Table_Bookmarks + "("
                + BookMark_ID  +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+BookMark_PageNo  + " INTEGER"+");";

        db.execSQL(CREATE_BOOKMARKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Table_Bookmarks);
    }

    public static DBhandler getinstance(Context c){

        return  new DBhandler(c);
    }

    protected void deleteBookMark(String bookMark_PageNo) {


        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table_Bookmarks, BookMark_PageNo + " = ?", new String[] { bookMark_PageNo });


        db.close(); // Closing database connection
    }



    protected ArrayList<Bookmarks> getBookmarks(){

        ArrayList<Bookmarks> bookmarksArrayList = new ArrayList<Bookmarks>();
        bookmarksArrayList.clear();
        // Select All Query
        String selectQuery = "SELECT * FROM " + Table_Bookmarks;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                // Adding Imagr Path to list
                bookmarksArrayList.add(new Bookmarks(cursor.getInt(cursor.getColumnIndex(BookMark_ID)),cursor.getInt(cursor.getColumnIndex(BookMark_PageNo))));

            } while (cursor.moveToNext());
        }

        db.close();
        // return Image list
        return bookmarksArrayList;

    }



    protected void AddBookmarks(int pageNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i=0;
        Cursor c = null;
        int count = -1;


            String query = "SELECT COUNT(*) FROM "
                    + Table_Bookmarks + " WHERE " + BookMark_PageNo + " = ?";
            c = db.rawQuery(query, new String[] {String.valueOf(pageNo)});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            if(count==0){

                ContentValues values = new ContentValues();
                values.put(BookMark_PageNo, pageNo);
                db.insert(Table_Bookmarks, null, values);}



        i=0;
        // Inserting Row

        db.close(); // Closing database connection
    }



}
