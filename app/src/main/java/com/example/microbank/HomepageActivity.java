package com.example.microbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ImageView;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import data.Implementation.AccountDAO;
import data.Model.Account;

public class HomepageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        Button logout = findViewById(R.id.logoutBtn);
        ImageButton withdrawImgBtn = findViewById(R.id.withdrawalImgBtn);
        ImageButton depositImgBtn = findViewById(R.id.depositImgBtn);

        TextView nameTag = findViewById(R.id.name_tag);
        Intent intent = getIntent();
        String customer_id = intent.getStringExtra("ID");
        nameTag.setText("Hello "+customer_id);


        RecyclerView rv = findViewById(R.id.rv_accList);
        AccountDAO getAcc = new AccountDAO(this);
        List<Account> accountList = new ArrayList<>();
        accountList = getAcc.getAccountsList(customer_id);


        AccDisplayAdapter arr_adp = new AccDisplayAdapter(this, accountList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(arr_adp);

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
