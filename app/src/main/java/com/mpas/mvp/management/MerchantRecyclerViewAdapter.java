package com.mpas.mvp.management;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import com.mpas.mvp.R;
import com.mpas.mvp.management.ui.sales.MerchantViewModel;
import com.mpas.mvp.management.ui.settings.SettingActivity;
import com.mpas.mvp.merchant1.repository.MerchantEntity;

import java.util.List;


public class MerchantRecyclerViewAdapter extends RecyclerView.Adapter<MerchantRecyclerViewAdapter.ViewHolder>{

    private  List<MerchantEntity> merchantEntities;
    private static MerchantViewModel viewModel;

    public MerchantRecyclerViewAdapter() {
        viewModel = SettingActivity.getMerchantViewModel();
    }

    @NonNull
    @Override
    public MerchantRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.merhcant_adapter_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("MerchantAdapter","onBindViewHolder");
        MerchantEntity entity = merchantEntities.get(position);
        holder.neonSign.setText(entity.getSitename());
        holder.neonSign_address.setText(String.format("주소 : %s" ,entity.getSiteaddress()));
        holder.neonSign_biz.setText(String.format("사업자 번호 : %s" ,entity.getBusinessNo()));
        holder.neonSign_mid.setText(String.format("나이스 페이먼츠 ID : %s" ,entity.getMerchantNo()));

        holder.edit.setOnClickListener(view -> {

            if(holder.editLayout.getVisibility() == View.GONE) {
                holder.editLayout.setVisibility(View.VISIBLE);
            }else {
                holder.editLayout.setVisibility(View.GONE);
            }

        });

        holder.setMain.setOnClickListener(view -> {
            //viewModel.SetMerchant(neonSign.getText().toString());
            //TODO Room 재설계 , 다운로드 후 room 저장 , 첫번째 다운로드 자동 master
            //마스터 세팅 및 가맹점 정보 호출
            //viewModel.setMaster(holder.neonSign.getText().toString());
            viewModel.setMaster(entity);
        });

        holder.delete.setOnClickListener(view -> {
            viewModel.deleteMerchant(holder.neonSign.getText().toString());
        });
    }

    public void setMerchantEntities(List<MerchantEntity> entityList) {
        merchantEntities = entityList;
    }

    @Override
    public int getItemCount() {
        if(merchantEntities == null) {
            return 0;
        } else {
            return merchantEntities.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //TextView title , info;
        TextView neonSign, neonSign_biz, neonSign_mid, neonSign_address;
        Button setMain , edit, delete;
        RelativeLayout editLayout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            Log.e("ViewHolder","ViewHolder");
            neonSign = itemView.findViewById(R.id.neonSign);
            neonSign_biz = itemView.findViewById(R.id.neonSign_biz);
            neonSign_mid = itemView.findViewById(R.id.neonSign_mid);
            neonSign_address = itemView.findViewById(R.id.neonSign_address);

            setMain = itemView.findViewById(R.id.setMain);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            editLayout = itemView.findViewById(R.id.editLayout);

        }
    }

}
