package com.example.davidarisz.pokemonbuilder.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.davidarisz.pokemonbuilder.Classes.NatureData;

public class NatureDatabase extends SQLiteOpenHelper {

    private static final String SQL_CREATE_NATURES = "CREATE TABLE " + "natures" + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name" + " TEXT NOT NULL, " +
            "increased" + " TEXT NOT NULL, " +
            "decreased" + " TEXT NOT NULL)";

    private static final String SQL_DELETE_NATURES = "DROP TABLE IF EXISTS " + "natures";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NATURES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NATURES);
        onCreate(db);
    }

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

    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("select * from natures",null);
    }

    public Long insert (NatureData natureData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",natureData.getName());
        contentValues.put("increased",natureData.getIncreased());
        contentValues.put("decreased",natureData.getDecreased());

        return getWritableDatabase().insert("natures",null,contentValues);
    }

    public long remove (int id) {
        return getWritableDatabase().delete("natures","_id = ?", new String[] { String.valueOf(id) });
    }
}
