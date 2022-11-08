package com.mpas.mvp.merchant1.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mvp.R;
import com.mpas.mvp.merchant1.util.TextConvert;
import com.mpas.mvp.merchant1.model.SalesDetailModel;
import java.util.List;

public class SaleDetailRecyclerViewAdapter extends  RecyclerView.Adapter<SaleDetailRecyclerViewAdapter.ViewHoler>{
    private final List<SalesDetailModel.SalesDetailDB> salesDetailDBList;

    public SaleDetailRecyclerViewAdapter(List<SalesDetailModel.SalesDetailDB> list) {
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

        SalesDetailModel.SalesDetailDB db  = salesDetailDBList.get(position);
        holder.amount.setText(TextConvert.toPrice(db.getTramt()));
        holder.count.setText(TextConvert.toCount(db.getTrcnt()));
        holder.bank.setText(db.getBankname());
        holder.imageView.setImageResource(setDrawable(db.getBankname()));

    }

    @Override
    public int getItemCount() {
        return salesDetailDBList.size();
    }

    public static class ViewHoler extends RecyclerView.ViewHolder {
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

    private int setDrawable(String purchase) {

        int resId = 0;

        switch (purchase) {
            case "비씨":
                resId = R.drawable.bc;
                break;
            case "KB국민":
                resId = R.drawable.kb;
                break;
            case "NH농협":
                resId = R.drawable.nh;
                break;
            case "외환":
                resId = R.drawable.hana;
                break;
            case "삼성":
                resId = R.drawable.samsung;
                break;
            case "신한":
                resId = R.drawable.shinhan;
                break;
            case "현대":
                resId = R.drawable.hyundai;
                break;
            case "롯데":
                resId = R.drawable.lotte;
                break;
            case "제로페이":
                resId = R.drawable.zero;
                break;
            case "카카오페이 머니":
                resId = R.drawable.kakao;
                break;
            case "현금영수증":
                resId = R.drawable.koreatax;
                break;
            case "L.PAY":
                resId = R.drawable.mpas_symbol02;
            case "SSG페이":
                resId = R.drawable.mpas_symbol02;
                break;
        }
        return resId;
    }

}
