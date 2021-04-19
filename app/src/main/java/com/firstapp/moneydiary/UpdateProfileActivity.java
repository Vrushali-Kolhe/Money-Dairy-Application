package com.firstapp.moneydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextPhoneNumber;
    Button mButtonUpdate;
    DatabaseHelper db;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextPhoneNumber = (EditText) findViewById(R.id.edittext_phone_number);
        mButtonUpdate = (Button) findViewById(R.id.button_update);

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mTextUsername.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String phone_number = mTextPhoneNumber.getText().toString().trim();

                Boolean res = db.updateUser(username,email,phone_number);
                if(res== true){
                    Intent dashboardScreen = new Intent(UpdateProfileActivity.this,DashboardActivity.class);
                    startActivity(dashboardScreen);
                }
                else{
                    Toast.makeText(UpdateProfileActivity.this,"Error in Updating",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
