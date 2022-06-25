package com.example.microbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomepageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Button logout = findViewById(R.id.logout_btn);
        ImageButton withdrawImgBtn = findViewById(R.id.withdrawalImgBtn);
        ImageButton depositImgBtn = findViewById(R.id.depositImgBtn);
        TextView nameTag = findViewById(R.id.name_tag);
        Intent intent = getIntent();
        String customer_id = intent.getStringExtra("ID");
        nameTag.setText("Hello "+customer_id);


        withdrawImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWithdrawalPage();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginpage();
            }
        });

        depositImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDepositpage();
            }
        });
    }

    public void openLoginpage(){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openWithdrawalPage(){

        Intent intent = new Intent(this, WithdrawalActivity.class);
        startActivity(intent);
    }

    public void openDepositpage(){

        Intent intent = new Intent(this, DepositActivity.class);
        startActivity(intent);
    }


}
