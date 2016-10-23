package com.example.kathyxu.googlesheetsapi.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.Assessment;
import com.example.kathyxu.googlesheetsapi.model.AssessmentNameAccess;
import com.example.kathyxu.googlesheetsapi.model.AssessmentNameDBHelper;

/**
 * Created by kathy on 21/10/2016.
 */

public class Settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawAssessmentNames();

    }

    private void drawAssessmentNames() {
        Button save = (Button) findViewById(R.id.saveAssessments);
        final EditText firstAssess = (EditText) findViewById(R.id.firstAssessLabel);
        final EditText secondAssess = (EditText) findViewById(R.id.secondAssessLabel);
        final EditText thirdAssess = (EditText) findViewById(R.id.thirdAssessLabel);
        final EditText fourthAssess = (EditText) findViewById(R.id.fourthAssessLabel);

        AssessmentNameDBHelper myDbHelper = new AssessmentNameDBHelper(this);
        final AssessmentNameAccess myDbAccess = new AssessmentNameAccess(myDbHelper);


        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //save the names into the database
                String firstName = String.valueOf(firstAssess.getText());
                String secondName = String.valueOf(secondAssess.getText());
                String thirdName = String.valueOf(thirdAssess.getText());
                String fourthName = String.valueOf(fourthAssess.getText());
                //port names over into the display in detailed view
                Assessment assessmentOne = new Assessment();
                assessmentOne.setId(1);
                assessmentOne.setName(firstName);
                Assessment assessmentTwo = new Assessment();
                assessmentTwo.setId(2);
                assessmentTwo.setName(secondName);
                Assessment assessmentThree = new Assessment();
                assessmentThree.setId(3);
                assessmentThree.setName(thirdName);
                Assessment assessmentFour = new Assessment();
                assessmentFour.setId(4);
                assessmentFour.setName(fourthName);

                //put four assessments into database
                myDbAccess.nameUpdate(assessmentOne);
                myDbAccess.nameUpdate(assessmentTwo);
                myDbAccess.nameUpdate(assessmentThree);
                myDbAccess.nameUpdate(assessmentFour);

                Toast.makeText(v.getContext(), "Saved!", Toast.LENGTH_LONG).show();
            }
        });

    }
}