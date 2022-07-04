package com.example.microbank.data.Implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.microbank.data.CustomerDAO;
import com.example.microbank.data.DBHandler;
import com.example.microbank.data.Model.Customer;

import java.sql.PreparedStatement;

import at.favre.lib.crypto.bcrypt.BCrypt;

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

    public void initCustomerTable(){
        //Loading dummy data. Hashed password
        ContentValues cv = new ContentValues();
        cv.put("CUSTOMER_ID","123432");
        cv.put("FIRST_NAME","John");
        cv.put("LAST_NAME","Rodrigo");
        cv.put("PASSWORD","$2a$12$t/gMa8qox.qxzrse7lJXse1EyIxpRqNj0PL/9ONJZR5CfDv4a5qCi");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("CUSTOMERS",null,cv);
        db.close();
    }

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
        // call to main server and check if the given username, password belongs to a valid customer
        if (customerID.equals("User123") && password.equals("#GoHomeGota2022")){
            Customer customer = new Customer(customerID, "Pala", "Nande");
            isSpecial = true;
            return customer;
        }
        return null;
    }

    public boolean isSpecialCustomer(String customerID){
        return isSpecial;
    }
}
