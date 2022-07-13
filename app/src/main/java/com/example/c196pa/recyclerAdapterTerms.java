package com.example.c196pa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapterTerms extends RecyclerView.Adapter<recyclerAdapterTerms.MyViewHolder>{
    private ArrayList<Term> termList;
    private recyclerAdapterClasses.RecyclerViewClickListener listener;

    public recyclerAdapterTerms(ArrayList<Term> termList, recyclerAdapterClasses.RecyclerViewClickListener listener){
        this.termList = termList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView termName;
        private TextView termDates;
        private TextView classNum;

        public MyViewHolder(final View view){
            super(view);
            termName = view.findViewById(R.id.layoutTermName);
            termDates = view.findViewById(R.id.layoutTermDates);
            classNum = view.findViewById(R.id.layoutTermClassNum);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public recyclerAdapterTerms.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_terms, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterTerms.MyViewHolder holder, int position) {
        String termName = termList.get(position).getTermName();
        Integer classAmt = termList.get(position).getAssociatedClasses().size();
        String dates = termList.get(position).getStartDate() + " - " + termList.get(position).getEndDate();
        holder.termName.setText(termName);
        holder.classNum.setText("Classes: " + classAmt);
        holder.termDates.setText(dates);
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
