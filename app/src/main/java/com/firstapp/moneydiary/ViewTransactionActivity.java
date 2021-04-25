package com.firstapp.moneydiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewTransactionActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TransactionsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //reference to
    FloatingActionButton btn_fab;
    RecyclerView rv_transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        btn_fab = findViewById(R.id.btn_fab);
        rv_transactions = findViewById(R.id.rv_transactions);

        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivity(registerIntent);
            }
        });


//        ArrayList <TransactionModel> transactionList = new ArrayList<>();
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
        DatabaseHelper databaseHelper = new DatabaseHelper(ViewTransactionActivity.this);
        final ArrayList<TransactionModel> transactionList = databaseHelper.getAllData();
        rv_transactions.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);


        if (transactionList.size() > 0){
            rv_transactions.setVisibility(View.VISIBLE);
            mAdapter = new TransactionsAdapter(this, transactionList);
            rv_transactions.setAdapter(mAdapter);

        }
        else {
            rv_transactions.setVisibility(View.GONE);
            Toast.makeText(this, "There is no transactions in the database. Start adding now", Toast.LENGTH_LONG).show();
        }


        rv_transactions.setLayoutManager(mLayoutManager);
         rv_transactions.setAdapter(mAdapter);
         mAdapter.setOnItemClickListener(new TransactionsAdapter.OnItemClickListener() {
             @Override
             public void OnItemClick(int position) {
                 Intent intent = new Intent(getApplicationContext(), UpdateTransaction.class);
                 //intent.getExtras("title", String.valueOf())
                 //transactionList.get(position).
                 startActivity(intent);
                 Toast.makeText(ViewTransactionActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
             }
         });

    }

}