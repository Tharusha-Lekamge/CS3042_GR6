package com.example.microbank.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microbank.Control.AppController;
import com.example.microbank.Control.AppController_ab;
import com.example.microbank.Control.SessionManagement;
import com.example.microbank.R;

import com.example.microbank.data.DBHandler;
import com.example.microbank.data.Model.Customer;

public class LoginActivity extends AppCompatActivity {
    AppController_ab appController = new AppController(LoginActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button submit = findViewById(R.id.btnSubmit);
        TextView pwdChange = findViewById(R.id.pwdForget);
        Thread dataThread = new Thread(){
            public void run(){
                  appController.getDataforAgent();
            }
        };
        dataThread.start();
//        appController.getDataforAgent();
        Toast pwdchng = Toast.makeText(LoginActivity.this, "Submitting Request", Toast.LENGTH_SHORT);
        pwdChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdchng.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = findViewById(R.id.userId);
                EditText userPwd = findViewById(R.id.userpwd);
                String customerID = userID.getText().toString();
                String password = userPwd.getText().toString();
                if (customerID.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this,"Please fill all the fields to Login",Toast.LENGTH_SHORT).show();
                }
                else{
                    Customer newCustomer = appController.getCustomerDAO().checkUserNamePassword(customerID,password);
                    if (newCustomer!=null){
                        Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                        boolean isSpecialRequest = appController.getCustomerDAO().isSpecialCustomer(customerID);
                        sessionManagement.saveSession(newCustomer, isSpecialRequest);
                        if (!isSpecialRequest)
                            openHomePage();
                        else
                            openWithdrawalPage();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });
    }

    protected void onStart(){
        super.onStart();
//        checkSession();
    }

    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        String userId = sessionManagement.getSession();
        boolean specialReq = sessionManagement.isSpecialRequest();
        if (!userId.equals("Over") && !specialReq){
            openHomePage();
        }
        else if (!userId.equals("Over") && specialReq){
            openWithdrawalPage();
        }
        else{

        }
    }

    private void openHomePage() {
        Intent intent = new Intent(getApplicationContext(),HomepageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openWithdrawalPage(){
        Intent intent = new Intent(getApplicationContext(),WithdrawalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}