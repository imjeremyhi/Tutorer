package com.example.kathyxu.googlesheetsapi.controller;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.AssessmentNameAccess;
import com.example.kathyxu.googlesheetsapi.model.AssessmentNameDBHelper;
import com.example.kathyxu.googlesheetsapi.model.StudentAccess;
import com.example.kathyxu.googlesheetsapi.model.StudentDBHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link /AnalyticsFragment./OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnalyticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalyticsFragment extends Fragment {

    StudentDBHelper myDbHelper;
    StudentAccess myDbAccess;
    AssessmentNameDBHelper assDbHelper;
    AssessmentNameAccess assDbAccess;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView assOneLabel;
    TextView assTwoLabel;
    TextView assThreeLabel;
    TextView assFourLabel;

    private TextView meanOne;
    private TextView meanTwo;
    private TextView meanThree;
    private TextView meanFour;

    private TextView medianOne;
    private TextView medianTwo;
    private TextView medianThree;
    private TextView medianFour;

    private TextView rangeOne;
    private TextView rangeTwo;
    private TextView rangeThree;
    private TextView rangeFour;

    private TextView stdOne;
    private TextView stdTwo;
    private TextView stdThree;
    private TextView stdFour;

    protected LineChart mChart;
    protected PieChart mChartTwo;

    private OnFragmentInteractionListener mListener;

    public AnalyticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalyticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalyticsFragment newInstance(String param1, String param2) {
        AnalyticsFragment fragment = new AnalyticsFragment();
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
        myDbHelper = new StudentDBHelper(getActivity());
        myDbAccess = new StudentAccess(myDbHelper);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("i am here");
        View rootView = inflater.inflate(R.layout.fragment_analytics, container, false);
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        assOneLabel = (TextView)rootView.findViewById(R.id.top1);
        assTwoLabel = (TextView)rootView.findViewById(R.id.top2);
        assThreeLabel = (TextView)rootView.findViewById(R.id.top3);
        assFourLabel = (TextView)rootView.findViewById(R.id.top4);

        meanOne = (TextView) rootView.findViewById(R.id.meanOne);
        meanTwo = (TextView) rootView.findViewById(R.id.meanTwo);
        meanThree = (TextView) rootView.findViewById(R.id.meanThree);
        meanFour = (TextView) rootView.findViewById(R.id.meanFour);

        medianOne = (TextView) rootView.findViewById(R.id.medianOne);
        medianTwo = (TextView) rootView.findViewById(R.id.medianTwo);
        medianThree = (TextView) rootView.findViewById(R.id.medianThree);
        medianFour = (TextView) rootView.findViewById(R.id.medianFour);

        rangeOne = (TextView) rootView.findViewById(R.id.rangeOne);
        rangeTwo = (TextView) rootView.findViewById(R.id.rangeTwo);
        rangeThree = (TextView) rootView.findViewById(R.id.rangeThree);
        rangeFour = (TextView) rootView.findViewById(R.id.rangeFour);

        stdOne = (TextView) rootView.findViewById(R.id.stdOne);
        stdTwo = (TextView) rootView.findViewById(R.id.stdTwo);
        stdThree = (TextView) rootView.findViewById(R.id.stdThree);
        stdFour = (TextView) rootView.findViewById(R.id.stdFour);

        mChart = (LineChart) rootView.findViewById(R.id.chart1);
        //mChart.setOnChartGestureListener(this);
        //mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);
        //#0066FF


        XAxis xAxis = mChart.getXAxis();
        //xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        //Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        mChart.getAxisRight().setEnabled(false);
        // add data
        mChart.invalidate();

        //mChart.invalidate();

        // get the legend (only possible after setting data)
        //Legend l = mChart.getLegend();

        // modify the legend ...
        //l.setForm(Legend.LegendForm.LINE);

        //Pie chart now below

        mChartTwo = (PieChart) rootView.findViewById(R.id.chart2);
        mChartTwo.setUsePercentValues(true);
        mChartTwo.getDescription().setEnabled(false);
        mChartTwo.setExtraOffsets(0, 25, 0, 0);

        mChartTwo.setDragDecelerationFrictionCoef(0.95f);

        //mChartTwo.setCenterTextTypeface(mTfLight);
        //mChartTwo.setCenterText(generateCenterSpannableText());

        mChartTwo.setDrawHoleEnabled(true);
        mChartTwo.setHoleColor(Color.WHITE);

        mChartTwo.setTransparentCircleColor(Color.WHITE);
        mChartTwo.setTransparentCircleAlpha(110);

        mChartTwo.setHoleRadius(25f);
        mChartTwo.setTransparentCircleRadius(25f);

        mChartTwo.setDrawCenterText(true);

        mChartTwo.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChartTwo.setRotationEnabled(false);
        mChartTwo.setHighlightPerTapEnabled(true);

        Legend l = mChartTwo.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(0f);
        l.setYOffset(-40);


        mChartTwo.setEntryLabelColor(Color.WHITE);
        //mChartTwo.setEntryLabelTypeface(mTfRegular);
        mChartTwo.setEntryLabelTextSize(12f);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setData();
            List<ILineDataSet> sets = mChart.getData()
                    .getDataSets();

            for (ILineDataSet iSet : sets) {
                LineDataSet set = (LineDataSet) iSet;
                set.setDrawFilled(true);
            }
            mChart.animateXY(1000,1000);
            setDataThree();
            setDataTwo();
            mChartTwo.animateX(1400, Easing.EasingOption.EaseInOutQuad);
        }
        else {
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
    }*/

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

    private void setData() {
        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Float> myValues = myDbAccess.getMeans();
        float one = myValues.get(0);
        int colOne = 1;
        float two = myValues.get(1);
        int colTwo = 2;
        float three = myValues.get(2);
        int colThree = 3;
        float four = myValues.get(3);
        int colFour = 4;
        values.add(new Entry(colOne,one));
        values.add(new Entry(colTwo,two));
        values.add(new Entry(colThree,three));
        values.add(new Entry(colFour,four));
        LineDataSet set = new LineDataSet(values,"");
        set.setColor(Color.rgb(0,40,100));
        set.setCircleColor(Color.rgb(0,40,100));
        set.setValueTextSize(15f);
        LineData data = new LineData(set);
        mChart.setData(data);
    }

    private void setDataTwo() {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Float> myValues = myDbAccess.getPieChartDataSpread();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        PieEntry fail = new PieEntry(myValues.get(0), "Fail");
        entries.add(fail);
        PieEntry pass = new PieEntry(myValues.get(1), "Pass");
        entries.add(pass);
        PieEntry credit = new PieEntry(myValues.get(2), "Credit");
        entries.add(credit);
        PieEntry distinction = new PieEntry(myValues.get(3), "Distinction");
        entries.add(distinction);
        PieEntry highDistinction = new PieEntry(myValues.get(4), "High Distinction");
        entries.add(highDistinction);



    /*
    for (int i = 0; i < count ; i++) {

        entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
    }*/

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        mChartTwo.setData(data);

        // undo all highlights
        mChartTwo.highlightValues(null);

        mChartTwo.invalidate();
    }

    private void setDataThree() {
        assDbHelper = new AssessmentNameDBHelper(getActivity());
        assDbAccess = new AssessmentNameAccess(assDbHelper);

        String nameOfOne = String.valueOf(assDbAccess.getAssessment("1").getName());
        assOneLabel.setText(nameOfOne);
        String nameOfTwo = String.valueOf(assDbAccess.getAssessment("2").getName());
        assTwoLabel.setText(nameOfTwo);
        String nameOfThree = String.valueOf(assDbAccess.getAssessment("3").getName());
        assThreeLabel.setText(nameOfThree);
        String nameOfFour = String.valueOf(assDbAccess.getAssessment("4").getName());
        assFourLabel.setText(nameOfFour);

        ArrayList<Float> means = myDbAccess.getMeans();
        ArrayList<Integer> medians = myDbAccess.getMedians();
        ArrayList<Integer> ranges = myDbAccess.getRanges();
        ArrayList<Float> stdDeviations = myDbAccess.getStdDeviations();

        if (means.get(0) != 0.0f) {
            meanOne.setText(String.valueOf(means.get(0)));
        }
        if (means.get(1) != 0.0f) {
            meanTwo.setText(String.valueOf(means.get(1)));
        }
        if (means.get(1) != 0.0f) {
            meanThree.setText(String.valueOf(means.get(2)));
        }
        if (means.get(1) != 0.0f) {
            meanFour.setText(String.valueOf(means.get(3)));
        }

        if (medians.get(0) != -1) {
            medianOne.setText(String.valueOf(medians.get(0)));
        }
        if (medians.get(1) != -1) {
            medianTwo.setText(String.valueOf(medians.get(1)));
        }
        if (medians.get(2) != -1) {
            medianThree.setText(String.valueOf(medians.get(2)));
        }
        if (medians.get(3) != -1) {
            medianFour.setText(String.valueOf(medians.get(3)));
        }

        if (ranges.get(0) != -100) {
            rangeOne.setText(String.valueOf(ranges.get(0)));
        }
        if (ranges.get(1) != -100) {
            rangeTwo.setText(String.valueOf(ranges.get(1)));
        }
        if (ranges.get(2) != -100) {
            rangeThree.setText(String.valueOf(ranges.get(2)));
        }
        if (ranges.get(3) != -100) {
            rangeFour.setText(String.valueOf(ranges.get(3)));
        }

        if (stdDeviations.get(0) != -1.0f) {
            stdOne.setText(String.format("%.1f", stdDeviations.get(0)));
        }
        if (stdDeviations.get(1) != -1.0f) {
            stdTwo.setText(String.format("%.1f", stdDeviations.get(1)));
        }
        if (stdDeviations.get(2) != -1.0f) {
            stdThree.setText(String.format("%.1f", stdDeviations.get(2)));
        }
        if (stdDeviations.get(3) != -1.0f) {
            stdFour.setText(String.format("%.1f", stdDeviations.get(3)));
        }
    }
}
