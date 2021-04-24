package com.firstapp.moneydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;

public class DashboardActivity extends AppCompatActivity {
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("userModel");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.update_profile){
            Intent updateProfileIntent = new Intent(DashboardActivity.this,UpdateProfileActivity.class);
            updateProfileIntent.putExtra("userModel",userModel);
            startActivity(updateProfileIntent);
        }
        else if(item.getItemId() == R.id.change_password){
            Intent changePasswordIntent = new Intent(DashboardActivity.this,ChangePasswordActivity.class);
            changePasswordIntent.putExtra("userModel",userModel);
            startActivity(changePasswordIntent);
        }
        else if(item.getItemId() == R.id.logout){
            finish();
            Intent logoutIntent = new Intent(getApplicationContext(),MainActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
            Toast.makeText(DashboardActivity.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
