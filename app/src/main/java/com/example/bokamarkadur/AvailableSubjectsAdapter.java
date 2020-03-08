package com.example.bokamarkadur;

import android.content.Context;
import android.content.Intent;
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

    // Notað fyrir debugging
    private static final String TAG = "SubjectsAdapter";

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

    // Tökum viðeigandi gildi fyrir hverja bók og tengjum við layout hluti.
    @Override
    public void onBindViewHolder(AvailableSubjectsAdapter.SubjectViewHolder holder, final int position) {

        holder.availableSubjects.setText(subjects.get(position));

        // Sendum það subject sem var valið áfram í BookBySubjectActivity.
        holder.availableSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + subjects.get(position));

                Intent intent = new Intent(context, BookBySubjectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("viewSubject", subjects.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
