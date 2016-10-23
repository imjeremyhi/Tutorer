package com.example.kathyxu.googlesheetsapi.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.Attendance;
import com.example.kathyxu.googlesheetsapi.model.AttendanceAccess;
import com.example.kathyxu.googlesheetsapi.model.StudentDBHelper;

import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private LinearLayoutManager myLinearLayoutManager;
    private AttendanceRecyclerAdapter myAdapter;
    private StudentDBHelper dbHelper;
    private AttendanceAccess attendanceAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new StudentDBHelper(this);
        attendanceAccess = new AttendanceAccess(dbHelper);

        myRecyclerView = (RecyclerView)findViewById(R.id.attendanceRecycler);
        myLinearLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLinearLayoutManager);
        List<Attendance> attendance = attendanceAccess.getAll();
        if (attendance != null) {
            myAdapter = new AttendanceRecyclerAdapter(attendance);
            myRecyclerView.setAdapter(myAdapter);
        }
    }
}
