package com.example.davidarisz.pokemonbuilder.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.davidarisz.pokemonbuilder.Classes.ItemData;

public class ItemDatabase extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ITEMS = "CREATE TABLE " + "items" + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name" + " TEXT NOT NULL, " +
            "effect" + " TEXT NOT NULL, " +
            "sprite" + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ITEMS = "DROP TABLE IF EXISTS " + "items";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ITEMS);
        onCreate(db);
    }

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

    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("select * from items",null);
    }

    public Long insert (ItemData itemData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",itemData.getName());
        contentValues.put("effect",itemData.getEffect());
        contentValues.put("sprite",itemData.getSprite());

        return getWritableDatabase().insert("items",null,contentValues);
    }

    public long remove (int id) {
        return getWritableDatabase().delete("items","_id = ?", new String[] { String.valueOf(id) });
    }
}
