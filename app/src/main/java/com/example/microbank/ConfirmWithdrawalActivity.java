package com.example.microbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmWithdrawalActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){

        Button confirm = findViewById(R.id.conWithdrawBtn);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_withdraw);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomePage();
            }
        });
    }

    public void openHomePage(){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}
