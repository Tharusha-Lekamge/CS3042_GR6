package com.example.microbank.data.Implementation;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
        ContentValues cv = new ContentValues();
        cv.put("CUSTOMER_ID",customerID);
        cv.put("ACCOUNT_NO",accNo);
        cv.put("TIMESTAMP",currentDateandTime);
        cv.put("TRANSACTION_TYPE",type);
        cv.put("TRANSACTION_CHARGE",trCharge);
        cv.put("AMOUNT",amount);
        cv.put("REFERENCE",reference);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("TRANSACTIONS",null,cv);
    }


}
