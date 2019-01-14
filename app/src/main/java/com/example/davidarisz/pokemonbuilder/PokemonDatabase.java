package com.example.davidarisz.pokemonbuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class PokemonDatabase extends SQLiteOpenHelper {

    private static final String SQL_CREATE_POKEMON = "CREATE TABLE " + "pokemon" + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name" + " TEXT NOT NULL, " +
            "item" + " TEXT NOT NULL, " +
            "ability" + " TEXT NOT NULL, " +
            "move1" + " TEXT NOT NULL, " +
            "move2" + " TEXT NOT NULL, " +
            "move3" + " TEXT NOT NULL, " +
            "move4" + " TEXT NOT NULL, " +
            "nature" + " TEXT NOT NULL, " +
            "hp_iv" + " INTEGER NOT NULL, " +
            "att_iv" + " INTEGER NOT NULL, " +
            "def_iv" + " INTEGER NOT NULL, " +
            "spa_iv" + " INTEGER NOT NULL, " +
            "spd_iv" + " INTEGER NOT NULL, " +
            "sp_iv" + " INTEGER NOT NULL, " +
            "hp_ev" + " INTEGER NOT NULL, " +
            "att_ev" + " INTEGER NOT NULL, " +
            "def_ev" + " INTEGER NOT NULL, " +
            "spa_ev" + " INTEGER NOT NULL, " +
            "spd_ev" + " INTEGER NOT NULL, " +
            "sp_ev" + " INTEGER NOT NULL, " +
            "url" + " TEXT NOT NULL)";

    private static final String SQL_DELETE_POKEMON = "DROP TABLE IF EXISTS " + "pokemon";

    private static final String sql = "INSERT INTO pokemon (name, item, ability, move1, move2, move3, move4, nature, " +
            "hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev, att_ev, def_ev, spa_ev, spd_ev, sp_ev, url) " +
            "VALUES('Charizard','Fire Orb','blaze','mega-punch','fire-punch','thunder punch','swords dance','adamant'" +
            ",'1','2','3','4','5','6','7','8','9','10','11','12','https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png')" ;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_POKEMON);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_POKEMON);
        onCreate(db);
    }

    public PokemonDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static PokemonDatabase instance;

    public static PokemonDatabase getInstance (Context c) {
        if (instance != null) {
            return instance;
        }
        else {
            instance = new PokemonDatabase(c, "pokemon", null, 1);
            return instance;
        }
    }

    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("select * from pokemon",null);
    }

    public Long insert (SavedPokemon savedPokemon) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",savedPokemon.getName());
        contentValues.put("item",savedPokemon.getItem());
        contentValues.put("ability",savedPokemon.getAbility());
        contentValues.put("move1",savedPokemon.getMove1());
        contentValues.put("move2",savedPokemon.getMove2());
        contentValues.put("move3",savedPokemon.getMove3());
        contentValues.put("move4",savedPokemon.getMove4());
        contentValues.put("nature",savedPokemon.getNature());
        contentValues.put("hp_iv",savedPokemon.getHp_iv());
        contentValues.put("att_iv",savedPokemon.getAtt_iv());
        contentValues.put("def_iv",savedPokemon.getDef_iv());
        contentValues.put("spa_iv",savedPokemon.getSpa_iv());
        contentValues.put("spd_iv",savedPokemon.getSpd_iv());
        contentValues.put("sp_iv",savedPokemon.getSp_iv());
        contentValues.put("hp_ev",savedPokemon.getHp_ev());
        contentValues.put("att_ev",savedPokemon.getAtt_ev());
        contentValues.put("def_ev",savedPokemon.getDef_ev());
        contentValues.put("spa_ev",savedPokemon.getSpa_ev());
        contentValues.put("spd_ev",savedPokemon.getSpd_ev());
        contentValues.put("sp_ev",savedPokemon.getSp_ev());
        contentValues.put("url",savedPokemon.getUrl());

        Log.d("dbTag", savedPokemon.getName());
        Log.d("dbTag", savedPokemon.getNature());
        Log.d("dbTag", savedPokemon.getItem());
        Log.d("dbTag", savedPokemon.getMove1());
        Log.d("dbTag", savedPokemon.getUrl());

        return getWritableDatabase().insertOrThrow("pokemon",null,contentValues);
    }

    public long remove (int id) {
        return getWritableDatabase().delete("pokemon","_id = ?", new String[] { String.valueOf(id) });
    }
}
