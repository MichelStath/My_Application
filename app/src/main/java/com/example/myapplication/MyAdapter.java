package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<AdminAlerts> adminAlerts;

    public MyAdapter(Context context, List<AdminAlerts> adminAlerts, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.adminAlerts = adminAlerts;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false),recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.alertTypeTV.setText(adminAlerts.get(position).getAlertType());
        holder.alertLocationTV.setText(adminAlerts.get(position).getAlertLocation());

    }

    @Override
    public int getItemCount() {
        return adminAlerts.size();
    }
}
