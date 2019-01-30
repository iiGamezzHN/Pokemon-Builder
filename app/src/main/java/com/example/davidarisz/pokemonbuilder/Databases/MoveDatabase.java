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

import com.example.davidarisz.pokemonbuilder.Classes.MoveData;

public class MoveDatabase extends SQLiteOpenHelper {

    // String for creating the moves table inside the database with all variables
    private static final String SQL_CREATE_MOVES = "CREATE TABLE " + "moves" + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name" + " TEXT NOT NULL, " +
            "power" + " INTEGER NOT NULL, " +
            "accuracy" + " INTEGER NOT NULL, " +
            "pp" + " INTEGER NOT NULL, " +
            "category" + " STRING NOT NULL, " +
            "effect" + " STRING NOT NULL, " +
            "type" + " STRING NOT NULL)";


    // Deletes the moves table from the database
    private static final String SQL_DELETE_MOVES = "DROP TABLE IF EXISTS " + "moves";


    // Excecuting the create table string
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVES);
    }


    // Updates the database with new entries
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MOVES);
        onCreate(db);
    }


    // Constructor for the database
    public MoveDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    private static MoveDatabase instance;


    public static MoveDatabase getInstance (Context c) {

        if (instance != null) {
            return instance;
        }
        else {
            instance = new MoveDatabase(c, "moves", null, 1);
            return instance;
        }
    }


    // Query to return all entries in the database
    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("select * from moves",null);
    }


    // Query to return only the row in the database that matches the given name
    public Cursor selectMove(String move) {
        return getWritableDatabase().rawQuery("select * from moves WHERE name = ?", new String[] { move });
    }


    // Insert a new entry into the database
    public Long insert (MoveData moveData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",moveData.getName());
        contentValues.put("power",moveData.getPower());
        contentValues.put("accuracy",moveData.getAccuracy());
        contentValues.put("pp",moveData.getPp());
        contentValues.put("category",moveData.getCategory());
        contentValues.put("effect",moveData.getEffect());
        contentValues.put("type",moveData.getType());

        return getWritableDatabase().insert("moves",null,contentValues);
    }


    // Excecuting code that deletes the move table
    public void remove () {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(SQL_DELETE_MOVES);
        onCreate(db);
    }
}
