package com.firstapp.moneydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        db = new DatabaseHelper(this);

        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextPhoneNumber = (EditText) findViewById(R.id.edittext_phone_number);
        mButtonUpdate = (Button) findViewById(R.id.button_update);

        Intent intent = getIntent();
        user = (UserModel) intent.getSerializableExtra("userModel");
        mTextUsername.setText(user.getUsername());
        mTextEmail.setText(user.getEmail());
        mTextPhoneNumber.setText(user.getPhone_Number());

        /*mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mTextUsername.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String phone_number = mTextPhoneNumber.getText().toString().trim();

                Boolean res = db.updateUser(username,email,phone_number);
                if(res== true){
                    Toast.makeText(UpdateProfileActivity.this,"Upadated Successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UpdateProfileActivity.this,"Error in Updating",Toast.LENGTH_SHORT).show();
                }
                Intent dashboardScreen = new Intent(UpdateProfileActivity.this,DashboardActivity.class);
                dashboardScreen.putExtra("userModel",user);
                startActivity(dashboardScreen);
            }
        });*/

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserModel userModel = db.find(user.getUser_id());
                    String username = mTextUsername.getText().toString().trim();
                    String email = mTextEmail.getText().toString().trim();
                    String phone_number = mTextPhoneNumber.getText().toString().trim();
                    UserModel temp = db.checkUserName(username);

                    if(!username.isEmpty()){
                       if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                           if(!phone_number.isEmpty() && Patterns.PHONE.matcher(phone_number).matches()){
                               if(!username.equalsIgnoreCase(userModel.getUsername()) && temp != null){
                                   Toast.makeText(UpdateProfileActivity.this, "Username Exists", Toast.LENGTH_SHORT).show();
                                   return;
                               }

                               userModel.setUsername(username);
                               userModel.setEmail(email);
                               userModel.setPhone_Number(phone_number);
                               Boolean res = db.updateUser(userModel);
                               if (res == true) {
                                   Toast.makeText(UpdateProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                               } else {
                                   Toast.makeText(UpdateProfileActivity.this, "Error in Updating", Toast.LENGTH_SHORT).show();
                               }

                               Intent dashboardScreen = new Intent(UpdateProfileActivity.this, DashboardActivity.class);
                               dashboardScreen.putExtra("userModel", userModel);
                               startActivity(dashboardScreen);

                           }
                           else{
                               Toast.makeText(UpdateProfileActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                           }
                       }
                       else{
                           Toast.makeText(UpdateProfileActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                       }
                    }
                    else{
                        Toast.makeText(UpdateProfileActivity.this, "Username Required", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e){
                    Toast.makeText(UpdateProfileActivity.this, "Error in Updating", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.update_profile){
            Intent updateProfileIntent = new Intent(UpdateProfileActivity.this,UpdateProfileActivity.class);
            updateProfileIntent.putExtra("userModel",user);
            startActivity(updateProfileIntent);
        }
        else if(item.getItemId() == R.id.change_password){
            Intent changePasswordIntent = new Intent(UpdateProfileActivity.this,ChangePasswordActivity.class);
            changePasswordIntent.putExtra("userModel",user);
            startActivity(changePasswordIntent);
        }
        else if(item.getItemId() == R.id.logout){
            Intent logoutIntent = new Intent(getApplicationContext(),MainActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
            Toast.makeText(UpdateProfileActivity.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
