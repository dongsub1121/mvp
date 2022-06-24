package com.mpas.mvp.merchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.ActivityManageBinding;
import com.mpas.mvp.merchant.view.ModelInfoFragment;

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
                fragmentChange(1,new Bundle());
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

        Objects.requireNonNull(fragment).setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(binding.recyclerViewLayout.getId(), fragment)
                .commit();
    }


}