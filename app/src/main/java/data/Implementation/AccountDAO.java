package data.Implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import data.Model.Account;
import data.DBHandler;

public class AccountDAO extends DBHandler implements data.AccountDAO {
    private Context context;
    public AccountDAO(@Nullable Context context) {
        super(context);
        this.context=context;
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

    public List<Account> getAccountsList(String CustomerID){
        List<Account> accountList = new ArrayList<>();
        String queryString = "SELECT * FROM ACCOUNTS WHERE CUSTOMER_ID='"+CustomerID+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            do {
                String accNo = cursor.getString(0);
                String customerID = cursor.getString(1);
                String accountType = cursor.getString(2);
                Float balance = cursor.getFloat(3);
                Account account = new Account(accNo,customerID,accountType,balance);
                accountList.add(account);
            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return accountList;
    }
}
