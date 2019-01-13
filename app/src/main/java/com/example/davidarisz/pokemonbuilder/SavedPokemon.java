package com.example.davidarisz.pokemonbuilder;

import java.io.Serializable;

public class SavedPokemon implements Serializable {
    private int id, hp_iv, att_iv, def_iv, spa_iv, spd_iv, sp_iv, hp_ev, att_ev, def_ev, spa_ev, spd_ev, sp_ev;
    private String name, item, ability, move1, move2, move3, move4, nature;

    public SavedPokemon(int id, int hp_iv, int att_iv, int def_iv, int spa_iv, int spd_iv, int sp_iv, int hp_ev,
                        int att_ev, int def_ev, int spa_ev, int spd_ev, int sp_ev, String name, String item,
                        String ability, String move1, String move2, String move3, String move4, String nature) {
        this.id = id;
        this.hp_iv = hp_iv;
        this.att_iv = att_iv;
        this.def_iv = def_iv;
        this.spa_iv = spa_iv;
        this.spd_iv = spd_iv;
        this.sp_iv = sp_iv;
        this.hp_ev = hp_ev;
        this.att_ev = att_ev;
        this.def_ev = def_ev;
        this.spa_ev = spa_ev;
        this.spd_ev = spd_ev;
        this.sp_ev = sp_ev;
        this.name = name;
        this.item = item;
        this.ability = ability;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
        this.nature = nature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHp_iv() {
        return hp_iv;
    }

    public void setHp_iv(int hp_iv) {
        this.hp_iv = hp_iv;
    }

    public int getAtt_iv() {
        return att_iv;
    }

    public void setAtt_iv(int att_iv) {
        this.att_iv = att_iv;
    }

    public int getDef_iv() {
        return def_iv;
    }

    public void setDef_iv(int def_iv) {
        this.def_iv = def_iv;
    }

    public int getSpa_iv() {
        return spa_iv;
    }

    public void setSpa_iv(int spa_iv) {
        this.spa_iv = spa_iv;
    }

    public int getSpd_iv() {
        return spd_iv;
    }

    public void setSpd_iv(int spd_iv) {
        this.spd_iv = spd_iv;
    }

    public int getSp_iv() {
        return sp_iv;
    }

    public void setSp_iv(int sp_iv) {
        this.sp_iv = sp_iv;
    }

    public int getHp_ev() {
        return hp_ev;
    }

    public void setHp_ev(int hp_ev) {
        this.hp_ev = hp_ev;
    }

    public int getAtt_ev() {
        return att_ev;
    }

    public void setAtt_ev(int att_ev) {
        this.att_ev = att_ev;
    }

    public int getDef_ev() {
        return def_ev;
    }

    public void setDef_ev(int def_ev) {
        this.def_ev = def_ev;
    }

    public int getSpa_ev() {
        return spa_ev;
    }

    public void setSpa_ev(int spa_ev) {
        this.spa_ev = spa_ev;
    }

    public int getSpd_ev() {
        return spd_ev;
    }

    public void setSpd_ev(int spd_ev) {
        this.spd_ev = spd_ev;
    }

    public int getSp_ev() {
        return sp_ev;
    }

    public void setSp_ev(int sp_ev) {
        this.sp_ev = sp_ev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getMove1() {
        return move1;
    }

    public void setMove1(String move1) {
        this.move1 = move1;
    }

    public String getMove2() {
        return move2;
    }

    public void setMove2(String move2) {
        this.move2 = move2;
    }

    public String getMove3() {
        return move3;
    }

    public void setMove3(String move3) {
        this.move3 = move3;
    }

    public String getMove4() {
        return move4;
    }

    public void setMove4(String move4) {
        this.move4 = move4;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}
