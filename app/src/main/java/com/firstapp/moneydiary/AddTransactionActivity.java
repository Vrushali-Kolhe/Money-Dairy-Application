package com.firstapp.moneydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn_add, btn_date;
    EditText et_title, et_description, et_amount;
    Spinner spn_category;
    DatabaseHelper mDatabaseHelper;
    TextView tv_date;
    String date = "Wed Mar 27 08:22:02 IST 2015";
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        mDatabaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("userModel");

        btn_add = findViewById(R.id.btn_add);
        btn_date = findViewById(R.id.btn_datepicker);
        et_title = findViewById(R.id.et_title);
        et_description =  findViewById(R.id.et_description);
        et_amount = findViewById(R.id.et_amount);
        spn_category = findViewById(R.id.spn_category);
        tv_date = findViewById(R.id.tv_date);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_category.setAdapter(adapter);
        spn_category.setOnItemSelectedListener(this);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    TransactionModel transactionModel = new TransactionModel(1,
                            et_title.getText().toString(),
                            date,
                            et_description.getText().toString(),
                            Float.valueOf(et_amount.getText().toString()),
                            spn_category.getSelectedItem().toString());
                    Toast.makeText(AddTransactionActivity.this, transactionModel.getTitle(), Toast.LENGTH_SHORT);
                    boolean insertData = mDatabaseHelper.insertData(transactionModel);
                    if (insertData) {
                        Toast.makeText(AddTransactionActivity.this, "Transaction added!", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(AddTransactionActivity.this,ViewTransactionActivity.class);
                        //intent.putExtra("userModel",userModel);
                        //startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(AddTransactionActivity.this, "Transaction not added!", Toast.LENGTH_SHORT).show();
                        //Intent intent1 = new Intent(AddTransactionActivity.this,ViewTransactionActivity.class);
                        //intent1.putExtra("userModel",userModel);
                        //startActivity(intent1);
                        finish();

                    }

                   // Toast.makeText(AddTransactionActivity.this, "Error!", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void handleDateButton(){
        final Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                String dateString = date + "/" + month + "/" + year;
                tv_date.setText(dateString);

            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.show();
        Toast.makeText(this, "date button", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            Intent updateProfileIntent = new Intent(AddTransactionActivity.this,UpdateProfileActivity.class);
            updateProfileIntent.putExtra("userModel",userModel);
            startActivity(updateProfileIntent);
        }
        else if(item.getItemId() == R.id.change_password){
            Intent changePasswordIntent = new Intent(AddTransactionActivity.this,ChangePasswordActivity.class);
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
            Toast.makeText(AddTransactionActivity.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
