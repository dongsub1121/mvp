package com.mpas.mvp.merchant1.view;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mvp.R;
import java.time.LocalDate;
import java.util.ArrayList;


public class CalRecyclerAdapter extends RecyclerView.Adapter<CalRecyclerAdapter.ViewHolder>{

    private ArrayList<LocalDate> days = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CalRecyclerAdapter() {
        setDays();
    }

    @NonNull
    @Override
    public CalRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.calender_cardview, parent, false);

        return new CalRecyclerAdapter.ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(days.get(position));

        holder.relativeLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                view.setBackgroundColor(Color.parseColor("#666699"));
                Log.e("view", String.valueOf(b));
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    public int getItemCount() {
        return days.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDays() {
        LocalDate localDate = LocalDate.now();
        int i = 365;

        while (i>0) {
            days.add(localDate.minusDays(i));
            i--;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView day , month, week;
        CardView cardView;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.calDay);
            month = itemView.findViewById(R.id.calMonth);
            week = itemView.findViewById(R.id.calWeek);
            cardView = itemView.findViewById(R.id.card);
            relativeLayout = itemView.findViewById(R.id.card_relative);

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void setItem(LocalDate date){

            String d = String.valueOf(date.getDayOfMonth());
            String m = String.valueOf(date.getMonthValue());
            String w = String.valueOf(date.getDayOfWeek());

            day.setText(d);
            month.setText(m);
            week.setText(w.substring(0,3));

            if(w.equals("SATURDAY")){
                day.setTextColor(Color.parseColor("#000099"));
                week.setTextColor(Color.parseColor("#000099"));
            }else if(w.equals("SUNDAY")){
                day.setTextColor(Color.parseColor("#CC0000"));
                week.setTextColor(Color.parseColor("#CC0000"));
            }else{
                day.setTextColor(Color.parseColor("#000000"));
                week.setTextColor(Color.parseColor("#000000"));
            }
        }
    }
}
