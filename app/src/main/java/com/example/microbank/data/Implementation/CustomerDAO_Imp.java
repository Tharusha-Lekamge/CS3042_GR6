package com.example.microbank.data.Implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.microbank.data.CustomerDAO;
import com.example.microbank.data.DBHandler;
import com.example.microbank.data.Model.Customer;

public class CustomerDAO_Imp extends DBHandler implements CustomerDAO {
    public CustomerDAO_Imp(@Nullable Context context) {
        super(context);
    }

    @Override
    public Customer checkUserNamePassword(String username, String password){
        SQLiteDatabase accountsTable = this.getWritableDatabase();
        Cursor cursor = accountsTable.rawQuery("SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID=? AND PASSWORD=?",new String[]{username,password});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            String customer_id = cursor.getString(0);
            String first_name = cursor.getString(1);
            String last_name = cursor.getString(2);
            Customer customer= new Customer(customer_id,first_name,last_name);
            cursor.close();
            accountsTable.close();
            return customer;
        }
        cursor.close();
        accountsTable.close();
        return null;
    }

    public void initCustomerTable(){
        ContentValues cv = new ContentValues();
        cv.put("CUSTOMER_ID","123432");
        cv.put("FIRST_NAME","John");
        cv.put("LAST_NAME","Rodrigo");
        cv.put("PASSWORD","#YoLo123");
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


}
