package com.example.kathyxu.googlesheetsapi.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.Assessment;
import com.example.kathyxu.googlesheetsapi.model.AssessmentNameAccess;
import com.example.kathyxu.googlesheetsapi.model.AssessmentNameDBHelper;
import com.example.kathyxu.googlesheetsapi.model.Student;
import com.example.kathyxu.googlesheetsapi.model.StudentAccess;
import com.example.kathyxu.googlesheetsapi.model.StudentDBHelper;

import java.io.ByteArrayOutputStream;

/**
 * Created by Kathy on 9/10/2016.
 */

public class DetailedActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE=1;

    StudentDBHelper myDbHelper;
    StudentAccess myDbAccess;
    Student detailStudent;

    AssessmentNameDBHelper assDbHelper;
    AssessmentNameAccess assDbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDbHelper = new StudentDBHelper(this);
        myDbAccess = new StudentAccess(myDbHelper);
        detailStudent = myDbAccess.getStudent(getIntent().getStringExtra("number"));
        fillFields();
        activatePhoto();
    }

    private void activatePhoto(){
        Button takePhoto = (Button)findViewById(R.id.takePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    System.out.println("you took a photo!");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult is called");

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //put the photo into the database for this student
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            byte imageByte[] = outputStream.toByteArray();
            detailStudent.setPhoto(imageByte);
            myDbAccess.photoUpdate(detailStudent);

            //populate the imageview at the same time
            ImageView photo = (ImageView) findViewById(R.id.photo);

            byte[] byteArray = detailStudent.getPhoto();
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            photo.setImageBitmap(bmp);

            System.out.println("the the photo is populated!");


        }
    }

    private void fillFields() {


        ImageView image = (ImageView)findViewById(R.id.photo);
        //set to image in database
        if(detailStudent.getPhoto()!=null) {
            byte[] imagephoto = detailStudent.getPhoto();
            Bitmap bmp = BitmapFactory.decodeByteArray(imagephoto, 0, imagephoto.length);
            image.setImageBitmap(bmp);
        }
        else{
        }

        //top details
        TextView name = (TextView)findViewById(R.id.name);
        name.setText(detailStudent.getFname() + " " + detailStudent.getLname());

        TextView id = (TextView)findViewById(R.id.zID);
        id.setText("z" + String.valueOf(detailStudent.getId()));

        TextView zmail = (TextView)findViewById(R.id.zmail);
        zmail.setText(detailStudent.getZmail());

        TextView tutorial = (TextView)findViewById(R.id.tut);
        tutorial.setText("Tutorial: " + detailStudent.getTut());

        ////////////////////////////////////////////////////////////////////////////////
        assDbHelper = new AssessmentNameDBHelper(this);
        assDbAccess = new AssessmentNameAccess(assDbHelper);

        TextView assOneLabel = (TextView)findViewById(R.id.ass1);
        String nameOfOne = String.valueOf(assDbAccess.getAssessment("1").getName());
        assOneLabel.setText(nameOfOne);
        TextView assTwoLabel = (TextView)findViewById(R.id.ass2);
        String nameOfTwo = String.valueOf(assDbAccess.getAssessment("2").getName());
        assTwoLabel.setText(nameOfTwo);
        TextView assThreeLabel = (TextView)findViewById(R.id.ass3);
        String nameOfThree = String.valueOf(assDbAccess.getAssessment("3").getName());
        assThreeLabel.setText(nameOfThree);
        TextView assFourLabel = (TextView)findViewById(R.id.ass4);
        String nameOfFour = String.valueOf(assDbAccess.getAssessment("4").getName());
        assFourLabel.setText(nameOfFour);
        ////////////////////////////////////////////////////////////////////////////////

        final EditText assessmentOneEdit = (EditText)findViewById(R.id.assessmentOneEdit);
        //if not -1
        if(detailStudent.getAssessmentOne()!=-1) {
            assessmentOneEdit.setText(String.valueOf(detailStudent.getAssessmentOne()));
        }
        else {
            //if -1
            //blank
        }
        final EditText assessmentTwoEdit = (EditText)findViewById(R.id.assessmentTwoEdit);
        if(detailStudent.getAssessmentTwo()!=-1) {
            assessmentTwoEdit.setText(String.valueOf(detailStudent.getAssessmentTwo()));
        }
        else {
            //if -1
            //blank
        }
        final EditText assessmentThreeEdit = (EditText)findViewById(R.id.assessmentThreeEdit);
        if(detailStudent.getAssessmentThree()!=-1) {
            assessmentThreeEdit.setText(String.valueOf(detailStudent.getAssessmentThree()));
        }
        else {
            //if -1
            //blank
        }
        final EditText assessmentFourEdit = (EditText)findViewById(R.id.assessmentFourEdit);
        if(detailStudent.getAssessmentFour()!=-1) {
            assessmentFourEdit.setText(String.valueOf(detailStudent.getAssessmentFour()));
        }
        else {
            //if -1
            //blank
        }

        //set textview to the value in the database IF there is a value
        final TextView assessmentOneDisplay = (TextView)findViewById(R.id.assessmentOneDisplay);
        if(detailStudent.getAssessmentOne()!=-1){
            assessmentOneDisplay.setText(String.valueOf(detailStudent.getAssessmentOne()));
        }
        final TextView assessmentTwoDisplay = (TextView)findViewById(R.id.assessmentTwoDisplay);
        if(detailStudent.getAssessmentTwo()!=-1){
            assessmentTwoDisplay.setText(String.valueOf(detailStudent.getAssessmentTwo()));
        }
        final TextView assessmentThreeDisplay = (TextView)findViewById(R.id.assessmentThreeDisplay);
        if(detailStudent.getAssessmentThree()!=-1){
            assessmentThreeDisplay.setText(String.valueOf(detailStudent.getAssessmentThree()));
        }
        final TextView assessmentFourDisplay = (TextView)findViewById(R.id.assessmentFourDisplay);
        if(detailStudent.getAssessmentFour()!=-1){
            assessmentFourDisplay.setText(String.valueOf(detailStudent.getAssessmentFour()));


        }
        final Button edit = (Button) findViewById(R.id.editButton);
        final Button save = (Button) findViewById(R.id.saveButton);

        edit.setVisibility(View.VISIBLE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);

                assessmentOneDisplay.setVisibility(View.INVISIBLE);
                assessmentTwoDisplay.setVisibility(View.INVISIBLE);
                assessmentThreeDisplay.setVisibility(View.INVISIBLE);
                assessmentFourDisplay.setVisibility(View.INVISIBLE);

                assessmentOneEdit.setVisibility(View.VISIBLE);
                assessmentTwoEdit.setVisibility(View.VISIBLE);
                assessmentThreeEdit.setVisibility(View.VISIBLE);
                assessmentFourEdit.setVisibility(View.VISIBLE);

                save.setVisibility(View.VISIBLE);
            }
        });

        save.setVisibility(View.GONE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((assessmentOneEdit.getText().toString()).matches("[0-9]?\\d")){//did the user enter a number between 0 and 100

                    edit.setVisibility(View.VISIBLE);
                    save.setVisibility(View.GONE);

                if (!assessmentOneEdit.getText().toString().equals("")) {
                    int assessmentOne = Integer.parseInt(String.valueOf(assessmentOneEdit.getText()));
                    detailStudent.setAssessmentOne(assessmentOne);
                    assessmentOneDisplay.setText(String.valueOf(detailStudent.getAssessmentOne()));

                    assessmentOneDisplay.setVisibility(View.VISIBLE);
                    assessmentOneEdit.setVisibility(View.GONE);

                }

                if (!assessmentTwoEdit.getText().toString().equals("")) {
                    int assessmentTwo = Integer.parseInt(String.valueOf(assessmentTwoEdit.getText()));
                    detailStudent.setAssessmentTwo(assessmentTwo);
                    assessmentTwoDisplay.setText(String.valueOf(detailStudent.getAssessmentTwo()));

                    assessmentTwoDisplay.setVisibility(View.VISIBLE);
                    assessmentTwoEdit.setVisibility(View.GONE);

                }
                if (!assessmentThreeEdit.getText().toString().equals("")) {
                    int assessmentThree = Integer.parseInt(String.valueOf(assessmentThreeEdit.getText()));
                    detailStudent.setAssessmentThree(assessmentThree);
                    assessmentThreeDisplay.setText(String.valueOf(detailStudent.getAssessmentThree()));

                    assessmentThreeDisplay.setVisibility(View.VISIBLE);
                    assessmentThreeEdit.setVisibility(View.GONE);
                }
                if (!assessmentFourEdit.getText().toString().equals("")) {
                    int assessmentFour = Integer.parseInt(String.valueOf(assessmentFourEdit.getText()));
                    detailStudent.setAssessmentFour(assessmentFour);
                    assessmentFourDisplay.setText(String.valueOf(detailStudent.getAssessmentFour()));

                    assessmentFourDisplay.setVisibility(View.VISIBLE);
                    assessmentFourEdit.setVisibility(View.GONE);

                }

                assessmentOneEdit.setVisibility(View.INVISIBLE);
                assessmentTwoEdit.setVisibility(View.INVISIBLE);
                assessmentThreeEdit.setVisibility(View.INVISIBLE);
                assessmentFourEdit.setVisibility(View.INVISIBLE);

                System.out.println("the students assessemtns are : " + detailStudent.getAssessmentOne() + " " + detailStudent.getAssessmentTwo() + " " + detailStudent.getAssessmentThree() + " " + detailStudent.getAssessmentFour());

                myDbAccess.assessmentUpdate(detailStudent);

            }
                else{
                    Toast.makeText(DetailedActivity.this, "You did not enter a number between 0 and 100", Toast.LENGTH_LONG).show();
                }
        }
        });
        //if there is avlue in database
        //display value
        // on tap open edit text

        //fi there is no value in database
        // display nothing
        //on tap open edit text


        final TextView comments = (TextView)findViewById(R.id.comments);
        comments.setText(detailStudent.getComments());

        final EditText editCommentText = (EditText)findViewById(R.id.editCommentText);
        editCommentText.setText(detailStudent.getComments());
        editCommentText.setVisibility(View.GONE);
        final Button saveComment = (Button)findViewById(R.id.saveComments);
        saveComment.setVisibility(View.GONE);

        final Button editComments = (Button)findViewById(R.id.editComments);
        editComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show edit text
                editComments.setVisibility(View.GONE);
                editCommentText.setVisibility(View.VISIBLE);
                comments.setVisibility(View.GONE);
                saveComment.setVisibility(View.VISIBLE);
            }
        });


        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text from edit text
                String newComment = String.valueOf(editCommentText.getText());
                detailStudent.setComments(newComment);

                // when save button is clicked
                //save into database
                myDbAccess.commentUpdate(detailStudent);

                //
                editCommentText.setVisibility(View.GONE);
                saveComment.setVisibility(View.GONE);
                comments.setText(detailStudent.getComments());
                comments.setVisibility(View.VISIBLE);
                editComments.setVisibility(View.VISIBLE);
            }
        });



    }
}