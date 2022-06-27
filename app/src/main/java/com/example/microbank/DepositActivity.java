package com.example.microbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import data.Model.Transaction;
import data.TransactionType;

public class DepositActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Button depositBtn = findViewById(R.id.btn_proceedDeposit);
        Spinner accSel = findViewById(R.id.spinner_1);
        TextView amount = findViewById(R.id.deposit_amount);
        TextView reference = findViewById(R.id.deposit_reference);




        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountNo = "2031134";
                String deposit_amount = amount.getText().toString();
                String deposit_reference = reference.getText().toString();
                String type = "DEPOSIT";
                double charge = 123.00;
                if (checkAmountValid(deposit_amount)){
                    double amount = Double.parseDouble(deposit_amount);
                    Transaction tr = new Transaction(accountNo,type,charge,amount,deposit_reference);

                    openConfirmDepositView(tr);
                }
                else{
                    Toast.makeText(DepositActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void openConfirmDepositView(Transaction tr){
        Intent intent = new Intent(this, ConfirmDepositActivity.class);
        intent.putExtra("Transaction",tr);
        startActivity(intent);
    }

    public boolean checkAmountValid(String amount){
        return amount.matches("[-+]?[0-9]*\\.?[0-9]+");
    }


}
