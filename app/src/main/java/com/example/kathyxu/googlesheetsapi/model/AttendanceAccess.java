package com.example.kathyxu.googlesheetsapi.model;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Jeremy on 14/10/2016.
 */

public class AttendanceAccess {
    private final AttendanceContract attendanceContract;

    public AttendanceAccess(SQLiteOpenHelper sqLiteOpenHelper) {
        this.attendanceContract = new AttendanceContract(sqLiteOpenHelper);
    }

    public List<Attendance> getAll() {
        return attendanceContract.getAttendances();
    }

    //Example standard contract methods
    public void insertAttendance(Attendance stu){
        attendanceContract.insert(stu);
    }

    public void deleteAttendance(String id){
        attendanceContract.delete(id);
    }

    //Example of extra methods
    public void insertAttendances(List<Attendance> attendances){
        for(Attendance stu : attendances){
            attendanceContract.insert(stu);
        }
    }

    public void deleteAttendances(String... ids){
        for(String id : ids){
            attendanceContract.delete(id);
        }
    }
    /*
    public Attendance getAttendance(String number){
        return attendanceContract.getAttendance(Integer.parseInt(number));
    }


    public boolean hasAttendance(int id){
        return attendanceContract.getAttendance(id) != null;
    }
    */
}
