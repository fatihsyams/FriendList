package com.fatihsyams.friendlist.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.fatihsyams.friendlist.model.User;

public class MyPref {
    private static final String PREF_NAME = "userPref";
    private static final int PREF_MODE  = Context.MODE_PRIVATE;

    private Context context;
    private SharedPreferences myPref;
    private SharedPreferences.Editor editor;

    public MyPref(Context context) {
        this.context = context;

        // initial object

        myPref = context.getSharedPreferences(PREF_NAME, PREF_MODE);
    }
    public void saveUser(User user){
        editor = myPref.edit();
        editor.putString("id", user.getId());
        editor.putString("nama", user.getNama());
        editor.putString("email", user.getEmail());
        editor.putString("pass", user.getPass());

        editor.commit();
    }
    public String getUserid() {
        return myPref.getString("id", null);
    }
    public String getUserNama() {
        return myPref.getString("nama", null);
    }
    public String getUserEmail() {
        return myPref.getString("email", null);
    }

    public String getUserPass() {
        return myPref.getString("pass", null);
    }

    public void deleteUser(){
        editor = myPref.edit();
        editor.clear().commit();
    }

}
