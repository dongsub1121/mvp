package com.mpas.mvp.merchant.view;

import static com.mpas.mvp.merchant.model.MerchantInfoModel.Result.*;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mvp.R;
import java.util.List;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.BankHolder> {

    private static final String TAG = BanksAdapter.class.getSimpleName();

    List<Banks> banks ;

    public BanksAdapter(List<Banks> banks ) {
        Log.e(TAG,"BanksAdapter");
        this.banks = banks;
    }

    @NonNull
    @Override
    public BanksAdapter.BankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG,"onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_list,parent,false);
        return new BankHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BanksAdapter.BankHolder holder, int position) {
        Log.e(TAG,"onBindViewHolder");
        holder.name.setText(banks.get(position).getBankname());
        holder.businessNumber.setText(banks.get(position).getMerchantnumber());
        holder.fiCode.setText(banks.get(position).getFicode());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BankHolder extends RecyclerView.ViewHolder{

        TextView name,fiCode,businessNumber;

        public BankHolder(@NonNull View itemView) {

            super(itemView);
            Log.e(TAG,"BankHolder");
            name = itemView.findViewById(R.id.info_name);
            fiCode = itemView.findViewById(R.id.info_code);
            businessNumber = itemView.findViewById(R.id.info_business_number);
        }
    }
}
