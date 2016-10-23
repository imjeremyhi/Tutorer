package com.example.kathyxu.googlesheetsapi.model;

import com.example.kathyxu.googlesheetsapi.model.Student;

import java.util.List;

/**
 * Created by kathy on 3/10/2016.
 */
public interface DbAccess {
    List<Student> getAll();
}
