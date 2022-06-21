package com.mpas.mvp.merchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.ActivityManageBinding;
import com.mpas.mvp.merchant.model.MerchantInfoModel;
import com.mpas.mvp.merchant.view.BanksAdapter;
import com.mpas.mvp.merchant.view.ModelInfoFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ManageActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static ActivityManageBinding binding;
    private static ManagementViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage);
        mViewModel = new ViewModelProvider(this).get(ManagementViewModel.class);

        binding.btMerchantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String biz = (Objects.requireNonNull(binding.textInputBusiness.getText())).toString();
                String mid = (Objects.requireNonNull(binding.textInputTid.getText())).toString();
                mViewModel.setMerchant(biz, mid);
                mViewModel.refresh();
                //fragmentChange(1,new Bundle());

                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                ArrayList<MerchantInfoModel.Result.Banks> banks = new ArrayList<>();
                MerchantInfoModel.Result.Banks banks1 = new MerchantInfoModel.Result.Banks();
                banks1.setBankname("JTNET");
                banks1.setFicode("8080");
                banks1.setMerchantnumber("1078155843");
                banks.add(banks1);
                banks.add(banks1);
                banks.add(banks1);
                Log.e("click",banks.toString());
                BanksAdapter adapter = new BanksAdapter(banks);
                binding.recyclerView.setAdapter(adapter);


            }
        });
    }

    public void fragmentChange(int status,Bundle bundle) {

        Fragment fragment = null;

        switch (status){
            case 1:
                fragment = ModelInfoFragment.newInstance();
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:
            case 5:

            default:
                break;
        }

/*        Objects.requireNonNull(fragment).setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(binding.fragmentLayout.getId(), fragment).commit();*/
    }

}