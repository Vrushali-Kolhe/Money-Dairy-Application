package com.firstapp.moneydiary;

import android.text.format.Time;

import java.io.Serializable;

public class NotificationModel implements Serializable {
    private int id;
    private String type;
    private Time remindertime;

    public NotificationModel() {
    }

    public NotificationModel(int id, String type, Time remindertime) {
        this.id = id;
        this.type = type;
        this.remindertime = remindertime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String title) {
        this.type = type;
    }

    public Time getReminderTime() {
        return remindertime;
    }

    public void ReminderTime(String date) {
        this.remindertime = remindertime;
    }


    @Override
    public String toString() {
        return "NotificationModel{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", remindertime='" + remindertime + '\'' +
                '}';
    }

}
