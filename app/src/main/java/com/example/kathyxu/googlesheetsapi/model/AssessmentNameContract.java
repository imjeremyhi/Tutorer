package com.example.kathyxu.googlesheetsapi.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kathy on 3/10/2016.
 */
public class AssessmentNameContract {
    public static final String TABLE_NAME = "assessmentName";
    private final SQLiteOpenHelper dbHelper;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    AssessmentEntry._ID + " INTEGER PRIMARY KEY," +
                    AssessmentEntry.COLUMN_NAME_ID + INT_TYPE + COMMA_SEP +
                    AssessmentEntry.COLUMN_NAME_ASSESSMENTNAME + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public abstract class AssessmentEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ASSESSMENTNAME = "assessmentName";
    }

    public AssessmentNameContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insert(Assessment assessment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AssessmentEntry.COLUMN_NAME_ID, assessment.getId());
        values.put(AssessmentEntry.COLUMN_NAME_ASSESSMENTNAME, assessment.getName());


        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public List<Assessment> getAssessments(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                AssessmentEntry._ID,
                AssessmentEntry.COLUMN_NAME_ID,
                AssessmentEntry.COLUMN_NAME_ASSESSMENTNAME

        };

        String sortOrder = AssessmentEntry.COLUMN_NAME_ID;

        Cursor cur = db.query(
                TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<Assessment> assessments = new ArrayList<>();

        while (cur.moveToNext()){
            Assessment stu = new Assessment();
            stu.setId(cur.getInt(cur.getColumnIndexOrThrow(AssessmentEntry.COLUMN_NAME_ID)));
            stu.setName(cur.getString(cur.getColumnIndexOrThrow(AssessmentEntry.COLUMN_NAME_ASSESSMENTNAME)));

            assessments.add(stu);
        }

        cur.close();
        db.close();
        return assessments;
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = AssessmentEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        db.delete(TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    //read values of one pokemon with id
    public Assessment getAssessment(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = AssessmentEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        //Columns to query
        String[] columns = {
                AssessmentEntry._ID,
                AssessmentEntry.COLUMN_NAME_ID,
                AssessmentEntry.COLUMN_NAME_ASSESSMENTNAME

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
        Assessment stu = null;

        if(cur.moveToNext()){
            stu = new Assessment();
            stu.setId(cur.getInt(cur.getColumnIndexOrThrow(AssessmentEntry.COLUMN_NAME_ID)));
            stu.setName(cur.getString(cur.getColumnIndexOrThrow(AssessmentEntry.COLUMN_NAME_ASSESSMENTNAME)));

        }
        cur.close();
        db.close();
        return stu;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void updateAssessmentName(Assessment ass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AssessmentEntry.COLUMN_NAME_ASSESSMENTNAME, ass.getName());

        String selection = AssessmentEntry.COLUMN_NAME_ID + " = ?";
        System.out.println(ass.getId());
        //All the different IDs its equal to
        String[] selectionArgs = {String.valueOf(ass.getId())};

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }
}
