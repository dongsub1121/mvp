package com.mpas.mvp.management;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mvp.R;

import java.util.ArrayList;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {

    ArrayList<PaymentsFactory.Item> itemArrayList;
    Context mContext;
    PaymentsFactory factory = new PaymentsFactory();

    public PaymentsAdapter(String type) {
        Log.e("PaymentsAdapter", "PaymentsAdapter");
        if (type.equals("first")) {
            itemArrayList = factory.getCredit_items();
        } else if (type.equals("second")) {
            itemArrayList = factory.getLink_items();
        }

        Log.e("adapter",itemArrayList.get(0).getText());
    }

    @NonNull
    @Override
    public PaymentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("onCreateViewHolder", "onCreateViewHolder");
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.payment_factory_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.e("viewmodel", "viewModel");

            imageView = itemView.findViewById(R.id.factory_imageView);
            textView = itemView.findViewById(R.id.factory_TextView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (textView.getText().toString()) {
                        case "QR결제":
                            Toast.makeText(mContext, "QR결제", Toast.LENGTH_SHORT).show();
                        default:
                            Toast.makeText(mContext, "미지원 기능 입니다", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void onBind(int position) {
            imageView.setImageResource(itemArrayList.get(position).getResId());
            textView.setText(itemArrayList.get(position).getText());
        }
    }
}