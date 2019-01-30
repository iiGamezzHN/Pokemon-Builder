/*
Author: David Arisz

This file contains the moves database. It can create and delete the move table.
It can also return and add move entries to/from the database.
 */

package com.example.davidarisz.pokemonbuilder.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.davidarisz.pokemonbuilder.Classes.ItemData;

public class ItemDatabase extends SQLiteOpenHelper {

    // String for creating the items table inside the database with all variables
    private static final String SQL_CREATE_ITEMS = "CREATE TABLE " + "items" + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name" + " TEXT NOT NULL, " +
            "effect" + " TEXT NOT NULL, " +
            "sprite" + " TEXT NOT NULL)";


    // Deletes the items table from the database
    private static final String SQL_DELETE_ITEMS = "DROP TABLE IF EXISTS " + "items";


    // Excecuting the create table string
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ITEMS);
    }


    // Updates the database with new entries
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ITEMS);
        onCreate(db);
    }


    // Constructor for the database
    public ItemDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    private static ItemDatabase instance;


    public static ItemDatabase getInstance (Context c) {

        if (instance != null) {
            return instance;
        }
        else {
            instance = new ItemDatabase(c, "items", null, 1);
            return instance;
        }
    }


    // Query to return all entries in the database
    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("select * from items",null);
    }


    // Insert a new entry into the database
    public Long insert (ItemData itemData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",itemData.getName());
        contentValues.put("effect",itemData.getEffect());
        contentValues.put("sprite",itemData.getSprite());

        return getWritableDatabase().insert("items",null,contentValues);
    }


    // Excecuting code that deletes the move table
    public void remove () {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(SQL_DELETE_ITEMS);
        onCreate(db);
    }
}
