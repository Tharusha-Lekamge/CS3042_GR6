package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DB_NAME = "microDB";



    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAccounts = "CREATE TABLE ACCOUNTS (" +
                "ACCOUNT_NO VARCHAR(20) NOT NULL," +
                "CUSTOMER_ID VARCHAR(10) NOT NULL," +
                "ACCOUNT_TYPE CHECK (ACCOUNT_TYPE IN ('CHILDREN','TEEN','ADULT','SENIOR','JOINT'))," +
                "PASSWORD VARCHAR(50) NOT NULL," +
                "BALANCE FLOAT," +
                "PRIMARY KEY(ACCOUNT_NO))";

        String createTableTransactions = "CREATE TABLE TRANSACTIONS (" +
                "TRANSACTION_ID VARCHAR(10) NOT NULL," +
                "ACCOUNT_NO VARCHAR(20)," +
                "DATE DATE," +
                "TIME DATE," +
                "TRANSACTION_TYPE CHECK (TRANSACTION_TYPE IN ('WITHDRAW','DEPOSIT'))," +
                "TRANSACTION_CHARGE FLOAT," +
                "PRIMARY KEY(TRANSACTION_ID)," +
                "FOREIGN KEY(ACCOUNT_NO) REFERENCES ACCOUNTS(ACCOUNT_NO))";
        db.execSQL(createTableAccounts);
        db.execSQL(createTableTransactions);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean checkUserName(String CustomerID){
        SQLiteDatabase accountsTable = this.getWritableDatabase();
        Cursor cursor = accountsTable.rawQuery("SELECT * FROM ACCOUNTS WHERE CUSTOMER_ID=?",new String[]{CustomerID});
        if (cursor.getCount()>0){
            return true;
        }
        return false;
    }

    public Boolean checkUserNamePassword(String username, String password){
        SQLiteDatabase accountsTable = this.getWritableDatabase();
        Cursor cursor = accountsTable.rawQuery("SELECT * FROM ACCOUNTS WHERE CUSTOMER_ID=? AND PASSWORD=?",new String[]{username,password});
        if (cursor.getCount()>0){
            return true;
        }
        return false;
    }
}
