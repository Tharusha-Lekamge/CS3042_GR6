package com.example.microbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DepositActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);


        Button depositBtn = findViewById(R.id.depositBtn);

        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfirmDepositView();
            }
        });
    }

    public void openConfirmDepositView(){
        Intent intent = new Intent(this, ConfirmDepositActivity.class);
        startActivity(intent);
    }


}