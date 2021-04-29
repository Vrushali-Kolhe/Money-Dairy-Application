package com.firstapp.moneydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="MoneyDiary.db";
    private Context context;
    String date = "Wed Mar 27 08:22:02 IST 2015";

    public static final String TABLE_NAME="User";
    public static final String COL_1="User_id";
    public static final String COL_2="UserName";
    public static final String COL_3="Password";
    public static final String COL_4="Email";
    public static final String COL_5="Phone_Number";

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
    //public static final String REMINDER_DATE="Reminder_Date";
    public static final String REMINDER_TIME="Reminder_Time";

    public static final String TABLE_REPEAT="Repeat";
    public static final String REPEAT_ID="Repeat_id";
    public static final String REPEAT_TYPE="Repeat_Type";
    public static final String REPEAT_DURATION="Duration";

    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME + "("
            + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_2 + " TEXT, "
            + COL_3 + " TEXT, "
            + COL_4 + " TEXT, "
            + COL_5 + " TEXT )";

    private static final String SQL_CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION + "("
            + TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRANSACTION_TITLE + " TEXT, "
            + TRANSACTION_DATE + " TEXT, "
            + TRANSACTION_AMOUNT + " REAL, "
            + TRANSACTION_DESCRIPTION + " TEXT, "
            + TRANSACTION_CATEGORY + " TEXT )";

    private static final String SQL_CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK + "("
            + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_TITLE + " TEXT, "
            + TASK_DATE + " TEXT, "
            + TASK_AMOUNT + " REAL, "
            + TASK_DESCRIPTION + " TEXT, "
            + TASK_CATEGORY + " TEXT )";

    private static final String SQL_CREATE_TABLE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION + "("
            + NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTIFICATION_TYPE + " TEXT, "
            //+ REMINDER_DATE + " DATETIME DEFAULT CURRENT_DATE, "
            + REMINDER_TIME + " DATETIME DEFAULT CURRENT_TIME )";

    private static final String SQL_CREATE_TABLE_REPEAT = "CREATE TABLE " + TABLE_REPEAT + "("
            + REPEAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REPEAT_TYPE + " TEXT, "
            + REPEAT_DURATION + " INTEGER )";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TRANSACTION);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TASK);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_NOTIFICATION);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_REPEAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_REPEAT);
        onCreate(sqLiteDatabase);
    }

    public long addUser(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,userModel.getUsername());
        contentValues.put(COL_3,userModel.getPassword());
        contentValues.put(COL_4,userModel.getEmail());
        contentValues.put(COL_5,userModel.getPhone_Number());
        long res = db.insert(TABLE_NAME,null,contentValues);
        //db.close();
        return res;
    }

    /*public boolean checkUser(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { COL_1 };
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password};
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        //db.close();

        if(count>0)
            return true;
        else
            return false;

    }*/

    public UserModel checkUser(String username, String password){
        UserModel userModel = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where username = ? and password = ?", new String[] {username, password});
            if(cursor.moveToFirst()){
                userModel = new UserModel();
                userModel.setUser_id(cursor.getInt(0));
                userModel.setUsername(cursor.getString(1));
                userModel.setPassword(cursor.getString(2));
                userModel.setEmail(cursor.getString(3));
                userModel.setPhone_Number(cursor.getString(4));
            }
        }
        catch (Exception e){
                userModel = null;
        }
        return userModel;
    }

    public UserModel checkUserName(String username){
        UserModel userModel = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where username = ?", new String[] {username});
            if(cursor.moveToFirst()){
                userModel = new UserModel();
                userModel.setUser_id(cursor.getInt(0));
                userModel.setUsername(cursor.getString(1));
                userModel.setPassword(cursor.getString(2));
                userModel.setEmail(cursor.getString(3));
                userModel.setPhone_Number(cursor.getString(4));
            }
        }
        catch (Exception e){
            userModel = null;
        }
        return userModel;
    }

    public UserModel find(int id){
        UserModel userModel = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where user_id = ?", new String[] {String.valueOf(id)});
            if(cursor.moveToFirst()){
                userModel = new UserModel();
                userModel.setUser_id(cursor.getInt(0));
                userModel.setUsername(cursor.getString(1));
                userModel.setPassword(cursor.getString(2));
                userModel.setEmail(cursor.getString(3));
                userModel.setPhone_Number(cursor.getString(4));
            }
        }
        catch (Exception e){
            userModel = null;
        }
        return userModel;
    }

    /*public boolean updateUser(String username, String email, String phone_number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,phone_number);
        Cursor cursor = db.rawQuery("Select * from User where UserName =?", new String[]{username});
        if(cursor.getCount()>0){
            long res = db.update(TABLE_NAME, contentValues,"UserName =?",new String[]{username});
            if(res>0){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }*/

    public boolean updateUser(UserModel userModel){
        boolean res = true;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, userModel.getUsername());
            contentValues.put(COL_3, userModel.getPassword());
            contentValues.put(COL_4, userModel.getEmail());
            contentValues.put(COL_5, userModel.getPhone_Number());
            res = db.update(TABLE_NAME, contentValues,COL_1+" = ?",new String[] {String.valueOf(userModel.getUser_id())})>0;
        }
        catch(Exception e){
            res = false;
        }
        //db.close();
        return res;
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
                String date = cursor.getString(2);
                String category = cursor.getString(5);
                String description = cursor.getString(4);
                float amount = cursor.getFloat(3);
                TransactionModel newTransaction = new TransactionModel(transactionId, title, date, description, amount, category);
                list.add(newTransaction);

            }while(cursor.moveToNext());

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

    public boolean updateTransaction(String row_id, String title, String description, Float amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRANSACTION_TITLE, title);
        cv.put(TRANSACTION_DESCRIPTION, description);
        cv.put(TRANSACTION_AMOUNT, amount);

        long result = db.update(TABLE_TRANSACTION, cv, "Transaction_id=?", new String[]{row_id});
        //long result = db.update(TABLE_TRANSACTION, cv, "=" + row_id, null);
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    public boolean deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TRANSACTION, "Transaction_id=?", new String[]{row_id});
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
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
                String category = cursor.getString(5);
                String description = cursor.getString(4);
                float amount = cursor.getFloat(3);
                TaskModel newTask = new TaskModel(taskId, title, date, description, amount, category);
                list.add(newTask);

            }while(cursor.moveToNext());

        }
        else{

        }
        cursor.close();
        //db.close();


        return list;
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

    public boolean updateTask(String row_id, String title, String description, Float amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_TITLE, title);
        cv.put(TASK_DESCRIPTION, description);
        cv.put(TASK_AMOUNT, amount);

        long result = db.update(TABLE_TASK, cv, "Task_id=?", new String[]{row_id});
        //long result = db.update(TABLE_TRANSACTION, cv, "=" + row_id, null);
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    public boolean deleteOneRowTask(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TASK, "Task_id=?", new String[]{row_id});
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean insertNotification(NotificationModel notifacationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Toast.makeText(this, transactionModel.getDescription(), Toast.LENGTH_SHORT);
        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_TYPE, notifacationModel.getType());
        values.put(REMINDER_TIME, notifacationModel.getReminderTime());

        Log.d(DATABASE_NAME, "adding values");
        // Inserting Row
        long result =  db.insert(TABLE_NOTIFICATION, null, values);
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
        // Closing database connection
    } 

}
