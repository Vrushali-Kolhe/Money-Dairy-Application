package com.firstapp.moneydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    EditText mTextEmail;
    EditText mTextPhoneNumber;
    Button mButtonRegister;
    TextView mTextViewLogin;
    DatabaseHelper db;
    UserModel userModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextConfirmPassword = (EditText) findViewById(R.id.edittext_confirm_password);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextPhoneNumber = (EditText) findViewById(R.id.edittext_phone_number);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mTextUsername.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                String confirm_password = mTextConfirmPassword.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String phone_number = mTextPhoneNumber.getText().toString().trim();

                if (password.equals(confirm_password)) {
                    try{
                        userModel = new UserModel(-1,username,password,email,phone_number);
                    }
                    catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "UserModel Error", Toast.LENGTH_SHORT).show();
                    }
                    long val = db.addUser(userModel);
                    if (val > 0) {
                        Toast.makeText(RegisterActivity.this, "You have registered successfully", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(moveToLogin);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Password are not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
