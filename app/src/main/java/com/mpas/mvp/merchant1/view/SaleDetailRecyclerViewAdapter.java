package com.mpas.mvp.merchant1.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mvp.R;
import com.mpas.mvp.merchant1.model.BanksModel;
import com.mpas.mvp.merchant1.model.SalseDetailModel;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;

public class SaleDetailRecyclerViewAdapter extends  RecyclerView.Adapter<SaleDetailRecyclerViewAdapter.ViewHoler>{
    private final List<SalseDetailModel.SalesDetailDB> salesDetailDBList;

    public SaleDetailRecyclerViewAdapter(List<SalseDetailModel.SalesDetailDB> list) {
        this.salesDetailDBList = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_detail_item,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        //TODO
        SalseDetailModel.SalesDetailDB db  = salesDetailDBList.get(position);
        holder.amount.setText(MessageFormat.format("{0}원", toPrice(db.getTramt())));
        holder.count.setText(MessageFormat.format("{0}건", db.getTrcnt()));
        holder.bank.setText(db.getBankname());

    }

    @Override
    public int getItemCount() {
        return salesDetailDBList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView bank, amount, count;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.sales_detail_ci);
            bank = itemView.findViewById(R.id.sales_detail_bank);
            count = itemView.findViewById(R.id.sales_detail_count);
            amount = itemView.findViewById(R.id.sales_detail_amount);
        }
    }

    public String toPrice(String num) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return  numberFormat.format(Integer.valueOf(num));
    }
}
