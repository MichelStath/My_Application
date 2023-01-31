package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView alertTypeTV, alertLocationTV;
    Button acceptBTN, declineBTN;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        alertTypeTV = itemView.findViewById(R.id.alertTypeTV);
        alertLocationTV = itemView.findViewById(R.id.alertLocationTV);
        acceptBTN = itemView.findViewById(R.id.acceptBTN);
        declineBTN = itemView.findViewById(R.id.declineBTN);




    }
}
