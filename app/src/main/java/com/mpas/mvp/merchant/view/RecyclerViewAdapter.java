package com.mpas.mvp.merchant.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mvp.R;
import com.mpas.mvp.merchant.model.BanksModel;

import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<BanksModel> banksModelList;

    public RecyclerViewAdapter(List<BanksModel> list) {
        this.banksModelList = list;
        Log.e("RecyclerViewAdapter",String.valueOf(list.size())+"_"+String.valueOf(banksModelList.size()));

    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.info_list, parent, false);
        Log.e("RecyclerViewAdapter","onCreateViewHolder");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder,  int position) {
        Log.e("onBindViewHolder", String.valueOf(position));

        String name = banksModelList.get(position).getBankname();
        String code = banksModelList.get(position).getFicode();
        String mid = banksModelList.get(position).getMerchantnumber();
        Log.e("onBindViewHolder",name+"_"+code+"_"+mid);

        holder.info_business_number.setText(banksModelList.get(position).getMerchantnumber());
        holder.info_name.setText(banksModelList.get(position).getBankname());
        holder.info_code.setText(banksModelList.get(position).getFicode());

    }

    @Override
    public int getItemCount() {
        return banksModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView info_name,info_code,info_business_number;

        public MyViewHolder(View view) {
            super(view);
            Log.e("RecyclerViewAdapter","MyViewHolder");
            info_name = view.findViewById(R.id.info_name);
            info_code = view.findViewById(R.id.info_code);
            info_business_number = view.findViewById(R.id.info_business_number);
        }

    }

}