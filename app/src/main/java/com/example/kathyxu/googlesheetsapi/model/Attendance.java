package com.example.kathyxu.googlesheetsapi.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jeremy on 14/10/2016.
 */

public class Attendance {
    private String name;
    private String id;
    private String date;

    public Attendance() {

    }

    public Attendance(String name, String id) {
        this.name = name;
        this.id = id;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String createdDate = dateFormat.format(new Date());
        this.date = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
