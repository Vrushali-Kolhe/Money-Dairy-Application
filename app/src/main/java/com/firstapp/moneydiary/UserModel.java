package com.firstapp.moneydiary;

public class UserModel {
    private int User_id;
    private String Username;
    private String Password;
    private String Email;
    private String Phone_Number;

    public UserModel() {
    }

    public UserModel(int user_id, String username, String password, String email, String phone_Number) {
        User_id = user_id;
        Username = username;
        Password = password;
        Email = email;
        Phone_Number = phone_Number;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "User_id=" + User_id +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone_Number='" + Phone_Number + '\'' +
                '}';
    }
}
