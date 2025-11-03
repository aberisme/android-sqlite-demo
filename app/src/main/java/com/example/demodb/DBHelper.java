package com.example.demodb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "demodb";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER = "number";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_NAME + " TEXT, " +
                COLUMN_NUMBER + " TEXT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Operation with demodb

    // adds new contact
    public boolean addContact(String name, String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        // prepare data
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NUMBER, number);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1;
    }

    // retrieves all contact
    public ArrayList<String> getAllContacts() {
        ArrayList<String> contacts = new ArrayList<>();
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String number = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER));
            contacts.add(name + " - " + number);
        }

        cursor.close();
        db.close();

        return contacts;
    }

}

