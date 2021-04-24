package com.firstapp.moneydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mTextViewRegister = (TextView)findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        /*mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mTextUsername.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                boolean res = db.checkUser(username,password);
                if(res==false){
                    Toast.makeText(MainActivity.this,"User Not Found",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent dashboardScreen = new Intent(MainActivity.this,DashboardActivity.class);
                    startActivity(dashboardScreen);
                }
            }
        });*/

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mTextUsername.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                UserModel userModel = db.checkUser(username,password);
                if(userModel==null){
                    Toast.makeText(MainActivity.this,"User Not Found",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent dashboardScreen = new Intent(MainActivity.this,DashboardActivity.class);
                    dashboardScreen.putExtra("userModel",userModel);
                    startActivity(dashboardScreen);
                }
            }
        });
    }
}
