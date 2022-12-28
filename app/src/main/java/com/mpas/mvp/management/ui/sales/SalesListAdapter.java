package com.mpas.mvp.management.ui.sales;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mvp.R;
import com.mpas.mvp.merchant1.util.TextConvert;

import java.util.List;

public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ListHolder>{

    private final List<String> list;

    public SalesListAdapter(List<String> item) {
        this.list = item;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay_list_apdapter,parent,false);

        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

        String price = list.get(position);
        if(price.charAt(0) == '8') {
            holder.payments.setVisibility(View.GONE);
            holder.oderPrice.setText(TextConvert.toPrice(price));
            holder.status.setText("배달");
            holder.statusImage.setImageResource(R.drawable.status_wait);
        }else if (price.charAt(0) == '7') {
            holder.order.setVisibility(View.GONE);
            holder.issuer.setText("010 - **** - 2291");
            holder.authNum.setText("KAKAO TALK");
            holder.paymentMenu.setText("크리스마스 커플 세트");
            holder.price.setText(TextConvert.toPrice(price));
            holder.status.setText("진행중");
            holder.main.setBackgroundColor(Color.parseColor("#FFEFEF"));
        } else if (price.charAt(0) == '6') {
            holder.payments.setVisibility(View.GONE);
            holder.oderPrice.setText(TextConvert.toPrice(price));
            holder.address.setText("POS Order");
            holder.request.setText("Table  No.4         Order No.1012");
            holder.status.setText("대기");
            holder.statusImage.setImageResource(R.drawable.status_wait_2);
        } else {
            holder.order.setVisibility(View.GONE);
            holder.price.setText(TextConvert.toPrice(price));
            holder.statusImage.setImageResource(R.drawable.status_done);
            holder.main.setBackgroundColor(Color.parseColor("#E7E7E7"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListHolder extends RecyclerView.ViewHolder {

        TextView price, oderPrice, address, menu, request, issuer, authNum, paymentMenu;
        RelativeLayout payments, order, main ;
        TextView status;
        ImageView statusImage;


        public ListHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.price);
            oderPrice = itemView.findViewById(R.id.orderPrice);
            address = itemView.findViewById(R.id.address);
            menu = itemView.findViewById(R.id.menu);
            request = itemView.findViewById(R.id.request);
            issuer = itemView.findViewById(R.id.issuer);
            authNum = itemView.findViewById(R.id.authNum);
            paymentMenu = itemView.findViewById(R.id.paymentMenu);

            payments = itemView.findViewById(R.id.paymentLayout);
            order = itemView.findViewById(R.id.orderLayout);
            main = itemView.findViewById(R.id.mainLayout);

            status = itemView.findViewById(R.id.status);
            statusImage = itemView.findViewById(R.id.statusImage);
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
