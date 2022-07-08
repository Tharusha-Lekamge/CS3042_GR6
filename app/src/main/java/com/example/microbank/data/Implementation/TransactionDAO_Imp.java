package com.example.microbank.data.Implementation;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.microbank.data.DBHandler;
import com.example.microbank.data.TransactionDAO;


public class TransactionDAO_Imp extends DBHandler implements TransactionDAO {
    public TransactionDAO_Imp(@Nullable Context context) {
        super(context);
    }

    @Override
    public void addTransaction(String customerID, String accNo,String type,Double trCharge,Double amount,String reference) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        String addTr = "INSERT INTO TRANSACTIONS VALUES (?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement(addTr);
        statement.bindString(2,customerID);
        statement.bindString(3,accNo);
        statement.bindString(4,currentDateandTime);
        statement.bindString(5,type);
        statement.bindDouble(6,trCharge);
        statement.bindDouble(7,amount);
        statement.bindString(8,reference);
        long rowId = statement.executeInsert();
    }


}
