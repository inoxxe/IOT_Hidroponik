package com.example.ino.iot_hidroponik;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ino.iot_hidroponik.Model.User;


public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "iot_hidroponik";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "keypass";
    private static final String KEY_ID = "keyid";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_NAME,user.getName());
        editor.putString(KEY_PASS,user.getPassword());
        editor.putString(KEY_ID,user.getId());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_NAME,null),
                sharedPreferences.getString(KEY_PASS,null),
                sharedPreferences.getString(KEY_ID,null)
                );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}