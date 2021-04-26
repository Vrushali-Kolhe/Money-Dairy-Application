package com.firstapp.moneydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateTransaction extends AppCompatActivity {

    Button btn_update, btn_date2, btn_delete;
    EditText et_title2, et_description2, et_amount2;
    Spinner spn_category2;
    DatabaseHelper mDatabaseHelper;
    TextView tv_date2;

    String title, description, category;
    int id;
    float amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_transaction);


        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_date2 = findViewById(R.id.btn_datepicker2);
        et_title2 = findViewById(R.id.et_title2);
        et_description2 =  findViewById(R.id.et_description2);
        et_amount2 = findViewById(R.id.et_amount2);
        spn_category2 = findViewById(R.id.spn_category2);
        tv_date2 = findViewById(R.id.tv_date2);

        getAndSetIntentData();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(UpdateTransaction.this);

                title = et_title2.getText().toString();
                description = et_description2.getText().toString();
                amount = Float.valueOf(et_amount2.getText().toString());
                db.updateTransaction(String.valueOf(id), title, description, amount);
                Intent intent = new Intent(getApplicationContext(), ViewTransactionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateTransaction.this);
                myDB.deleteOneRow(String.valueOf(id));
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

}