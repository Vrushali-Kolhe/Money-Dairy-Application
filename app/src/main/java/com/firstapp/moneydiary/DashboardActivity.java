package com.firstapp.moneydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

public class DashboardActivity extends AppCompatActivity {
    UserModel userModel;
    Button mButtonViewTransaction;
    Button mButtonViewTask;
    Button mButtonGenerateReport;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = new DatabaseHelper(this);
        mButtonViewTransaction = (Button) findViewById(R.id.button_view_transaction);
        mButtonViewTask = (Button) findViewById(R.id.button_view_task);
        mButtonGenerateReport = (Button) findViewById(R.id.button_generate_report);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("userModel");

        mButtonViewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view_transaction = new Intent(DashboardActivity.this,ViewTransactionActivity.class);
                view_transaction.putExtra("userModel",userModel);
                startActivity(view_transaction);
            }
        });

        mButtonViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view_task = new Intent(DashboardActivity.this,ViewTask.class);
                view_task.putExtra("userModel",userModel);
                startActivity(view_task);
            }
        });

        mButtonGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generate_report = new Intent(DashboardActivity.this,ReportActivity.class);
                generate_report.putExtra("userModel",userModel);
                startActivity(generate_report);
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
