package com.example.davidarisz.pokemonbuilder.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.davidarisz.pokemonbuilder.Classes.MoveData;
import com.example.davidarisz.pokemonbuilder.Classes.NatureData;

public class MoveDatabase extends SQLiteOpenHelper {

    private static final String SQL_CREATE_MOVES = "CREATE TABLE " + "moves" + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name" + " TEXT NOT NULL, " +
            "power" + " INTEGER NOT NULL, " +
            "accuracy" + " INTEGER NOT NULL, " +
            "pp" + " INTEGER NOT NULL)";

    private static final String SQL_DELETE_MOVES = "DROP TABLE IF EXISTS " + "moves";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MOVES);
        onCreate(db);
    }

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

    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("select * from moves",null);
    }

    public Cursor selectMove(String move) {
        return getWritableDatabase().rawQuery("select * from moves WHERE name = ?", new String[] { move });
    }

    public Long insert (MoveData moveData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",moveData.getName());
        contentValues.put("power",moveData.getPower());
        contentValues.put("accuracy",moveData.getAccuracy());
        contentValues.put("pp",moveData.getPp());

        return getWritableDatabase().insert("moves",null,contentValues);
    }

    public void remove () {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(SQL_DELETE_MOVES);
        onCreate(db);
    }
}
