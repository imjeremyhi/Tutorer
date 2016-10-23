package com.example.kathyxu.googlesheetsapi.model;

/**
 * Created by kathy on 29/09/2016.
 */
public class Student {

    private String fname;
    private String lname;
    private int id;
    private String zmail;
    private String tut;
    private String comments;
    private int assessmentOne;
    private int assessmentTwo;
    private int assessmentThree;
    private int assessmentFour;
    private byte[] photo;

    public Student(){

    }

    public Student(String fname, String lname, int id, String zmail, String tut) {
        this.fname = fname;
        this.lname = lname;
        this.id = id;
        this.zmail = zmail;
        this.tut = tut;

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZmail() {
        return zmail;
    }

    public void setZmail(String zmail) {
        this.zmail = zmail;
    }

    public String getTut() {
        return tut;
    }

    public void setTut(String tut) {
        this.tut = tut;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getAssessmentOne() {
        return assessmentOne;
    }

    public void setAssessmentOne(int assessmentOne) {
        this.assessmentOne = assessmentOne;
    }

    public int getAssessmentTwo() {
        return assessmentTwo;
    }

    public void setAssessmentTwo(int assessmentTwo) {
        this.assessmentTwo = assessmentTwo;
    }

    public int getAssessmentThree() {
        return assessmentThree;
    }

    public void setAssessmentThree(int assessmentThree) {
        this.assessmentThree = assessmentThree;
    }

    public int getAssessmentFour() {
        return assessmentFour;
    }

    public void setAssessmentFour(int assessmentFour) {
        this.assessmentFour = assessmentFour;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
