package com.example.c196pa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapterClasses extends RecyclerView.Adapter<recyclerAdapterClasses.MyViewHolder>{
    private ArrayList<Class> classList;
    private RecyclerViewClickListener listener;

    public recyclerAdapterClasses(ArrayList<Class> classList, RecyclerViewClickListener listener){
        this.classList = classList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView className;
        private TextView classDates;
        private TextView assessmentNum;

        public MyViewHolder(final View view){
            super(view);
            className = view.findViewById(R.id.classNameLayout);
            classDates = view.findViewById(R.id.classDateLayout);
            assessmentNum = view.findViewById(R.id.assessmentNumLayout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public recyclerAdapterClasses.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_classes, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterClasses.MyViewHolder holder, int position) {
        String className = classList.get(position).getClassName();
        Integer assessmentAmt = classList.get(position).getAssociatedAssessments().size();
        String dates = classList.get(position).getClassStart() + " - " + classList.get(position).getClassEnd();
        holder.className.setText(className);
        holder.assessmentNum.setText("Assessments: " + assessmentAmt);
        holder.classDates.setText(dates);
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
