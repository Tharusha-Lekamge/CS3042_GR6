package com.example.microbank.data.Implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.example.microbank.data.AccountDAO;
import com.example.microbank.data.Exception.InvalidAccountException;
import com.example.microbank.data.Model.Account;
import com.example.microbank.data.DBHandler;

public class AccountDAO_Imp extends DBHandler implements AccountDAO {
    public AccountDAO_Imp(@Nullable Context context) {
        super(context);
    }

    @Override
    public void addAccount(Account acc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ACCOUNT_NO",acc.getAcc_No());
        cv.put("CUSTOMER_ID",acc.getCustomer_ID());
        cv.put("ACCOUNT_TYPE",acc.getAccount_type());
        cv.put("BALANCE",acc.getBalance());
        db.insert("ACCOUNTS",null,cv);
        db.close();
    }
    public List<Account> getAccountsList(String CustomerID) throws InvalidAccountException{
        List<Account> accountList = new ArrayList<>();
        String queryString = "SELECT * FROM ACCOUNTS WHERE CUSTOMER_ID='"+CustomerID+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            do {
                String accNo = cursor.getString(0);
                String customerID = cursor.getString(1);
                String accountType = cursor.getString(2);
                Double balance = cursor.getDouble(3);
                Account account = new Account(accNo,customerID,accountType,balance);
                accountList.add(account);
            }while (cursor.moveToNext());
        }
        else{
            String msg = "No accounts found for "+CustomerID;
            throw new InvalidAccountException(msg);
        }
        cursor.close();
        db.close();
        return accountList;
    }
    public void updateBalance(String accNo,String type,Double trCharge,Double amount) throws InvalidAccountException {
        String SQLUpdate = "SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_NO = '" + accNo + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLUpdate, null);
        if (!cursor.moveToFirst()) {
            String msg = "Account " + accNo + " is invalid.";
            throw new InvalidAccountException(msg);
        } else {
            double balance = cursor.getDouble(0);
            switch (type) {
                case "DEPOSIT":
                    balance = balance + amount - trCharge;
                case "WITHDRAW":
                    balance = balance - amount - trCharge;
            }
            String setBalance = "UPDATE ACCOUNTS SET BALANCE= " + balance + " WHERE ACCOUNT_NO = '" + accNo + "';";
            db.execSQL(setBalance);
            db.close();
        }
    }

    public boolean checkBalance(String accNo,double amount, String type,double charge) throws InvalidAccountException{
        boolean isValid;
        String SQLUpdate = "SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_NO = '" + accNo + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLUpdate, null);
        if (!cursor.moveToFirst()) {
            String msg = "Account " + accNo + " is invalid.";
            throw new InvalidAccountException(msg);
        } else {
            double balance = cursor.getDouble(0);
            switch (type) {
                case "DEPOSIT":
                    balance = balance + amount-charge;
                case "WITHDRAW":
                    balance = balance - amount-charge;
            }
            //Assume that the minimum balance is LKR250.00
            if (balance >= 250) {
                isValid = true;
            }else{
                isValid = false;
            }
        }
        cursor.close();
        db.close();
        return isValid;
    }
}
