package com.mpas.mvp.merchant1.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mvp.R;
import com.mpas.mvp.merchant1.repository.MerchantFactory;
import java.util.List;


public class MerchantRecyclerViewAdapter extends RecyclerView.Adapter<MerchantRecyclerViewAdapter.ViewHolder>{

    private final List<String> merchantList;
    private final List<String> merchantListDetail;

    public MerchantRecyclerViewAdapter(MerchantFactory factory) {
        this.merchantList = factory.getKetSet();
        this.merchantListDetail = factory.getDetail();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("MerchantAdapter","onBindViewHolder");
        String name = merchantList.get(position);
        Log.e("MerchantAdapter",name);
        String info = merchantListDetail.get(position);

        holder.title.setText(name);
        holder.info.setText(info);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagementActivity.goFragment(0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return merchantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title , info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.merchant_name);
            info = itemView.findViewById(R.id.merchant_info);
        }
    }

}
