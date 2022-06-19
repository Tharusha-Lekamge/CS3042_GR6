package com.example.microbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button submit = findViewById(R.id.btnSubmit);
        TextView pwdChange = findViewById(R.id.pwdForget);
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

                boolean isUser = isUser(userID.getText().toString(),userPwd.getText().toString());
                if (isUser){
                    Toast validUser = Toast.makeText(getApplicationContext(), "Hello " + userID.getText().toString(), Toast.LENGTH_LONG);
                    validUser.show();
                    userID.getText().clear();
                    userPwd.getText().clear();
                }
                else
                {
                    Toast invalidUser = Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG);
                    invalidUser.show();
                    userID.getText().clear();
                    userPwd.getText().clear();
                }

            }
        });



    }

    protected boolean isUser(String userID,String pwd){
        return (userID.equals("User123") && pwd.equals("GoHomeGota2022"));
    }
}