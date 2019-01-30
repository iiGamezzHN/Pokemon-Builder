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

import com.example.davidarisz.pokemonbuilder.Classes.NatureData;

public class NatureDatabase extends SQLiteOpenHelper {

    // String for creating the natures table inside the database with all variables
    private static final String SQL_CREATE_NATURES = "CREATE TABLE " + "natures" + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name" + " TEXT NOT NULL, " +
            "increased" + " TEXT NOT NULL, " +
            "decreased" + " TEXT NOT NULL)";


    // Deletes the nartures table from the database
    private static final String SQL_DELETE_NATURES = "DROP TABLE IF EXISTS " + "natures";


    // Excecuting the create table string
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NATURES);
    }


    // Updates the database with new entries
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NATURES);
        onCreate(db);
    }


    // Constructor for the database
    public NatureDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    private static NatureDatabase instance;


    public static NatureDatabase getInstance (Context c) {

        if (instance != null) {
            return instance;
        }
        else {
            instance = new NatureDatabase(c, "natures", null, 1);
            return instance;
        }
    }


    // Query to return all entries in the database
    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("select * from natures",null);
    }


    // Insert a new entry into the database
    public Long insert (NatureData natureData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",natureData.getName());
        contentValues.put("increased",natureData.getIncreased());
        contentValues.put("decreased",natureData.getDecreased());

        return getWritableDatabase().insert("natures",null,contentValues);
    }


    // Excecuting code that deletes the move table
    public long remove (int id) {
        return getWritableDatabase().delete("natures","_id = ?", new String[] { String.valueOf(id) });
    }
}
