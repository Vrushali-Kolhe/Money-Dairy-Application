package com.firstapp.moneydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.util.Log;
import android.view.SurfaceControl;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="MoneyDiary.db";
    private Context context;

    public static final String TABLE_USER="User";
    public static final String USER_ID="User_id";
    public static final String USER_NAME="UserName";
    public static final String USER_PHONENUMBER="PhoneNumber";
    public static final String USER_EMAIL="Email";
    public static final String USER_PASSWORD="Password";

    public static final String TABLE_TRANSACTION="Transactions";
    public static final String TRANSACTION_ID="Transaction_id";
    public static final String TRANSACTION_TITLE="Title";
    public static final String TRANSACTION_DATE="Date";
    public static final String TRANSACTION_AMOUNT="Amount";
    public static final String TRANSACTION_DESCRIPTION="Description";
    public static final String TRANSACTION_CATEGORY="Category";

    public static final String TABLE_TASK="Task";
    public static final String TASK_ID="Task_id";
    public static final String TASK_TITLE="Title";
    public static final String TASK_DATE="Date";
    public static final String TASK_AMOUNT="Amount";
    public static final String TASK_DESCRIPTION="Description";
    public static final String TASK_CATEGORY="Category";

    public static final String TABLE_NOTIFICATION="Notification";
    public static final String NOTIFICATION_ID="Notification_id";
    public static final String NOTIFICATION_TYPE="Notification_Type";
    public static final String REMINDER_DATE="Reminder_Date";
    public static final String REMINDER_TIME="Reminder_Time";

    public static final String TABLE_REPEAT="Repeat";
    public static final String REPEAT_ID="Repeat_id";
    public static final String REPEAT_TYPE="Repeat_Type";
    public static final String REPEAT_DURATION="Duration";

    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT, "
            + USER_PHONENUMBER + " INTEGER, "
            + USER_EMAIL + " TEXT, "
            + USER_PASSWORD + " TEXT )";

    private static final String SQL_CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION + "("
            + TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRANSACTION_TITLE + " TEXT, "
            + TRANSACTION_DATE + " INTEGER, "
            + TRANSACTION_AMOUNT + " REAL, "
            + TRANSACTION_DESCRIPTION + " TEXT, "
            + TRANSACTION_CATEGORY + " TEXT )";

    private static final String SQL_CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK + "("
            + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_TITLE + " TEXT, "
            + TASK_DATE + " INTEGER, "
            + TASK_AMOUNT + " REAL, "
            + TASK_DESCRIPTION + " TEXT, "
            + TASK_CATEGORY + " TEXT )";

    private static final String SQL_CREATE_TABLE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION + " ("
            + NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTIFICATION_TYPE + "TEXT, "
            + REMINDER_DATE + "DATETIME DEFAULT CURRENT_DATE, "
            + REMINDER_TIME + "DATETIME DEFAULT CURRENT_TIME) ";

    private static final String SQL_CREATE_TABLE_REPEAT = "CREATE TABLE " + TABLE_REPEAT + " ("
            + REPEAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REPEAT_TYPE + "TEXT, "
            + REPEAT_DURATION + "INTEGER) ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_TRANSACTION);
        db.execSQL(SQL_CREATE_TABLE_TASK);
        db.execSQL(SQL_CREATE_TABLE_NOTIFICATION);
        db.execSQL(SQL_CREATE_TABLE_REPEAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_REPEAT);
        onCreate(db);
    }



    public long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put("username",user);
        values1.put("password",password);
        long res = db.insert("User",null,values1);
        db.close();
        return res;
    }

    public boolean checkUser(String username, String password){
        String[] columns = { USER_ID };
        SQLiteDatabase db = getReadableDatabase();
        String selection = USER_NAME + "=?" + " and " + USER_PASSWORD + "=?";
        String[] selectionArgs = { username, password};
        Cursor cursor = db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;

    }

    ArrayList<TransactionModel> getAllData() {
        ArrayList<TransactionModel> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_TRANSACTION + " ORDER BY " + TRANSACTION_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int transactionId = cursor.getInt(0);
                String title = cursor.getString(1);
                String category = cursor.getString(2);
                String description = cursor.getString(4);
                float amount = cursor.getFloat(3);
                TransactionModel newTransaction = new TransactionModel(transactionId, title, description, amount, category);
                list.add(newTransaction);

            }while(cursor.moveToNext());

        }
        else{

        }
        cursor.close();
        //db.close();


        return list;
    }

    ArrayList<TaskModel> getAllTasks() {
        ArrayList<TaskModel> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_TASK + " ORDER BY " + TASK_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int taskId = cursor.getInt(0);
                String title = cursor.getString(1);
                String category = cursor.getString(2);
                String description = cursor.getString(4);
                float amount = cursor.getFloat(3);
                TaskModel newTask = new TaskModel(taskId, title, description, amount, category);
                list.add(newTask);

            }while(cursor.moveToNext());

        }
        else{

        }
        cursor.close();
        //db.close();


        return list;
    }

    public boolean insertData(TransactionModel transactionModel) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Toast.makeText(this, transactionModel.getDescription(), Toast.LENGTH_SHORT);
        ContentValues values = new ContentValues();
        values.put(TRANSACTION_TITLE, transactionModel.getTitle());
        values.put(TRANSACTION_DESCRIPTION, transactionModel.getDescription());
        values.put(TRANSACTION_AMOUNT, transactionModel.getAmount() );
        values.put(TRANSACTION_CATEGORY, transactionModel.getCategory());
        values.put(TRANSACTION_DATE, 400);

       Log.d(DATABASE_NAME, "adding values");
        // Inserting Row
       long result =  db.insert(TABLE_TRANSACTION, null, values);
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
         // Closing database connection
    }

    public boolean insertTask(TaskModel taskModel) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Toast.makeText(this, transactionModel.getDescription(), Toast.LENGTH_SHORT);
        ContentValues values = new ContentValues();
        values.put(TASK_TITLE, taskModel.getTitle());
        values.put(TASK_DESCRIPTION, taskModel.getDescription());
        values.put(TASK_AMOUNT, taskModel.getAmount() );
        values.put(TASK_CATEGORY, taskModel.getCategory());
        values.put(TASK_DATE, 400);

        Log.d(DATABASE_NAME, "adding values");
        // Inserting Row
        long result =  db.insert(TABLE_TASK, null, values);
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
        // Closing database connection
    }

    void updateTransaction(String row_id, String title, String description, Float amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRANSACTION_TITLE, title);
        cv.put(TRANSACTION_DESCRIPTION, description);
        cv.put(TRANSACTION_AMOUNT, amount);

        long result = db.update(TABLE_TRANSACTION, cv, "Transaction_id=?", new String[]{row_id});
        //long result = db.update(TABLE_TRANSACTION, cv, "=" + row_id, null);
        db.close();
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void updateTask(String row_id, String title, String description, Float amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_TITLE, title);
        cv.put(TASK_DESCRIPTION, description);
        cv.put(TASK_AMOUNT, amount);

        long result = db.update(TABLE_TASK, cv, "Task_id=?", new String[]{row_id});
        //long result = db.update(TABLE_TRANSACTION, cv, "=" + row_id, null);
        db.close();
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TRANSACTION, "Transaction_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRowTask(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TASK, "Task_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

}


