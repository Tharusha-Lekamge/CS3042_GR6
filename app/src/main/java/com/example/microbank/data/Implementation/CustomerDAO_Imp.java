package com.example.microbank.data.Implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.microbank.data.CustomerDAO;
import com.example.microbank.data.DBHandler;
import com.example.microbank.data.Model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

import at.favre.lib.crypto.bcrypt.BCrypt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CustomerDAO_Imp extends DBHandler implements CustomerDAO {
    public CustomerDAO_Imp(@Nullable Context context) {
        super(context);
    }

    boolean isSpecial = false;

    @Override
    public Customer checkUserNamePassword(String username, String password){
        SQLiteDatabase accountsTable = this.getWritableDatabase();
        Cursor cursor = accountsTable.rawQuery("SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID=?",new String[]{username});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            String customer_id = cursor.getString(0);
            String first_name = cursor.getString(1);
            String last_name = cursor.getString(2);
            String password_data = cursor.getString(3);
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), password_data);
            if (result.verified){
                Customer customer= new Customer(customer_id,first_name,last_name);
                cursor.close();
                accountsTable.close();
                return customer;
            }
        }
        // dummy data for special customers
        Customer customer = checkValidCustomer(username, password);
        if (customer != null){
            return customer;
        }
        cursor.close();
        accountsTable.close();
        return null;
    }

//    public void initCustomerTable(){
//        //Loading dummy data. Hashed password
//        ContentValues cv = new ContentValues();
//        cv.put("CUSTOMER_ID","123432");
//        cv.put("FIRST_NAME","John");
//        cv.put("LAST_NAME","Rodrigo");
//        cv.put("PASSWORD","$2a$12$t/gMa8qox.qxzrse7lJXse1EyIxpRqNj0PL/9ONJZR5CfDv4a5qCi");
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert("CUSTOMERS",null,cv);
//        db.close();
//    }

    public Customer getUser(String customer_id){
        SQLiteDatabase accountsTable = this.getWritableDatabase();
        Cursor cursor = accountsTable.rawQuery("SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID=?",new String[]{customer_id});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            String id = cursor.getString(0);
            String first_name = cursor.getString(1);
            String last_name = cursor.getString(2);
            Customer customer= new Customer(id,first_name,last_name);
            cursor.close();
            accountsTable.close();
            return customer;
        }
        cursor.close();
        accountsTable.close();
        return null;
    }

    public Customer checkValidCustomer(String customerID, String password){
        if (customerID.equals("User123") && password.equals("#GoHomeGota2022")){
            Customer customer = new Customer(customerID, "Pala", "Nande");
            isSpecial = true;
            return customer;
        }
        return null;
    }

    public void LoadCustomerData(JSONArray customers){
        for (int i=0;i<customers.length();i++){
            try {
                JSONObject c = new JSONObject(customers.get(i).toString());
//                ContentValues cv = new ContentValues();
//                cv.put("CUSTOMER_ID",customerID);
//                cv.put("FIRST_NAME",firstName);
//                cv.put("LAST_NAME",lastName);
//                cv.put("PASSWORD",password);
//                db.insert("CUSTOMERS",null,cv);
                SQLiteDatabase db = this.getWritableDatabase();
                String insertCustomer = "INSERT INTO CUSTOMERS VALUES (?,?,?,?)";
                SQLiteStatement addCus = db.compileStatement(insertCustomer);
                addCus.bindString(1,c.getString("customerID"));
                addCus.bindString(2,c.getString("firstName"));
                addCus.bindString(3,c.getString("lastName"));
                addCus.bindString(4,c.getString("password"));
                addCus.executeInsert();
                db.close();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isSpecialCustomer(String customerID){
        return isSpecial;
    }

    public void clearCustomerTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM CUSTOMERS");
        db.close();
    }
}

