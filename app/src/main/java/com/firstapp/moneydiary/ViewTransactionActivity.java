package com.firstapp.moneydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewTransactionActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TransactionsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseHelper databaseHelper;
    
    UserModel userModel;



    //reference to
    FloatingActionButton btn_fab;
    RecyclerView rv_transactions;
    EditText et_search;

    ArrayList<TransactionModel> transactionList;
    //TransactionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        btn_fab = findViewById(R.id.btn_fab);
        rv_transactions = findViewById(R.id.rv_transactions);
        et_search = findViewById(R.id.et_search);
        refresh_transaction = findViewById(R.id.refresh_transaction);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("userModel");

        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                registerIntent.putExtra("userModel",userModel);
                startActivity(registerIntent);
            }
        });
        
        refresh_transaction.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               transactionList = databaseHelper.getAllData();
                mAdapter.filterList(transactionList);
                //refresh_transaction.setOnRefreshListener();
                refresh_transaction.setRefreshing(false);
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });


//        ArrayList <TransactionModel> transactionList = new ArrayList<>();
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
        databaseHelper = new DatabaseHelper(ViewTransactionActivity.this);
        transactionList = databaseHelper.getAllData();
        rv_transactions.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);


        if (transactionList.size() > 0){
            rv_transactions.setVisibility(View.VISIBLE);
            mAdapter = new TransactionsAdapter(ViewTransactionActivity.this,this, transactionList);
            rv_transactions.setAdapter(mAdapter);

        }
        else {
            rv_transactions.setVisibility(View.GONE);
            Toast.makeText(this, "There is no transactions in the database. Start adding now", Toast.LENGTH_LONG).show();
        }


        rv_transactions.setLayoutManager(mLayoutManager);
         rv_transactions.setAdapter(mAdapter);
         if (transactionList.size() > 0) {
             mAdapter.setOnItemClickListener(new TransactionsAdapter.OnItemClickListener() {
                 @Override
                 public void OnItemClick(int position) {
                     Intent intent = new Intent(getApplicationContext(), UpdateTransaction.class);
                     intent.putExtra("title", String.valueOf(transactionList.get(position).getTitle()));
                     intent.putExtra("description", String.valueOf(transactionList.get(position).getDescription()));
                     // intent.putExtra("amount", String.valueOf(transactionList.get(position).getAmount()));
                     intent.putExtra("category", String.valueOf(transactionList.get(position).getCategory()));
                     intent.putExtra("id", transactionList.get(position).getId());
                     intent.putExtra("amount", transactionList.get(position).getAmount());


                     //transactionList.get(position).
                     intent.putExtra("userModel",userModel);
                     startActivity(intent);
                     Toast.makeText(ViewTransactionActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
                 }
             });
         }

    }

    private void filter(String text){
        //new array list that will hold the filtered data
        ArrayList<TransactionModel> filteredList = new ArrayList<>();

        //looping through existing elements
        for (TransactionModel transaction : transactionList) {
            //if the existing elements contains the search input
            if (transaction.getTitle().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filteredList.add(transaction);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mAdapter.filterList(filteredList);
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
            Intent updateProfileIntent = new Intent(ViewTransactionActivity.this,UpdateProfileActivity.class);
            updateProfileIntent.putExtra("userModel",userModel);
            startActivity(updateProfileIntent);
        }
        else if(item.getItemId() == R.id.change_password){
            Intent changePasswordIntent = new Intent(ViewTransactionActivity.this,ChangePasswordActivity.class);
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
            Toast.makeText(ViewTransactionActivity.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
