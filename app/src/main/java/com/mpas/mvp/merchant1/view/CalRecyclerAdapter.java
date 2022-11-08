package com.mpas.mvp.merchant1.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mvp.R;
import java.time.LocalDate;
import java.util.ArrayList;


public class CalRecyclerAdapter extends RecyclerView.Adapter<CalRecyclerAdapter.ViewHolder>{

    private ArrayList<LocalDate> days = new ArrayList<>();
    private final SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
    private int checkPosition = -1;
    private static CalendarListener calendarListener = null;

    public interface CalendarListener {
        void onRefresh(LocalDate localDate, int position);
    }
    public static void setOnCalendarListener(CalendarListener listener){
        calendarListener = listener;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public CalRecyclerAdapter(CalendarListener listener) {
        setDays();
        calendarListener = listener;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.onBind(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.O)
        void onBind(int position) {
            LocalDate localDate = days.get(position);

            String d = String.valueOf(localDate.getDayOfMonth());
            String m = String.valueOf(localDate.getMonthValue());
            String w = String.valueOf(localDate.getDayOfWeek());

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

            if(isItemSelected(position)){
                relativeLayout.setBackgroundColor(Color.parseColor("#6666CC"));
            } else {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }

            relativeLayout.setOnClickListener(view -> {
                clearSelectedItem();
                toggleItemSelected(position);
                checkPosition = position;
                sparseBooleanArray.put(position,true);
                notifyDataSetChanged();

                if(calendarListener != null) {
                    Log.e("onRefresh_localDate",localDate.toString());
                    calendarListener.onRefresh(localDate,position);}
            });
        }
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

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<LocalDate> localDates) {
        days = localDates;
        notifyDataSetChanged();
    }

    private void toggleItemSelected(int position) {

        if (sparseBooleanArray.get(position, false)) {
            sparseBooleanArray.delete(position);
            notifyItemChanged(position);
        } else {
            sparseBooleanArray.put(position, true);
            notifyItemChanged(position);
        }
    }

    private boolean isItemSelected(int position) {
        return sparseBooleanArray.get(position, false);
    }

    public void clearSelectedItem() {
        int position;

        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            position = sparseBooleanArray.keyAt(i);
            sparseBooleanArray.put(position, false);
            notifyItemChanged(position);
        }

        sparseBooleanArray.clear();
    }
}
