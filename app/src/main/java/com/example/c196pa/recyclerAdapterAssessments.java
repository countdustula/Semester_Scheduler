package com.example.c196pa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapterAssessments extends RecyclerView.Adapter<recyclerAdapterAssessments.MyViewHolder>{
    private ArrayList<Assessment> assessmentList;
    private RecyclerViewClickListener listener;


    public recyclerAdapterAssessments(ArrayList<Assessment> assessmentList, RecyclerViewClickListener listener){
        this.assessmentList = assessmentList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameAssessment;
        private TextView assessmentDescription;
        private TextView assessmentDates;

        public MyViewHolder(final View view){
            super(view);
            nameAssessment = view.findViewById(R.id.assessmentName);
            assessmentDescription = view.findViewById(R.id.realAssessmentDescription);
            assessmentDates = view.findViewById(R.id.assessmentDates);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public recyclerAdapterAssessments.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_assessments, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterAssessments.MyViewHolder holder, int position) {
        String assessment = assessmentList.get(position).getAssessmentName();
        String description = assessmentList.get(position).getAssessmentDetails();
        String dates = assessmentList.get(position).getStartDate() + " - " + assessmentList.get(position).getEndDate();
        holder.nameAssessment.setText(assessment);
        holder.assessmentDescription.setText(description);
        holder.assessmentDates.setText(dates);
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
