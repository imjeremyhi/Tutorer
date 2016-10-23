package com.example.kathyxu.googlesheetsapi.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.Attendance;

import java.util.List;

/**
 * Created by Jeremy on 14/10/2016.
 */

public class AttendanceRecyclerAdapter extends RecyclerView.Adapter<AttendanceRecyclerAdapter.ViewHolder> {
    private List<Attendance> attendanceList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView name;
        public TextView date;

        public ViewHolder (View v) {
            super(v);
            id = (TextView)v.findViewById(R.id.attendanceId);
            name = (TextView)v.findViewById(R.id.attendanceName);
            date = (TextView)v.findViewById(R.id.attendanceDate);
        }
    }
    public AttendanceRecyclerAdapter(List<Attendance>attendanceList) {
        this.attendanceList = attendanceList;
    }

    @Override
    public AttendanceRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_row, parent, false);
        ViewHolder vh = new ViewHolder(inflatedView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(attendanceList.get(position).getId());
        holder.name.setText(attendanceList.get(position).getName());
        holder.date.setText(attendanceList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }
}
