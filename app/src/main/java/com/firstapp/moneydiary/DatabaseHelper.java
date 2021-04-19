package com.firstapp.moneydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="MoneyDiary.db";
    public static final String TABLE_NAME="User";
    public static final String COL_1="User_id";
    public static final String COL_2="UserName";
    public static final String COL_3="Password";
    public static final String COL_4="Email";
    public static final String COL_5="Phone_Number";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE User (User_id INTEGER PRIMARY KEY AUTOINCREMENT, UserName TEXT, Password TEXT, Email TEXT, Phone_Number TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
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

    public boolean checkUser(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
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

    }

    public boolean updateUser(String username, String email, String phone_number){
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
    }
}
