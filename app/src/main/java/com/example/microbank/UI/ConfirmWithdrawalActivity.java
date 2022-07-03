package com.example.microbank.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.microbank.Control.AppController;
import com.example.microbank.Control.AppController_ab;
import com.example.microbank.R;
import com.example.microbank.data.Exception.InvalidAccountException;
import com.example.microbank.data.Model.Transaction;

public class ConfirmWithdrawalActivity extends AppCompatActivity {

    private AppController_ab appController = new AppController(ConfirmWithdrawalActivity.this);

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_withdraw);

        Button confirm = findViewById(R.id.conWithdrawBtn);
        TextView fromAcc = findViewById(R.id.fromAccNo);
        TextView withdraw_amount = findViewById(R.id.withdrawalAmount);
        TextView withdraw_reference = findViewById(R.id.withdrawRef);

        Transaction tr = getIntent().getParcelableExtra("Transaction");

        String accNo = tr.getAccNo();
        Double amount = tr.getAmount();
        String reference = tr.getReference();
        Double charge = appController.getTrCharge();
        String type = "WITHDRAW";

        fromAcc.setText(accNo);
        withdraw_amount.setText(String.valueOf(amount));
        withdraw_reference.setText(reference);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (appController.getAccountDAO().checkBalance(accNo,amount,type,charge)){
                        appController.addTransaction(accNo,type,amount,reference);
                        Toast.makeText(ConfirmWithdrawalActivity.this, "Transaction added successfully", Toast.LENGTH_LONG).show();
                        openHomePage();
                    }
                } catch (InvalidAccountException e) {
                    e.printStackTrace();
                }
                ;
            }
        });
    }

    public void openHomePage(){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}
