package com.mpas.mvp.management.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentSettingMerchantBinding;
import com.mpas.mvp.management.ui.sales.MerchantViewModel;

import java.util.Objects;

public class SettingMerchantFragment extends Fragment {

    private static MerchantViewModel merchantViewModel;
    private  static  SettingActivity settingActivity;
    private FragmentSettingMerchantBinding binding;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        settingActivity = (SettingActivity) getActivity();
    }

    public static SettingMerchantFragment newInstance() {
        SettingMerchantFragment fragment = new SettingMerchantFragment();

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_setting_merchant);
        merchantViewModel = SettingActivity.getMerchantViewModel();

        binding.downButton.setOnClickListener(view->{
            String biz = Objects.requireNonNull(binding.businessIdText.getText()).toString();
            String mid = Objects.requireNonNull(binding.merchantIdText.getText()).toString();

            Log.e("download",biz+" ::: "+mid);
            //merchantViewModel.MerchantDownLoad(biz, mid);
            merchantViewModel.checkMerchant(biz, mid);
        });

        merchantViewModel.getMessage().observe(getViewLifecycleOwner(), msg->{
            binding.notificationMessage.setText(msg);
        });

        merchantViewModel.getErrorMessage().observe(getViewLifecycleOwner(), msg ->{
            binding.notificationMessage.setText(msg);
        });

        merchantViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), msg ->{
            binding.notificationMessage.setText(msg);
        });

        return  inflater.inflate(R.layout.fragment_setting_merchant, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.resultText.setText("");
    }
}