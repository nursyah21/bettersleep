package com.example.mobile.julfani.tubes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile.julfani.tubes.entity.Sleep;

import java.util.List;

class SleepViewHolder extends RecyclerView.ViewHolder {
    public final TextView textView;

    public SleepViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.recylceview_textview);
    }
}

public class SleepListAdapter extends RecyclerView.Adapter<SleepViewHolder> {

    private List<Sleep> sleepList;
    private final LayoutInflater inflater;

    public SleepListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public SleepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycleview, parent, false);
        return new SleepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SleepViewHolder holder, int position) {
        Sleep current = sleepList.get(position);
        String res = current.getData() + "m";
        holder.textView.setText(res);
    }

    @Override
    public int getItemCount() {
        if(sleepList != null) return sleepList.size();
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSleepList(List<Sleep> sleepList) {
        this.sleepList = sleepList;
        notifyDataSetChanged();
    }
}