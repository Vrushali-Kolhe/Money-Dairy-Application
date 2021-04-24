package com.firstapp.moneydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText mTextCurrentPassword;
    EditText mTextNewPassword;
    EditText mTextConfirmPassword;
    Button mButtonDone;
    DatabaseHelper db;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        db = new DatabaseHelper(this);

        mTextCurrentPassword = (EditText) findViewById(R.id.edittext_current_password);
        mTextNewPassword = (EditText) findViewById(R.id.edittext_new_password);
        mTextConfirmPassword = (EditText) findViewById(R.id.edittext_confirm_password);
        mButtonDone = (Button) findViewById(R.id.button_done);

        Intent intent = getIntent();
        user = (UserModel) intent.getSerializableExtra("userModel");

        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = mTextCurrentPassword.getText().toString().trim();
                String newPassword = mTextNewPassword.getText().toString().trim();
                String confirmPassword = mTextConfirmPassword.getText().toString().trim();

                if(!currentPassword.isEmpty()){
                    if(!newPassword.isEmpty()){
                        if(!confirmPassword.isEmpty()){
                            if (currentPassword.equals(user.getPassword())) {
                                if (newPassword.equals(confirmPassword)) {
                                    user.setPassword(newPassword);
                                    Boolean res = db.updateUser(user);
                                    if (res == true) {
                                        Toast.makeText(ChangePasswordActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ChangePasswordActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                                    }
                                    Intent dashboardScreen = new Intent(ChangePasswordActivity.this, DashboardActivity.class);
                                    dashboardScreen.putExtra("userModel", user);
                                    startActivity(dashboardScreen);
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "Password are not matching", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Invalid Current Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                                Toast.makeText(ChangePasswordActivity.this, "Confirm Password Required", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(ChangePasswordActivity.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this, "Current Password Required", Toast.LENGTH_SHORT).show();
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
            Intent updateProfileIntent = new Intent(ChangePasswordActivity.this,UpdateProfileActivity.class);
            updateProfileIntent.putExtra("userModel",user);
            startActivity(updateProfileIntent);
        }
        else if(item.getItemId() == R.id.change_password){
            Intent changePasswordIntent = new Intent(ChangePasswordActivity.this,ChangePasswordActivity.class);
            changePasswordIntent.putExtra("userModel",user);
            startActivity(changePasswordIntent);
        }
        else if(item.getItemId() == R.id.logout){
            finish();
            Intent logoutIntent = new Intent(getApplicationContext(),MainActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
            Toast.makeText(ChangePasswordActivity.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
