package com.example.kathyxu.googlesheetsapi.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kathyxu.googlesheetsapi.R;
import com.example.kathyxu.googlesheetsapi.model.Student;

import java.util.List;

/**
 * Created by kathy on 3/10/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>{

    private List<Student> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Student> itemList){
        this.itemList=itemList;
        this.context=context;
    }


    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.student = itemList.get(position);
        holder.studentName.setText(itemList.get(position).getFname().substring(0,1).toUpperCase() + itemList.get(position).getFname().substring(1)+ " " + itemList.get(position).getLname().substring(0,1).toUpperCase() + itemList.get(position).getLname().substring(1));
        //TODO fill this method out
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
