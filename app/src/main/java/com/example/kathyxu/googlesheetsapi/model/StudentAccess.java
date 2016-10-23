package com.example.kathyxu.googlesheetsapi.model;

import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by kathy on 3/10/2016.
 */
public class StudentAccess implements DbAccess {
    private final StudentContract studentContract;

    public StudentAccess(SQLiteOpenHelper sqLiteOpenHelper) {
        this.studentContract = new StudentContract(sqLiteOpenHelper);
    }

    //Example of overriding interface
    @Override
    public List<Student> getAll() {
        return studentContract.getStudents();
    }

    //Example standard contract methods
    public void insertStudent(Student stu){
        studentContract.insert(stu);
    }

    public void deleteStudent(int id){
        studentContract.delete(id);
    }

    //Example of extra methods
    public void insertStudents(List<Student> students){
        for(Student stu : students){
            studentContract.insert(stu);
        }
    }

    public void deleteStudents(int... ids){
        for(int id : ids){
            studentContract.delete(id);
        }
    }
    public Student getStudent(String number){
        return studentContract.getStudent(Integer.parseInt(number));
    }

    public boolean hasStu(int id){
        return studentContract.getStudent(id) != null;
    }

    public void assessmentUpdate(Student stu){
        studentContract.updateStudentAssessements(stu);
    }
    public void commentUpdate(Student stu){
        studentContract.updateComments(stu);
    }

    public void photoUpdate(Student stu){
        studentContract.updatePhoto(stu);
    }

    public ArrayList<Float> getMeans() {
        int totalOne = 0;
        int totalTwo = 0;
        int totalThree = 0;
        int totalFour = 0;
        List<Student> students = getAll();
        int sizeOne = students.size();
        int sizeTwo = sizeOne;
        int sizeThree = sizeOne;
        int sizeFour = sizeOne;

        for (Student student: students) {
            if (student.getAssessmentOne() != -1) {
                totalOne += student.getAssessmentOne();
            } else if (student.getAssessmentOne() == -1) {
                sizeOne--;
            }
            if (student.getAssessmentTwo() != -1) {
                totalTwo += student.getAssessmentTwo();
            } else if (student.getAssessmentTwo() == -1) {
                sizeTwo--;
            }
            if (student.getAssessmentThree() != -1) {
                totalThree += student.getAssessmentThree();
            } else if (student.getAssessmentThree() == -1) {
                sizeThree--;
            }

            if (student.getAssessmentFour() != -1) {
                totalFour += student.getAssessmentFour();
            } else if (student.getAssessmentFour() == -1) {
                sizeFour--;
            }
        }
        if (sizeOne == 0) {
            sizeOne = 1;
        }
        if (sizeTwo == 0) {
            sizeTwo = 1;
        }
        if (sizeThree == 0) {
            sizeThree = 1;
        }
        if (sizeFour == 0) {
            sizeFour = 1;
        }
        ArrayList<Float> means = new ArrayList<>();
        means.add((float)totalOne/sizeOne);
        means.add((float)totalTwo/sizeTwo);
        means.add((float)totalThree/sizeThree);
        means.add((float)totalFour/sizeFour);
        return means;
    }

