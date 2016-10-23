package com.example.kathyxu.googlesheetsapi.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 14/10/2016.
 */

public class AttendanceContract {
    public static final String TABLE_NAME = "attendance";
    private final SQLiteOpenHelper dbHelper;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    AttendanceContract.AttendanceEntry._ID + " INTEGER PRIMARY KEY," +
                    AttendanceContract.AttendanceEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    AttendanceContract.AttendanceEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public abstract class AttendanceEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATE = "date";
    }

    public AttendanceContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insert(Attendance Attendance) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AttendanceContract.AttendanceEntry.COLUMN_NAME_ID, Attendance.getId());
        values.put(AttendanceContract.AttendanceEntry.COLUMN_NAME_NAME, Attendance.getName());
        values.put(AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE, Attendance.getDate());

        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public List<Attendance> getAttendances(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                AttendanceContract.AttendanceEntry._ID,
                AttendanceContract.AttendanceEntry.COLUMN_NAME_ID,
                AttendanceContract.AttendanceEntry.COLUMN_NAME_NAME,
                AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE

        };

        String sortOrder = AttendanceContract.AttendanceEntry._ID + " DESC";

        Cursor cur = db.query(
                TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<Attendance> attendances = new ArrayList<>();

        while (cur.moveToNext()){
            Attendance stu = new Attendance();
            stu.setId(cur.getString(cur.getColumnIndexOrThrow(AttendanceContract.AttendanceEntry.COLUMN_NAME_ID)));
            stu.setName(cur.getString(cur.getColumnIndexOrThrow(AttendanceContract.AttendanceEntry.COLUMN_NAME_NAME)));
            stu.setDate(cur.getString(cur.getColumnIndexOrThrow(AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE)));
            attendances.add(stu);
        }

        cur.close();
        db.close();
        return attendances;
    }

    public void delete(String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = AttendanceContract.AttendanceEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        db.delete(TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    //read values of one pokemon with id
    public Attendance getAttendance(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = AttendanceContract.AttendanceEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        //Columns to query
        String[] columns = {
                AttendanceContract.AttendanceEntry._ID,
                AttendanceContract.AttendanceEntry.COLUMN_NAME_ID,
                AttendanceContract.AttendanceEntry.COLUMN_NAME_NAME,
                AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE,
        };

        Cursor cur = db.query(
                TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        Attendance stu = null;

        if(cur.moveToNext()){
            stu = new Attendance();
            stu.setId(cur.getString(cur.getColumnIndexOrThrow(AttendanceContract.AttendanceEntry.COLUMN_NAME_ID)));
            stu.setName(cur.getString(cur.getColumnIndexOrThrow(AttendanceContract.AttendanceEntry.COLUMN_NAME_NAME)));
            stu.setDate(cur.getString(cur.getColumnIndexOrThrow(AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE)));
        }
        cur.close();
        db.close();
        return stu;
    }
}
