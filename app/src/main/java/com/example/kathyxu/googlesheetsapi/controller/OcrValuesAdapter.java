package com.example.kathyxu.googlesheetsapi.controller;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kathyxu.googlesheetsapi.R;

import java.util.ArrayList;

/**
 * Created by Jeremy on 6/10/2016.
 */

public class OcrValuesAdapter extends RecyclerView.Adapter<OcrValuesAdapter.ViewHolder> {
    private ArrayList<String> valuesList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public EditText value;
        public Button deleteButton;

        public ViewHolder (View v) {
            super(v);
            value = (EditText) v.findViewById(R.id.value);
            deleteButton = (Button) v.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            removeRecord(getAdapterPosition());
        }
    }

    public OcrValuesAdapter(ArrayList<String>valuesList) {
        this.valuesList = valuesList;
    }

    public void removeRecord(int position) {
        valuesList.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<String> retrieveData() {
        return valuesList;
    }
    @Override
    public OcrValuesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.values_row, parent, false);
        ViewHolder vh = new ViewHolder(inflatedView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.value.setText(valuesList.get(position));
        holder.value.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valuesList.set(position, s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return valuesList.size();
    }
}
