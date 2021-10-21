package com.example.coffeeandmore;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagment(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(UserData user) {
        int id = user.getUid();
        String name = user.getUNAME();
        String email = user.getEMAIL();
        String password = user.getPWD();

        editor.putInt(SESSION_KEY, id).commit();
        editor.putString("name", name).commit();
        editor.putString("email", email).commit();
        editor.putString("password", password).commit();


    }


    public UserData getSession() {

         int uid =  sharedPreferences.getInt(SESSION_KEY, -1);
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        UserData user = new UserData(uid, name, email, password);

        return user;


    }
}
