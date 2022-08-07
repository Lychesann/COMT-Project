package com.example.musicsteam2;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyView extends RecyclerView.ViewHolder{
    public TextView titleTxt;
    public TextView titleArtist;
    public ImageView image1;
    public Button removeBtn;

    public MyView(@NonNull View itemView) {
        super(itemView);

        titleTxt = itemView.findViewById(R.id.titleTxt);
        titleArtist = itemView.findViewById(R.id.titleArtist);
        image1 = itemView.findViewById(R.id.image1);
        removeBtn = itemView.findViewById(R.id.removeBtn);
    }
}