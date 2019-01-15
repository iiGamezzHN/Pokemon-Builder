package com.example.davidarisz.pokemonbuilder.models;

public class Move {
    private int accuracy;
    private DamageClass damage_class;
    private EffectEntries effect_entries;
    private int power;
    private String name;
    private int pp;
    private int priority;
    private Target target;
    private Type type;

    public class DamageClass {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class EffectEntries {
        private String short_effect;

        public String getShort_effect() {
            return short_effect;
        }

        public void setShort_effect(String short_effect) {
            this.short_effect = short_effect;
        }
    }

    public class Target {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Type {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public DamageClass getDamage_class() {
        return damage_class;
    }

    public void setDamage_class(DamageClass damage_class) {
        this.damage_class = damage_class;
    }

    public EffectEntries getEffect_entries() {
        return effect_entries;
    }

    public void setEffect_entries(EffectEntries effect_entries) {
        this.effect_entries = effect_entries;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}