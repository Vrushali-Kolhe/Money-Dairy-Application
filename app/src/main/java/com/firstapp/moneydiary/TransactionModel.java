package com.firstapp.moneydiary;



import java.util.Locale;

public class TransactionModel {
    private int id;
    private String title;
    //private Date date;
    private String description;
    private float amount;
    private String category;

    //constructors


    public TransactionModel(int id, String title, String description, float amount, String category) {
        this.id = id;
        this.title = title;
        //this.date = date;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public TransactionModel() {
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                '}';
    }

    //getters and setters

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

//    public LocaleData getDate() {
//        return date;
//    }
//
//    public void setDate(LocaleData date) {
//        this.date = date;
//    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
