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
public class StudentContract {
    public static final String TABLE_NAME = "student";
    private final SQLiteOpenHelper dbHelper;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    StudentEntry._ID + " INTEGER PRIMARY KEY," +
                    StudentEntry.COLUMN_NAME_ID + INT_TYPE + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_FNAME + TEXT_TYPE + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_LNAME + TEXT_TYPE + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_ZMAIL + TEXT_TYPE + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_TUT + TEXT_TYPE  + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_COMMENTS + TEXT_TYPE  + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_ASSESSMENTONE + INT_TYPE  + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_ASSESSMENTTWO + INT_TYPE  + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_ASSESSMENTTHREE + INT_TYPE  + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_ASSESSMENTFOUR + INT_TYPE  + COMMA_SEP +
                    StudentEntry.COLUMN_NAME_PHOTO + BLOB_TYPE  +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public abstract class StudentEntry implements BaseColumns {
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_FNAME = "fname";
        public static final String COLUMN_NAME_LNAME = "lname";
        public static final String COLUMN_NAME_ZMAIL = "zmail";
        public static final String COLUMN_NAME_TUT = "tut";
        public static final String COLUMN_NAME_COMMENTS = "comments";
        public static final String COLUMN_NAME_ASSESSMENTONE = "assessmentOne";
        public static final String COLUMN_NAME_ASSESSMENTTWO = "assessmentTwo";
        public static final String COLUMN_NAME_ASSESSMENTTHREE = "assessmentThree";
        public static final String COLUMN_NAME_ASSESSMENTFOUR = "assessmentFour";
        public static final String COLUMN_NAME_PHOTO = "photo";

    }

    public StudentContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insert(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentEntry.COLUMN_NAME_ID, student.getId());
        values.put(StudentEntry.COLUMN_NAME_FNAME, student.getFname());
        values.put(StudentEntry.COLUMN_NAME_LNAME, student.getLname());
        values.put(StudentEntry.COLUMN_NAME_ZMAIL, student.getZmail());
        values.put(StudentEntry.COLUMN_NAME_TUT, student.getTut());
        values.put(StudentEntry.COLUMN_NAME_COMMENTS, student.getComments());
        values.put(StudentEntry.COLUMN_NAME_ASSESSMENTONE, student.getAssessmentOne());
        values.put(StudentEntry.COLUMN_NAME_ASSESSMENTTWO, student.getAssessmentTwo());
        values.put(StudentEntry.COLUMN_NAME_ASSESSMENTTHREE, student.getAssessmentThree());
        values.put(StudentEntry.COLUMN_NAME_ASSESSMENTFOUR, student.getAssessmentFour());
        values.put(StudentEntry.COLUMN_NAME_PHOTO, student.getPhoto());


        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public List<Student> getStudents(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                StudentEntry._ID,
                StudentEntry.COLUMN_NAME_ID,
                StudentEntry.COLUMN_NAME_FNAME,
                StudentEntry.COLUMN_NAME_LNAME,
                StudentEntry.COLUMN_NAME_ZMAIL,
                StudentEntry.COLUMN_NAME_TUT,
                StudentEntry.COLUMN_NAME_COMMENTS,
                StudentEntry.COLUMN_NAME_ASSESSMENTONE,
                StudentEntry.COLUMN_NAME_ASSESSMENTTWO,
                StudentEntry.COLUMN_NAME_ASSESSMENTTHREE,
                StudentEntry.COLUMN_NAME_ASSESSMENTFOUR,
                StudentEntry.COLUMN_NAME_PHOTO


        };

        String sortOrder = StudentEntry.COLUMN_NAME_ID;

        Cursor cur = db.query(
                TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<Student> students = new ArrayList<>();

        while (cur.moveToNext()){
            Student stu = new Student();
            stu.setId(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ID)));
            stu.setFname(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_FNAME)));
            stu.setLname(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_LNAME)));
            stu.setZmail(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ZMAIL)));
            stu.setTut(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_TUT)));
            stu.setComments(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_COMMENTS)));
            stu.setAssessmentOne(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ASSESSMENTONE)));
            stu.setAssessmentTwo(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ASSESSMENTTWO)));
            stu.setAssessmentThree(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ASSESSMENTTHREE)));
            stu.setAssessmentFour(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ASSESSMENTFOUR)));
            stu.setPhoto(cur.getBlob(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_PHOTO)));


            students.add(stu);
        }

        cur.close();
        db.close();
        return students;
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = StudentEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        db.delete(TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    //read values of one pokemon with id
    public Student getStudent(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = StudentEntry.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        //Columns to query
        String[] columns = {
                StudentEntry._ID,
                StudentEntry.COLUMN_NAME_ID,
                StudentEntry.COLUMN_NAME_FNAME,
                StudentEntry.COLUMN_NAME_LNAME,
                StudentEntry.COLUMN_NAME_ZMAIL,
                StudentEntry.COLUMN_NAME_TUT,
                StudentEntry.COLUMN_NAME_COMMENTS,
                StudentEntry.COLUMN_NAME_ASSESSMENTONE,
                StudentEntry.COLUMN_NAME_ASSESSMENTTWO,
                StudentEntry.COLUMN_NAME_ASSESSMENTTHREE,
                StudentEntry.COLUMN_NAME_ASSESSMENTFOUR,
                StudentEntry.COLUMN_NAME_PHOTO



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
        Student stu = null;

        if(cur.moveToNext()){
            stu = new Student();
            stu.setId(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ID)));
            stu.setFname(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_FNAME)));
            stu.setLname(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_LNAME)));
            stu.setZmail(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ZMAIL)));
            //byte[] img = cur.getBlob(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_IMAGE));
            stu.setTut(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_TUT)));
            stu.setComments(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_COMMENTS)));
            stu.setAssessmentOne(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ASSESSMENTONE)));
            stu.setAssessmentTwo(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ASSESSMENTTWO)));
            stu.setAssessmentThree(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ASSESSMENTTHREE)));
            stu.setAssessmentFour(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_ASSESSMENTFOUR)));
            stu.setPhoto(cur.getBlob(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME_PHOTO)));

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

    public void updateStudentAssessements(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(StudentEntry.COLUMN_NAME_ASSESSMENTONE, student.getAssessmentOne());
        values.put(StudentEntry.COLUMN_NAME_ASSESSMENTTWO, student.getAssessmentTwo());
        values.put(StudentEntry.COLUMN_NAME_ASSESSMENTTHREE, student.getAssessmentThree());
        values.put(StudentEntry.COLUMN_NAME_ASSESSMENTFOUR, student.getAssessmentFour());

        String selection = StudentEntry.COLUMN_NAME_ID + " = ?";
        System.out.println(student.getId());
        //All the different IDs its equal to
        String[] selectionArgs = {String.valueOf(student.getId())};

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }
    public void updateComments(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(StudentEntry.COLUMN_NAME_COMMENTS, student.getComments());

        String selection = StudentEntry.COLUMN_NAME_ID + " = ?";
        System.out.println(student.getId());
        //All the different IDs its equal to
        String[] selectionArgs = {String.valueOf(student.getId())};

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public void updatePhoto(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(StudentEntry.COLUMN_NAME_PHOTO, student.getPhoto());

        String selection = StudentEntry.COLUMN_NAME_ID + " = ?";
        System.out.println(student.getId());
        //All the different IDs its equal to
        String[] selectionArgs = {String.valueOf(student.getId())};

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }
}