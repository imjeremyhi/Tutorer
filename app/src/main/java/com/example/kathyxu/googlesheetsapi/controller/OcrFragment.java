package com.example.kathyxu.googlesheetsapi.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.Attendance;
import com.example.kathyxu.googlesheetsapi.model.AttendanceAccess;
import com.example.kathyxu.googlesheetsapi.model.StudentDBHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OcrFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OcrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OcrFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView myRecyclerView;
    private LinearLayoutManager myLinearLayoutManager;
    StudentDBHelper myDbHelper;
    private OcrValuesAdapter myAdapter;
    private AttendanceAccess myDbAccess;
    ArrayList<String> passedList;
    ArrayList<Attendance> newList;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OcrFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OcrFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OcrFragment newInstance(String param1, String param2) {
        OcrFragment fragment = new OcrFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_ocr, container, false);
        myDbHelper = new StudentDBHelper(getActivity());
        myDbAccess = new AttendanceAccess(myDbHelper);

        passedList = getActivity().getIntent().getStringArrayListExtra("returnedList");
        if (passedList != null) {
            for (String str : passedList) {
                System.out.println("items are");
                System.out.println(str);
            }
            myRecyclerView = (RecyclerView)rootView.findViewById(R.id.myOcrValuesRecyclerView);
            myLinearLayoutManager = new LinearLayoutManager(getActivity());
            myRecyclerView.setLayoutManager(myLinearLayoutManager);
            myAdapter = new OcrValuesAdapter(passedList);
            myRecyclerView.setAdapter(myAdapter);
        }
        Button ocrLoadButton = (Button)rootView.findViewById(R.id.loadOcrButton);
        ocrLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);
                getActivity().startActivity(intent);
            }
        });

        Button saveButton = (Button)rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passedList != null) {
                    if (passedList.size() % 2 != 0 || passedList.size() == 0) {
                        Toast.makeText(getActivity(), "Missing zId or name. Please scan agan", Toast.LENGTH_LONG).show();
                    } else {
                        passedList = myAdapter.retrieveData();
                        for (String str : passedList) {
                            System.out.println("items in update record are ");
                            System.out.println(str);
                        }
                        int i = 0;
                        newList = new ArrayList<>();
                        while (i < passedList.size()) {
                            String id = passedList.get(i);
                            String name = passedList.get(i + 1);
                            Attendance toAdd = new Attendance(id, name);
                            newList.add(toAdd);
                            i++;
                            i++;
                        }
                        myDbAccess.insertAttendances(newList);
                        Toast.makeText(getActivity(), "Attendance successfully saved", Toast.LENGTH_LONG).show();
                        passedList.clear();
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        return rootView;
    }

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

    public void loadOcr(View v) {
        Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);
        this.startActivity(intent);
    }

    public void updateRecord(View v) {
        passedList = myAdapter.retrieveData();
        for (String str : passedList) {
            System.out.println("items in update record are ");
            System.out.println(str);
        }
    }
}
