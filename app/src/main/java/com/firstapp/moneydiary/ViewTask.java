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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewTask extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_task);
//    }
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    UserModel userModel;

    //reference to
    FloatingActionButton btn_fab;
    RecyclerView rv_task;
    EditText et_search;

    ArrayList<TaskModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        Intent intent = getIntent();
        userModel = (UserModel) intent.getSerializableExtra("userModel");

        btn_fab = findViewById(R.id.btn_fab_task);
        rv_task = findViewById(R.id.rv_task);
        et_search = findViewById(R.id.et_search_task);

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

        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), AddTaskActivity.class);
                registerIntent.putExtra("userModel",userModel);
                startActivity(registerIntent);
            }
        });



//        ArrayList <TransactionModel> transactionList = new ArrayList<>();
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
//        transactionList.add(new TransactionModel(1, "Electricity bill",   "some description", 100, "Food"));
        DatabaseHelper databaseHelper = new DatabaseHelper(ViewTask.this);
        taskList = databaseHelper.getAllTasks();
        rv_task.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);


        if (taskList.size() > 0){
            rv_task.setVisibility(View.VISIBLE);
            mAdapter = new TaskAdapter(ViewTask.this,this, taskList);
            rv_task.setAdapter(mAdapter);

        }
        else {
            rv_task.setVisibility(View.GONE);
            Toast.makeText(this, "There is no tasks in the database. Start adding now", Toast.LENGTH_LONG).show();
        }


        rv_task.setLayoutManager(mLayoutManager);
        rv_task.setAdapter(mAdapter);
        if (taskList.size()> 0) {
            mAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    Intent intent = new Intent(getApplicationContext(), UpdateTask.class);
                    intent.putExtra("title", String.valueOf(taskList.get(position).getTitle()));
                    intent.putExtra("description", String.valueOf(taskList.get(position).getDescription()));
                    // intent.putExtra("amount", String.valueOf(transactionList.get(position).getAmount()));
                    intent.putExtra("category", String.valueOf(taskList.get(position).getCategory()));
                    intent.putExtra("id", taskList.get(position).getId());
                    intent.putExtra("amount", taskList.get(position).getAmount());


                    //transactionList.get(position).
                    intent.putExtra("userModel",userModel);
                    startActivityForResult(intent, 1);
                    //startActivity(intent);
                    Toast.makeText(ViewTask.this, "clicked!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void filter(String text){
        //new array list that will hold the filtered data
        ArrayList<TaskModel> filteredList = new ArrayList<>();

        //looping through existing elements
        for (TaskModel task : taskList) {
            //if the existing elements contains the search input
            if (task.getTitle().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filteredList.add(task);
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
            Intent updateProfileIntent = new Intent(ViewTask.this,UpdateProfileActivity.class);
            updateProfileIntent.putExtra("userModel",userModel);
            startActivity(updateProfileIntent);
        }
        else if(item.getItemId() == R.id.change_password){
            Intent changePasswordIntent = new Intent(ViewTask.this,ChangePasswordActivity.class);
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
            Toast.makeText(ViewTask.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}