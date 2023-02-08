package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView alertTypeTV, alertLocationTV, alertDeskTV;
    Button acceptBTN, declineBTN;
//πρεπει να βαλω και reco + id

    public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        alertTypeTV = itemView.findViewById(R.id.alertTypeTV);
        alertLocationTV = itemView.findViewById(R.id.alertLocationTV);
        alertDeskTV = itemView.findViewById(R.id.alertDescTV);
        acceptBTN = itemView.findViewById(R.id.acceptBTN);
        declineBTN = itemView.findViewById(R.id.declineBTN);



        acceptBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface!=null){
                    int position = getAdapterPosition();

                    if(position!= RecyclerView.NO_POSITION){
                        recyclerViewInterface.onAcceptBtnClicked(position);
                    }

                }
            }
        });

        declineBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface!=null){
                    int position = getAdapterPosition();

                    if(position!= RecyclerView.NO_POSITION){
                        recyclerViewInterface.onDeclineBtnClicked(position);
                    }

                }
            }
        });




    }
}
