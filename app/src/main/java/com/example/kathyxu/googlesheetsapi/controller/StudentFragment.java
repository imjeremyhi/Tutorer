package com.example.kathyxu.googlesheetsapi.controller;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.Assessment;
import com.example.kathyxu.googlesheetsapi.model.AssessmentNameAccess;
import com.example.kathyxu.googlesheetsapi.model.AssessmentNameDBHelper;
import com.example.kathyxu.googlesheetsapi.model.Student;
import com.example.kathyxu.googlesheetsapi.model.StudentAccess;
import com.example.kathyxu.googlesheetsapi.model.StudentDBHelper;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerViewAdapter adapter;
    StudentDBHelper myDbHelper;
    StudentAccess myDbAccess;
    AssessmentNameDBHelper assDbHelper;
    AssessmentNameAccess assDbAccess;
    private RecyclerView rView;
    List<Student> studentToSearch;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment StudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*
    public static StudentFragment newInstance(String param1, String param2) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("reached");

        View rootView = inflater.inflate(R.layout.fragment_student, container, false);

        myDbHelper = new StudentDBHelper(getActivity());
        myDbAccess = new StudentAccess(myDbHelper);

        //load default values of assessment names in database
        assDbHelper = new AssessmentNameDBHelper(getActivity());
        assDbAccess = new AssessmentNameAccess(assDbHelper);
        Assessment def1 = new Assessment();
        def1.setId(1);
        def1.setName("Assessment 1");
        Assessment def2 = new Assessment();
        def2.setId(2);
        def2.setName("Assessment 2");
        Assessment def3 = new Assessment();
        def3.setId(3);
        def3.setName("Assessment 3");
        Assessment def4 = new Assessment();
        def4.setId(4);
        def4.setName("Assessment 4");
        assDbAccess.insertAssessment(def1);
        assDbAccess.insertAssessment(def2);
        assDbAccess.insertAssessment(def3);
        assDbAccess.insertAssessment(def4);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        rView = (RecyclerView)rootView.findViewById(R.id.recycler_view);//TODO
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayoutManager);

        System.out.println("WTF IS HAPPENING");

        boolean databaseExists = databaseExists();
        if (databaseExists==true){//if there is a database, load the display using the database
            System.out.println("setdisplay called!");
            setDisplay();
        }
        else{//if there is no database, go get the info from poke api using the getPokemon() method
            studentToSearch=myDbAccess.getAll();
            DownloadData downloadData = (DownloadData) new DownloadData();
            downloadData.execute();
            System.out.println("executed!");
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private boolean databaseExists(){
        boolean isExists = false;
        if(myDbAccess.getAll().size()>0){
            isExists=true;
        }
        return isExists;
    }

    private void setDisplay(){
        List <Student> dblist = myDbAccess.getAll();

        for(Student s : dblist){
            System.out.println("student: " + s.getFname() + " " + s.getLname());
        }

        adapter = new RecyclerViewAdapter(getActivity(), dblist);//here your arraylist of pokemon
        rView.setAdapter(adapter);
        System.out.println("SET DISPLAY WORKED!");
    }

    public class DownloadData extends AsyncTask<Void, Void, Integer> {
//call to googlesheets api to retrieve information from students spreadsheet
        @Override
        protected Integer doInBackground(Void... params) {
            Student studentToAdd = new Student();
            SpreadsheetService service = new SpreadsheetService("com.example");
            try {
                System.out.println("hi1");

                String urlString = "https://spreadsheets.google.com/feeds/list/1WyczlSzNWQSWf88KqTVOwJccMhxK8JTARArUWLtxIps/default/public/values";

                URL url = new URL(urlString);

                ListFeed feed = service.getFeed(url, ListFeed.class);

                for (ListEntry entry : feed.getEntries()) {

                    CustomElementCollection elements = entry.getCustomElements();
//for firstname
                    String fname = elements.getValue("FirstName");
                    System.out.println("First Name is: " + fname);
                    studentToAdd.setFname(fname);

//for lastname
                    String lname = elements.getValue("LastName");
                    System.out.println("Last Name is: " + lname);
                    studentToAdd.setLname(lname);

//for zID
                    String id = elements.getValue("zID");
                    String idToAdd = "";
                    if(id.substring(0,1).toLowerCase().equals("z")){
                        System.out.println(id.substring(0,1));
                        idToAdd = id.substring(1);
                    }
                    else{
                        idToAdd = id;
                    }
                    System.out.println("zID: " + id);
                    studentToAdd.setId(Integer.parseInt(idToAdd));

//for zmail
                    String zmail = elements.getValue("zmail");
                    System.out.println("zmail is: " + zmail);
                    studentToAdd.setZmail(zmail);

//for tutorial time
                    String tut = elements.getValue("Tutorial");
                    System.out.println("tutorial is: "+ tut);
                    studentToAdd.setTut(tut);


                    studentToAdd.setComments("Write a comment");
                    studentToAdd.setAssessmentOne(-1);
                    studentToAdd.setAssessmentTwo(-1);
                    studentToAdd.setAssessmentThree(-1);
                    studentToAdd.setAssessmentFour(-1);

                    myDbAccess.insertStudent(studentToAdd);//inserts the student into the database

                    System.out.println("student "+studentToAdd.getFname() + " " + studentToAdd.getLname() + " has zID of " + studentToAdd.getId() + " and a zmail of " + studentToAdd.getZmail() + " and a tutorial at " + studentToAdd.getTut() + " was added");

                }
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ServiceException e) {
                e.printStackTrace();

            }
            //setDisplay();

            return 1;
        }
        @Override
        protected void onPostExecute(Integer hi) {
            System.out.println("on post execute reached");
            setDisplay();
        }
    }
}