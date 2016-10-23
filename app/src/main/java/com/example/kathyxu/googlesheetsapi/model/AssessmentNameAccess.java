package com.example.kathyxu.googlesheetsapi.model;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by kathy on 3/10/2016.
 */
public class AssessmentNameAccess implements DbAccessForAssessment {
    private final AssessmentNameContract assessmentNameContract;

    public AssessmentNameAccess(SQLiteOpenHelper sqLiteOpenHelper) {
        this.assessmentNameContract = new AssessmentNameContract(sqLiteOpenHelper);
    }

    //Example of overriding interface
    @Override
    public List<Assessment> getAll() {
        return assessmentNameContract.getAssessments();
    }

    //Example standard contract methods
    public void insertAssessment(Assessment stu){
        assessmentNameContract.insert(stu);
    }

    public void deleteAssessment(int id){
        assessmentNameContract.delete(id);
    }

    //Example of extra methods
    public void insertAssessments(List<Assessment> assessments){
        for(Assessment stu : assessments){
            assessmentNameContract.insert(stu);
        }
    }

    public void deleteAssessments(int... ids){
        for(int id : ids){
            assessmentNameContract.delete(id);
        }
    }
    public Assessment getAssessment(String number){
        return assessmentNameContract.getAssessment(Integer.parseInt(number));
    }

    public boolean hasStu(int id){
        return assessmentNameContract.getAssessment(id) != null;
    }

    public void nameUpdate(Assessment stu){
        assessmentNameContract.updateAssessmentName(stu);
    }
}
