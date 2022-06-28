package com.mpas.mvp.merchant.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mvp.R;
import com.mpas.mvp.merchant.model.BanksModel;

import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private final List<BanksModel> banksModelList;

    public RecyclerViewAdapter(List<BanksModel> list) {
        this.banksModelList = list;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.info_list, parent, false);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.info_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder,  int position) {

        Log.e("viewAdapter","onBindViewHolder");
        String name = banksModelList.get(position).getBankname();
        Log.e("viewAdapter",name);
        String code = banksModelList.get(position).getFicode();
        Log.e("viewAdapter",code);
        String mid = banksModelList.get(position).getMerchantnumber();
        Log.e("viewAdapter",mid);


        holder.id.setText(mid);
        holder.name.setText(name);
        holder.code.setText(code);
        //holder.ci.setImageDrawable(R.drawable.mpas_symbol02);

    }

    @Override
    public int getItemCount() {
        return banksModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,code,id;
        ImageView ci;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.issuer_brand);
            code = itemView.findViewById(R.id.issuer_code);
            id = itemView.findViewById(R.id.issuer_id);
            ci = itemView.findViewById(R.id.ci);

        }
/*        TextView info_name,info_code,info_business_number;

        public MyViewHolder(View view) {
            super(view);
            info_name = view.findViewById(R.id.info_name);
            info_code = view.findViewById(R.id.info_code);
            info_business_number = view.findViewById(R.id.info_business_number);
        }*/

    }

}