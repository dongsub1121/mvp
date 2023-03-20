package com.mpas.mvp.merchant1.view;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;
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

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.ViewHolder>{

    private ArrayList<LocalDate> days = new ArrayList<>();
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    String fragment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public MultiChoiceAdapter(String frag) {
        setDays();
        this.fragment = frag;
        Log.e("fragment",fragment);
    }


    @NonNull
    @Override
    public MultiChoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.calender_cardview, parent, false);

        return new MultiChoiceAdapter.ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MultiChoiceAdapter.ViewHolder holder, int position) {
        holder.setItem(days.get(position));

        if (isItemSelected(position)) {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#6666CC"));
        } else {
            holder.relativeLayout.setBackgroundColor(Color.WHITE);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView day , month, week;

        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.calDay);
            month = itemView.findViewById(R.id.calMonth);
            week = itemView.findViewById(R.id.calWeek);

            relativeLayout = itemView.findViewById(R.id.card_relative);

            relativeLayout.setOnClickListener(view -> {
                int position = getAdapterPosition();
                toggleItemSelected(position);
            });

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDays() {
        LocalDate localDate = LocalDate.now();
        int i = 365;

        while (i>0) {
            days.add(localDate.minusDays(i));
            i--;
        }
    }

    private void toggleItemSelected(int position) {

        if (mSelectedItems.get(position, false) == true) {
            mSelectedItems.delete(position);
            notifyItemChanged(position);
        } else {
            mSelectedItems.put(position, true);
            notifyItemChanged(position);
        }
    }

    private boolean isItemSelected(int position) {
        return mSelectedItems.get(position, false);
    }

    public void clearSelectedItem() {
        int position;

        for (int i = 0; i < mSelectedItems.size(); i++) {
            position = mSelectedItems.keyAt(i);
            mSelectedItems.put(position, false);
            notifyItemChanged(position);
        }

        mSelectedItems.clear();
    }
}
