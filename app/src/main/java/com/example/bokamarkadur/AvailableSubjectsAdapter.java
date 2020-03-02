package com.example.bokamarkadur;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bokamarkadur.POJO.Book;

import java.util.List;

public class AvailableSubjectsAdapter extends RecyclerView.Adapter<AvailableSubjectsAdapter.SubjectViewHolder> {

    private static final String TAG = "ASubjectsAdapter";

    private List<String> subjects;
    private int rowLayout;
    private Context context;


    public static class SubjectViewHolder extends RecyclerView.ViewHolder {

        LinearLayout subjectsLayout;
        TextView availableSubjects;

        public SubjectViewHolder(View v) {
            super(v);
            subjectsLayout = (LinearLayout) v.findViewById(R.id.available_subjects_layout);
            availableSubjects = (TextView) v.findViewById(R.id.available_subjects);
        }
    }

    public AvailableSubjectsAdapter(List<String> subjects, int rowLayout, Context context) {
        this.subjects = subjects;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public AvailableSubjectsAdapter.SubjectViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AvailableSubjectsAdapter.SubjectViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AvailableSubjectsAdapter.SubjectViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.availableSubjects.setText(subjects.get(position));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
