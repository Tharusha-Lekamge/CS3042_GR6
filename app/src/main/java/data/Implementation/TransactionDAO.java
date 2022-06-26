package data.Implementation;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import data.DBHandler;
import data.Model.Transaction;

public class TransactionDAO extends DBHandler implements data.TransactionDAO {
    public TransactionDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        String accNo = transaction.getAccNo();
        String type = transaction.getType();
        Double trCharge = transaction.getTrCharge();
        Double amount = transaction.getAmount();
        String reference = transaction.getReference();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        ContentValues cv = new ContentValues();
        cv.put("ACCOUNT_NO",accNo);
        cv.put("TIMESTAMP",currentDateandTime);
        cv.put("TRANSACTION_TYPE",type);
        cv.put("TRANSACTION_CHARGE",trCharge);
        cv.put("AMOUNT",amount);
        cv.put("REFERENCE",reference);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try{

            db.insert("TRANSACTIONS",null,cv);
            updateBalance(accNo,type,amount);
            db.setTransactionSuccessful();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {

            db.endTransaction();
            db.close();
            return true;
        }



    }
}