    public ArrayList<Integer> getMedians() {
        List<Student> students = getAll();
        ArrayList<Student> studentListOne = new ArrayList<>();
        ArrayList<Student> studentListTwo = new ArrayList<>();
        ArrayList<Student> studentListThree = new ArrayList<>();
        ArrayList<Student> studentListFour = new ArrayList<>();

        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        for (Student student: students) {
            if (student.getAssessmentOne() != -1) {
                studentListOne.add(i, student);
                i++;
            }
            if (student.getAssessmentTwo() != -1) {
                studentListTwo.add(j, student);
                j++;
            }
            if (student.getAssessmentThree() != -1) {
                studentListThree.add(k, student);
                k++;
            }
            if (student.getAssessmentFour() != -1) {
                studentListFour.add(l, student);
                l++;
            }
        }
        ArrayList<Integer> medians = new ArrayList<>();
        if (i != 0) {
            Student middleStudentOne = students.get((int) (studentListOne.size() / 2));
            medians.add(middleStudentOne.getAssessmentOne());
        } else if (i == 0) {
            medians.add(-1);
        }
        if (j != 0) {
            Student middleStudentTwo = students.get((int) (studentListTwo.size() / 2));
            medians.add(middleStudentTwo.getAssessmentTwo());
        } else if (j == 0) {
            medians.add(-1);
        }
        if (k != 0) {
            Student middleStudentThree = students.get((int) (studentListThree.size() / 2));
            medians.add(middleStudentThree.getAssessmentThree());
        } else if (k == 0) {
            medians.add(-1);
        }
        if (l != 0) {
            Student middleStudentFour = students.get((int) (studentListFour.size() / 2));
            medians.add(middleStudentFour.getAssessmentFour());
        } else if (l == 0) {
            medians.add(-1);
        }
        return medians;
    }

    public ArrayList<Integer> getRanges() {
        int lowestAssessmentOne = 100;
        int lowestAssessmentTwo = 100;
        int lowestAssessmentThree = 100;
        int lowestAssessmentFour = 100;
        int highestAssessmentOne = 0;
        int highestAssessmentTwo = 0;
        int highestAssessmentThree = 0;
        int highestAssessmentFour = 0;

        List<Student> students = getAll();
        for (Student student: students) {
            if (student.getAssessmentOne() != -1) {
                if (student.getAssessmentOne() > highestAssessmentOne) {
                    highestAssessmentOne = student.getAssessmentOne();
                }
                if (student.getAssessmentOne() < lowestAssessmentOne) {
                    lowestAssessmentOne = student.getAssessmentOne();
                }
            }
            if (student.getAssessmentTwo() != -1) {
                if (student.getAssessmentTwo() > highestAssessmentTwo) {
                    highestAssessmentTwo = student.getAssessmentTwo();
                }
                if (student.getAssessmentTwo() < lowestAssessmentTwo) {
                    lowestAssessmentTwo = student.getAssessmentTwo();
                }
            }
            if (student.getAssessmentThree() != -1) {
                if (student.getAssessmentThree() > highestAssessmentThree) {
                    highestAssessmentThree = student.getAssessmentThree();
                }
                if (student.getAssessmentThree() < lowestAssessmentThree) {
                    lowestAssessmentThree = student.getAssessmentThree();
                }
            }
            if (student.getAssessmentFour() != -1) {
                if (student.getAssessmentFour() > highestAssessmentFour) {
                    highestAssessmentFour = student.getAssessmentFour();
                }
                if (student.getAssessmentFour() < lowestAssessmentFour) {
                    lowestAssessmentFour = student.getAssessmentFour();
                }
            }
        }
        ArrayList<Integer> ranges = new ArrayList<>();
        ranges.add(highestAssessmentOne-lowestAssessmentOne);
        ranges.add(highestAssessmentTwo-lowestAssessmentTwo);
        ranges.add(highestAssessmentThree-lowestAssessmentThree);
        ranges.add(highestAssessmentFour-lowestAssessmentFour);
        return ranges;
    }

