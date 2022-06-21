package com.mpas.mvp.merchant.view;

import static com.mpas.mvp.merchant.model.MerchantInfoModel.*;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mvp.R;
import com.mpas.mvp.merchant.model.MerchantInfoModel;

import java.util.ArrayList;
import java.util.List;

public class MerchantInfoAdapter extends RecyclerView.Adapter<MerchantInfoAdapter.BanksHolder> {

    private static final String TAG = MerchantInfoAdapter.class.getSimpleName();
    private static List<Result.Banks> mBanks = new ArrayList<>();
    private final Context mContext;

    public MerchantInfoAdapter(Context context, List<Result.Banks> banks) {
        mContext = context;
        mBanks = banks;
        Log.e(TAG,String.valueOf(banks));
    }

    @NonNull
    @Override
    public BanksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.info_list,parent,false);
        Log.e(TAG,"onCreateViewHolder");
        return new BanksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BanksHolder holder, int position) {
        final MerchantInfoModel.Result.Banks bank = mBanks.get(position);
        Log.e("getMerchantnumber()",bank.getMerchantnumber());
        holder.businessNumber.setText(bank.getMerchantnumber());
        holder.name.setText(bank.getBankname());
        holder.code.setText(bank.getFicode());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BanksHolder extends RecyclerView.ViewHolder{

        private final TextView name,code,businessNumber;

        public BanksHolder(@NonNull View itemView) {
            super(itemView);
            Log.e(TAG,"ViewHolder");
            name = itemView.findViewById(R.id.info_name);
            code = itemView.findViewById(R.id.info_code);
            businessNumber = itemView.findViewById(R.id.info_business_number);
        }
    }
}
