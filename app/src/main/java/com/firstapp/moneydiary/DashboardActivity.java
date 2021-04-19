package com.firstapp.moneydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
            startActivity(updateProfileIntent);
        }
        else if(item.getItemId() == R.id.change_password){
            Intent changePasswordIntent = new Intent(DashboardActivity.this,ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
        }
        else if(item.getItemId() == R.id.logout){
            finish();
            Intent logoutIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(logoutIntent);
            Toast.makeText(DashboardActivity.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