    public ArrayList<Float> getStdDeviations() {
        int executed = 0;
        int sdTotalOne = 0;
        int sdTotalTwo = 0;
        int sdTotalThree = 0;
        int sdTotalFour = 0;
        ArrayList<Float> means = getMeans();
        List<Student> students = getAll();
        for (Student student: students) {
            if (student.getAssessmentOne() != -1) {
                sdTotalOne += pow((student.getAssessmentOne() - means.get(0)),2);
                executed = 1;
            }
            if (student.getAssessmentTwo() != -1) {
                sdTotalTwo += pow((student.getAssessmentTwo() - means.get(1)),2);
            }
            if (student.getAssessmentThree() != -1) {
                sdTotalThree += pow((student.getAssessmentThree() - means.get(2)),2);
            }
            if (student.getAssessmentFour() != -1) {
                sdTotalFour += pow((student.getAssessmentFour() - means.get(3)),2);
            }
        }

        int size = students.size();
        ArrayList<Float> standardDeviations = new ArrayList<>();

        if (size == 0 || executed == 0) {
            standardDeviations.add(-1.0f);
            standardDeviations.add(-1.0f);
            standardDeviations.add(-1.0f);
            standardDeviations.add(-1.0f);
        } else {
            float sdOne = (float) sqrt(sdTotalOne / size);
            float sdTwo = (float) sqrt(sdTotalTwo / size);
            float sdThree = (float) sqrt(sdTotalThree / size);
            float sdFour = (float) sqrt(sdTotalFour / size);
            standardDeviations.add(sdOne);
            standardDeviations.add(sdTwo);
            standardDeviations.add(sdThree);
            standardDeviations.add(sdFour);
        }
        return standardDeviations;
    }

    public ArrayList<Float> getPieChartDataSpread() {
        List<Student> students = getAll();
        int size = students.size() * 4;
        int failCount = 0;
        int passCount = 0;
        int creditCount = 0;
        int distinctionCount = 0;
        int highDistinctionCount = 0;
        for (Student student: students) {
            if (student.getAssessmentOne() != -1) {
                int value = student.getAssessmentOne();
                if (value < 50) {
                    failCount++;
                } else if (value >= 50  && value < 65) {
                    passCount++;
                } else if (value >= 65 && value < 75) {
                    creditCount++;
                } else if (value >= 75 && value < 85) {
                    distinctionCount++;
                } else if (value >= 85 && value <= 100) {
                    highDistinctionCount++;
                }
            } else if (student.getAssessmentOne() == -1) {
                size--;
            }
            if (student.getAssessmentTwo() != -1) {
                int value = student.getAssessmentTwo();
                if (value < 50) {
                    failCount++;
                } else if (value >= 50  && value < 65) {
                    passCount++;
                } else if (value >= 65 && value < 75) {
                    creditCount++;
                } else if (value >= 75 && value < 85) {
                    distinctionCount++;
                } else if (value >= 85 && value <= 100) {
                    highDistinctionCount++;
                }
            } else if (student.getAssessmentTwo() == -1) {
                size--;
            }
            if (student.getAssessmentThree() != -1) {
                int value = student.getAssessmentThree();
                if (value < 50) {
                    failCount++;
                } else if (value >= 50  && value < 65) {
                    passCount++;
                } else if (value >= 65 && value < 75) {
                    creditCount++;
                } else if (value >= 75 && value < 85) {
                    distinctionCount++;
                } else if (value >= 85 && value <= 100) {
                    highDistinctionCount++;
                }
            } else if (student.getAssessmentThree() == -1) {
                size--;
            }
            if (student.getAssessmentFour() != -1) {
                int value = student.getAssessmentFour();
                if (value < 50) {
                    failCount++;
                } else if (value >= 50  && value < 65) {
                    passCount++;
                } else if (value >= 65 && value < 75) {
                    creditCount++;
                } else if (value >= 75 && value < 85) {
                    distinctionCount++;
                } else if (value >= 85 && value <= 100) {
                    highDistinctionCount++;
                }
            } else if (student.getAssessmentFour() == -1) {
                size--;
            }
        }

        if (size == 0) {
            size = 1;
        }

        ArrayList<Float> spread = new ArrayList<>();
        spread.add((float)failCount/size*100);
        spread.add((float)passCount/size*100);
        spread.add((float)creditCount/size*100);
        spread.add((float)distinctionCount/size*100);
        spread.add((float)highDistinctionCount/size*100);
        return spread;
    }
}
