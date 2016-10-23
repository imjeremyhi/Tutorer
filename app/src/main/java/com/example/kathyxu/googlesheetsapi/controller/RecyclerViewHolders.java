package com.example.kathyxu.googlesheetsapi.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.Student;

/**
 * Created by kathy on 3/10/2016.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView studentName;

    public Student student;


    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        studentName = (TextView)itemView.findViewById(R.id.student_name);
        System.out.println("Holder is made");
    }

    @Override
    public void onClick(View v) {
        Context context = itemView.getContext();
        //TODO write this when creating new activity:
        Intent showStudentIntent = new Intent(context, DetailedActivity.class );
        String numberKey = String.valueOf(student.getId());
        showStudentIntent.putExtra("number", numberKey);
        context.startActivity(showStudentIntent);
    }

}
