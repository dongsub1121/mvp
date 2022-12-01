package com.mpas.mvp.merchant1.view;

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
import com.mpas.mvp.management.SettingActivity;
import com.mpas.mvp.management.ui.sales.MerchantViewModel;
import com.mpas.mvp.merchant1.repository.MerchantEntity;
import com.mpas.mvp.merchant1.repository.MerchantFactory;
import java.util.List;


public class MerchantRecyclerViewAdapter extends RecyclerView.Adapter<MerchantRecyclerViewAdapter.ViewHolder>{

    //private final List<String> merchantList;
    //private final List<String> merchantListDetail;
    private final List<MerchantEntity> merchantEntities;
    private static MerchantViewModel viewModel;

    public MerchantRecyclerViewAdapter(List<MerchantEntity> entityList) {
        //this.merchantList = factory.getKetSet();
        //this.merchantListDetail = factory.getDetail();
        Log.e("MerchantRecyclerViewAdapter","MerchantRecyclerViewAdapter");
        merchantEntities = entityList;
        viewModel = SettingActivity.getMerchantViewModel();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
        /*Log.e("MerchantAdapter","onBindViewHolder");
        String name = merchantList.get(position);
        Log.e("MerchantAdapter",name);
        String info = merchantListDetail.get(position);

        holder.title.setText(name);
        holder.info.setText(info);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return merchantEntities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //TextView title , info;
        TextView neonSign, neonSign_biz, neonSign_mid, neonSign_address;
        Button setMain , edit, delete;
        RelativeLayout editLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            neonSign = itemView.findViewById(R.id.neonSign);
            neonSign_biz = itemView.findViewById(R.id.neonSign_biz);
            neonSign_mid = itemView.findViewById(R.id.neonSign_mid);
            neonSign_address = itemView.findViewById(R.id.neonSign_address);

            setMain = itemView.findViewById(R.id.setMain);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            editLayout = itemView.findViewById(R.id.editLayout);

            edit.setOnClickListener(view -> {

                if(editLayout.getVisibility() == View.GONE) {
                    editLayout.setVisibility(View.VISIBLE);
                }else {
                    editLayout.setVisibility(View.GONE);
                }

            });

            setMain.setOnClickListener(view -> {
                viewModel.SetMerchant(neonSign.getText().toString());
            });

            delete.setOnClickListener(view -> {
                viewModel.deleteSingle(neonSign.getText().toString());
            });
        }
    }

}
