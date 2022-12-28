package com.mpas.mvp.management;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mvp.R;
import com.mpas.mvp.management.ui.payments.PayFactoryActivity;
import com.mpas.mvp.management.ui.scanner.ScanActivity;

import java.util.ArrayList;

public class PayItemAdapter extends RecyclerView.Adapter<PayItemAdapter.ViewHolder>{

    ArrayList<PaymentsFactory.Item> itemArrayList;
    Context mContext;
    PaymentsFactory factory = new PaymentsFactory();


    public PayItemAdapter(String type) {
        if (type.equals("first")) {
            itemArrayList = factory.getCredit_items();
        } else if (type.equals("second")) {
            itemArrayList = factory.getLink_items();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.payment_factory_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return itemArrayList.size();
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
                            Intent intent = new Intent(mContext, PayFactoryActivity.class);
                            mContext.startActivity(intent);
                            break;
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
