package com.example.microbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import data.DBHandler;
import data.Implementation.TransactionDAO;
import data.Model.Transaction;

public class ConfirmDepositActivity extends AppCompatActivity {
    TransactionDAO transactionDAO = new TransactionDAO(this);
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_deposit);
        Button confirm = findViewById(R.id.conDepositBtn);
        TextView depositAccount = findViewById(R.id.depositToAcc);
        TextView depositAmount = findViewById(R.id.depositAmount);
        TextView depositRef = findViewById(R.id.depositReference);
        Transaction tr = getIntent().getParcelableExtra("Transaction");
        depositAccount.setText(tr.getAccNo());
        depositAmount.setText(String.valueOf(tr.getAmount()));
        depositRef.setText(tr.getType());
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transactionDAO.addTransaction(tr)){
                    Toast.makeText(ConfirmDepositActivity.this, "Transaction addedd successfully", Toast.LENGTH_LONG).show();
                    openHomePage();
                };
            }
        });
    }

    public void openHomePage(){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}
