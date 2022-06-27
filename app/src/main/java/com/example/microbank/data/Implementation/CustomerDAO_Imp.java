package com.example.microbank.data.Implementation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.microbank.data.CustomerDAO;
import com.example.microbank.data.DBHandler;

public class CustomerDAO_Imp extends DBHandler implements CustomerDAO {
    public CustomerDAO_Imp(@Nullable Context context) {
        super(context);
    }

    @Override
    public Boolean checkUserNamePassword(String username, String password){
        SQLiteDatabase accountsTable = this.getWritableDatabase();
        Cursor cursor = accountsTable.rawQuery("SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID=? AND PASSWORD=?",new String[]{username,password});
        if (cursor.getCount()>0){
            accountsTable.close();
            return true;
        }
        accountsTable.close();
        return false;
    }
}
