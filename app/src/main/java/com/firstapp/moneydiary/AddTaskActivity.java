package com.firstapp.moneydiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.wdullare.amterialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public abstract class AddTaskActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,AdapterView.OnItemSelectedListener {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_task);
//    }
    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    Button btn_add, btn_date, btn_setnotification;
    EditText et_title, et_description, et_amount, et_type;
    Spinner spn_category;
    DatabaseHelper mDatabaseHelper;
    TextView tv_date;
    String date = "Wed Mar 27 08:22:02 IST 2015";
    Time remindertime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mDatabaseHelper = new DatabaseHelper(this);

        btn_add = findViewById(R.id.btn_add_task);
        btn_setnotification = findViewById(R.id.btn_set_notification);
        btn_date = findViewById(R.id.btn_datepicker_task);
        et_title = findViewById(R.id.et_title_task);
        et_description =  findViewById(R.id.et_description_task);
        et_amount = findViewById(R.id.et_amount_task);
        spn_category = findViewById(R.id.spn_category_task);
        tv_date = findViewById(R.id.tv_date_task);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_category.setAdapter(adapter);
        spn_category.setOnItemSelectedListener(this);

        tpd = TimePickerDialog.newInstance(
                AddTaskActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND)
        );

        btn_setnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpd.show(TimePickerDialog);
                NotificationModel notificationModel = new NotificationModel(1,

                        et_type.getText().toString(),
                        remindertime.getCurrentTimezone().toString());

                Toast.makeText(AddTaskActivity.this, notificationModel.getType(), Toast.LENGTH_SHORT);
                boolean insertNotification = mDatabaseHelper.insertNotification(notificationModel);
                if (insertNotification) {
                    Toast.makeText(AddTaskActivity.this, "Notification created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ViewTask.class));

                }
            }   
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskModel taskModel = new TaskModel(1,
                        et_title.getText().toString(),
                        date,
                        et_description.getText().toString(),
                        Float.valueOf(et_amount.getText().toString()),
                        spn_category.getSelectedItem().toString());
                Toast.makeText(AddTaskActivity.this, taskModel.getTitle(), Toast.LENGTH_SHORT);
                boolean insertData = mDatabaseHelper.insertTask(taskModel);
                if (insertData) {
                    Toast.makeText(AddTaskActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ViewTask.class));

                }
                else{
                    Toast.makeText(AddTaskActivity.this, "Task not added!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ViewTask.class));

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
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,second);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
