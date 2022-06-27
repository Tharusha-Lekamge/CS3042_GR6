package com.example.microbank.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.microbank.R;

public class WithdrawalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        Button withdrawalBtn = findViewById(R.id.withdrawalBtn);

        withdrawalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfrimWithdrawalPage();
            }
        });
    }

    public void openConfrimWithdrawalPage(){
        Intent intent = new Intent(this, ConfirmWithdrawalActivity.class);
        startActivity(intent);
    }
}