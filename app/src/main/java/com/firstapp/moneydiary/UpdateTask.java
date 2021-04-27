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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateTask extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_task);
//    }

    Button btn_update, btn_date2, btn_delete;
    EditText et_title2, et_description2, et_amount2;
    Spinner spn_category2;
    DatabaseHelper mDatabaseHelper;
    TextView tv_date2;
    UserModel userModel;

    String title, description, category;
    int id;
    float amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("userModel");

        btn_update = findViewById(R.id.btn_update_task);
        btn_delete = findViewById(R.id.btn_delete_task);
        btn_date2 = findViewById(R.id.btn_datepicker_task2);
        et_title2 = findViewById(R.id.et_title_task2);
        et_description2 =  findViewById(R.id.et_description_task2);
        et_amount2 = findViewById(R.id.et_amount_task2);
        spn_category2 = findViewById(R.id.spn_category_task2);
        tv_date2 = findViewById(R.id.tv_date_task2);

        getAndSetIntentData();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(UpdateTask.this);

                title = et_title2.getText().toString();
                description = et_description2.getText().toString();
                amount = Float.valueOf(et_amount2.getText().toString());
                Boolean res = db.updateTask(String.valueOf(id), title, description, amount);
                if(res == false){
                    Toast.makeText(UpdateTask.this, "Failed", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UpdateTask.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                }
                //Intent intent = new Intent(getApplicationContext(), ViewTask.class);
                //intent.putExtra("userModel",userModel);
                //startActivity(intent);
                finish();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateTask.this);
                Boolean res = myDB.deleteOneRowTask(String.valueOf(id));
                if(res == false){
                    Toast.makeText(UpdateTask.this, "Failed to Delete.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateTask.this, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });





    }


    void getAndSetIntentData(){
//        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
//                getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
        //Getting Data from Intent
        // id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        amount = getIntent().getFloatExtra("amount", 0);
        category = getIntent().getStringExtra("category");
        id = getIntent().getIntExtra("id", 0);

        //Toast.makeText(this, title, Toast.LENGTH_SHORT);
//            author = getIntent().getStringExtra("author");
//            pages = getIntent().getStringExtra("pages");
//
//            //Setting Intent Data
        et_title2.setText(title);
        et_description2.setText(description);
        et_amount2.setText(String.valueOf(amount));
        // spn_category2.setOnItemSelectedListener(category);
//            author_input.setText(author);
//            pages_input.setText(pages);
//            Log.d("stev", title+" "+author+" "+pages);
//        }else{
//            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
//        }
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
            Intent updateProfileIntent = new Intent(UpdateTask.this,UpdateProfileActivity.class);
            updateProfileIntent.putExtra("userModel",userModel);
            startActivity(updateProfileIntent);
        }
        else if(item.getItemId() == R.id.change_password){
            Intent changePasswordIntent = new Intent(UpdateTask.this,ChangePasswordActivity.class);
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
            Toast.makeText(UpdateTask.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
