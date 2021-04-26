package com.firstapp.moneydiary;

import java.io.Serializable;

public class TaskModel implements Serializable {
    private int id;
    private String title;
    private String date;
    private String description;
    private float amount;
    private String category;


    public TaskModel(int id, String title, String date, String description, float amount, String category) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
