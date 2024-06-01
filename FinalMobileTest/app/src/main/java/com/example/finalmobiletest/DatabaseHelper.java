package com.example.finalmobiletest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_COLUMN_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_DESC = "user_desc";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_USERNAME = "user_username";

    // Table Bookmark
    private static final String BOOKMARK_TABLE_NAME = "bookmark";
    private static final String BOOKMARK_COLUMN_ID = "bookmark_id";
    private static final String BOOKMARK_COLUMN_USER_ID = "bookmark_user_id";
    private static final String BOOKMARK_COLUMN_CATEGORY = "bookmark_kategori";
    private static final String BOOKMARK_COLUMN_TITLE = "bookmark_title";
    private static final String BOOKMARK_COLUMN_AUTHOR = "bookmark_author";
    private static final String BOOKMARK_COLUMN_QUOTE_ID = "bookmark_quote_id";
    private static final String BOOKMARK_COLUMN_TIMESTAMP = "bookmark_timestamp";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT, "
                + USER_DESC + " TEXT, "
                + USER_PASSWORD + " TEXT, "
                + USER_USERNAME + " TEXT)";
        db.execSQL(createUserTable);

        // Create bookmark table
        String createBookmarkTable = "CREATE TABLE " + BOOKMARK_TABLE_NAME + " ("
                + BOOKMARK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOKMARK_COLUMN_USER_ID + " INTEGER, "
                + BOOKMARK_COLUMN_TITLE + " TEXT, "
                + BOOKMARK_COLUMN_AUTHOR + " TEXT, "
                + BOOKMARK_COLUMN_CATEGORY + " TEXT, "
                + BOOKMARK_COLUMN_QUOTE_ID + " INTEGER, "
                + "FOREIGN KEY (" + BOOKMARK_COLUMN_USER_ID + ") REFERENCES " + USER_TABLE_NAME + "(" + USER_COLUMN_ID + "))";
        db.execSQL(createBookmarkTable);
    }

    public void insertUser(String name, String username, String password, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        values.put(USER_DESC, description);
        db.insert(USER_TABLE_NAME, null, values);
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_USERNAME + " = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_USERNAME + " = ? AND " + USER_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void insertBookmarkQuote(int quoteId, int userId, String title, String author, String category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOKMARK_COLUMN_QUOTE_ID, quoteId);
        values.put(BOOKMARK_COLUMN_USER_ID, userId);
        values.put(BOOKMARK_COLUMN_TITLE, title);
        values.put(BOOKMARK_COLUMN_AUTHOR, author);
        values.put(BOOKMARK_COLUMN_CATEGORY, category);
        db.insert(BOOKMARK_TABLE_NAME, null, values);
    }

    public Cursor getAllBookmark(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + BOOKMARK_TABLE_NAME, null);
    }


    public Set<Integer> getDestinationByUserIdBookmark(int userId) {
        Set<Integer> destinationSet = new HashSet<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = getReadableDatabase();
            if (db == null) {
                Log.e("Database", "getReadableDatabase() returned null");
                return destinationSet;
            }

            String[] columns = {BOOKMARK_COLUMN_QUOTE_ID};
            String selection = BOOKMARK_COLUMN_USER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(userId)};

            cursor = db.query(BOOKMARK_TABLE_NAME, columns, selection, selectionArgs, null, null, BOOKMARK_COLUMN_TIMESTAMP + " DESC");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int destinationId = cursor.getInt(cursor.getColumnIndexOrThrow(BOOKMARK_COLUMN_QUOTE_ID));
                    destinationSet.add(destinationId);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error while getting destination by user ID bookmark", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return destinationSet;
    }


    public boolean deletebookmark(int idQuotes, int userId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(BOOKMARK_TABLE_NAME, BOOKMARK_COLUMN_ID + "=? AND " + BOOKMARK_COLUMN_USER_ID + "=?", new String[]{String.valueOf(idQuotes), String.valueOf(userId)}) > 0;
    }
    public int getUserId(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {USER_COLUMN_ID};
        String selection = USER_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(USER_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_COLUMN_ID));
        }
        cursor.close();
        db.close();
        return userId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement database upgrade logic if needed
    }
}
