package com.example.microbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import data.DBHandler;

public class LoginActivity extends AppCompatActivity {
    DBHandler DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button submit = findViewById(R.id.btnSubmit);
        TextView pwdChange = findViewById(R.id.pwdForget);
        Toast pwdchng = Toast.makeText(LoginActivity.this, "Submitting Request", Toast.LENGTH_SHORT);
        DB = new DBHandler(this);
//        DB.init();
       // DB.setup();
       // DB.setup_customers();
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
                    Boolean checkUserPass = DB.checkUserNamePassword(customerID,password);
                    if (checkUserPass){
                        Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),HomepageActivity.class);
                        intent.putExtra("ID",customerID);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });



    }

}