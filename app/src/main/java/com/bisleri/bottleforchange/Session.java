package com.bisleri.bottleforchange;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

public class Session {

    private final SharedPreferences prefs;
    private static Session instance;
    ArrayList<String> listEndUsersId = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));

    private Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public static Session getInstance(Context context){
        if(instance == null){
            instance = new Session(context);
        }
        return instance;
    }

    public void setUserId(String _id) {
        prefs.edit().putString("user_id", _id).apply();
    }

    public String getUserId() {
        return prefs.getString("user_id","");
    }

    public void setCategoryId(String _id) {
        prefs.edit().putString("category_id", _id).apply();
    }

    public String getCategoryId() {
        return prefs.getString("category_id","");
    }

    public void setPin(String pin) {
        prefs.edit().putString("is_pin_set", pin).apply();
    }

    public String isPinSet() {
        return prefs.getString("is_pin_set","");
    }

    public void setInvalidPin(String pin) {
        prefs.edit().putString("is_valid_pin", pin).apply();
    }

    public String isPinInvalid() {
        return prefs.getString("is_valid_pin","");
    }

    public boolean isCategoryDashboard() {
        return listEndUsersId.contains(getCategoryId());
    }
}
