package com.example.microbank.Control;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.microbank.data.Model.Customer;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveSession(Customer customer){
        String id = customer.getCustomer_id();
        editor.putString(SESSION_KEY,id).commit();
    }
    public String  getSession(){
        return sharedPreferences.getString(SESSION_KEY,"Over");
    }

    public void removeSession(){
        editor.putString(SESSION_KEY,"Over").commit();
    }
}
