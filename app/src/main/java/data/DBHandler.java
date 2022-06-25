package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Console;
import java.io.IOException;

import data.Implementation.AccountDAO;
import data.Model.Account;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DB_NAME = "microDB";
    Context context;


    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Main activity", "checkUserNamePassword: ");
        String dropTableAccounts = "DROP TABLE IF EXISTS ACCOUNTS";
        String dropTableCustomers = "DROP TABLE IF EXISTS CUSTOMERS";
        String dropTableTransactions = "DROP TABLE IF EXISTS TRANSACTIONS";
        String createTableAccounts = "CREATE TABLE ACCOUNTS (" +
                "ACCOUNT_NO VARCHAR(20) NOT NULL," +
                "CUSTOMER_ID VARCHAR(10) NOT NULL," +
                "ACCOUNT_TYPE CHECK (ACCOUNT_TYPE IN ('CHILDREN','TEEN','ADULT','SENIOR','JOINT'))," +
                "BALANCE FLOAT," +
                "PRIMARY KEY(ACCOUNT_NO)," +
                "FOREIGN KEY(CUTOMER_ID) REFERENCES CUSTOMERS(CUSTOMER_ID))";

        String createTableTransactions = "CREATE TABLE TRANSACTIONS (" +
                "TRANSACTION_ID VARCHAR(10) NOT NULL," +
                "ACCOUNT_NO VARCHAR(20)," +
                "DATE DATE," +
                "TIME DATE," +
                "TRANSACTION_TYPE CHECK (TRANSACTION_TYPE IN ('WITHDRAW','DEPOSIT'))," +
                "TRANSACTION_CHARGE FLOAT," +
                "PRIMARY KEY(TRANSACTION_ID)," +
                "FOREIGN KEY(ACCOUNT_NO) REFERENCES ACCOUNTS(ACCOUNT_NO))";

        String createTableCustomers = "CREATE TABLE CUSTOMERS (" +
                "CUSTOMER_ID VARCHAR(10) NOT NULL," +
                "PASSWORD VARCHAR(50) NOT NULL," +
                "PRIMARY KEY(CUSTOMER_ID))";
        db.execSQL(dropTableAccounts);
        db.execSQL(dropTableTransactions);
        db.execSQL(dropTableCustomers);
        db.execSQL(createTableAccounts);
        db.execSQL(createTableTransactions);
        db.execSQL(createTableCustomers);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean checkUserName(String CustomerID){
        SQLiteDatabase accountsTable = this.getWritableDatabase();
        Cursor cursor = accountsTable.rawQuery("SELECT * FROM ACCOUNTS WHERE CUSTOMER_ID=?",new String[]{CustomerID});
        accountsTable.close();
        if (cursor.getCount()>0){
            return true;
        }
        return false;
    }

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

    public void setup(){
        Account acc1 = new Account("2039134","123432","ADULT",12500);
        Account acc2 = new Account("2031134","123432","TEEN",15500);
        AccountDAO accDAO = new AccountDAO(this.context);
        accDAO.addAccount(acc1);
        accDAO.addAccount(acc2);
    }

    public void setup_customers(){
        ContentValues cv = new ContentValues();
        cv.put("CUSTOMER_ID","123432");
        cv.put("PASSWORD","#YoLo123");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("CUSTOMERS",null,cv);
        db.close();
    }

    public void LoadData(){
        OkHttpClient client = new OkHttpClient();
        String url = "";
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String dataString = response.body().string();

                }
            }
        });

    }
}
