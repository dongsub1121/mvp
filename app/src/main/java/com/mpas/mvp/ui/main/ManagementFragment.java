package com.mpas.mvp.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpas.mvp.MainActivity;
import com.mpas.mvp.OnBackPressedListener;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.MainFragmentBinding;
import com.mpas.mvp.databinding.ManagementFragmentBinding;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Objects;

public class ManagementFragment extends Fragment implements OnBackPressedListener {

    private ManagementViewModel mViewModel;
    private ManagementFragmentBinding binding;
    private MainActivity mainActivity;

    public static ManagementFragment newInstance() {
        return new ManagementFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if ( context instanceof Activity){
            mainActivity = (MainActivity) context;
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.management_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ManagementViewModel.class);

        // 가맹점 다운로드
        binding.btMerchantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String biz = Objects.requireNonNull(binding.textInputBusiness.getText()).toString();
                String tid = Objects.requireNonNull(binding.textInputTid.getText()).toString();
                ManagementViewModel.setBusinessID(biz);
                ManagementViewModel.setMerchantID(tid);
                mViewModel.getMerchantInfo();

            }
        });

        //발급사 가맹점 번호 조회
        binding.btInfoQueryIssuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fiCode = Objects.requireNonNull(binding.textInputFicode.getText()).toString();
                String biz = Objects.requireNonNull(binding.textInputBusiness.getText()).toString();
                String tid = Objects.requireNonNull(binding.textInputTid.getText()).toString();
                ManagementViewModel.setBusinessID(biz);
                ManagementViewModel.setMerchantID(tid);
                ManagementViewModel.setFiCode(fiCode);
                mViewModel.getBankInfo();
            }
        });

        binding.btIQueryISales.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                String biz = Objects.requireNonNull(binding.textInputBusiness.getText()).toString();
                String tid = Objects.requireNonNull(binding.textInputTid.getText()).toString();
                int year , month, toDay, startDay = 0;

                ManagementViewModel.setBusinessID(biz);
                ManagementViewModel.setMerchantID(tid);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate currDate = LocalDate.now();
                    year = currDate.getYear();
                    Month mon = currDate.getMonth();
                    month = mon.getValue();
                    toDay = currDate.getDayOfMonth();

                    if ( toDay > 2){
                        startDay = toDay-1;
                    }else if( toDay <= 1){
                        startDay = toDay;
                    }

                    ManagementViewModel.setStartDate(String.format("%04d%02d%02d",year, month,startDay));
                    ManagementViewModel.setEndDate(String.format("%04d%02d%02d",year, month,toDay));

                }
                mViewModel.getQueryISales();
            }
        });

        binding.btIQueryIPurchase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                String biz = Objects.requireNonNull(binding.textInputBusiness.getText()).toString();
                String tid = Objects.requireNonNull(binding.textInputTid.getText()).toString();

                ManagementViewModel.setBusinessID(biz);
                ManagementViewModel.setMerchantID(tid);
                int year , month, toDay, startDay = 0;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate currDate = LocalDate.now();
                    year = currDate.getYear();
                    Month mon = currDate.getMonth();
                    month = mon.getValue();
                    toDay = currDate.getDayOfMonth();

                    if ( toDay > 2){
                        startDay = toDay-1;
                    }else if( toDay <= 1){
                        startDay = toDay;
                    }

                    ManagementViewModel.setEndDate(String.format("%04d%02d%02d",year, month,toDay));

                }

                mViewModel.getQueryISalesPurchase();
            }
        });

        binding.btIQueryIPurchaseDetail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                String biz = Objects.requireNonNull(binding.textInputBusiness.getText()).toString();
                String tid = Objects.requireNonNull(binding.textInputTid.getText()).toString();

                int year , month, toDay, startDay = 0;

                ManagementViewModel.setBusinessID(biz);
                ManagementViewModel.setMerchantID(tid);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate currDate = LocalDate.now();
                    year = currDate.getYear();
                    Month mon = currDate.getMonth();
                    month = mon.getValue();
                    toDay = currDate.getDayOfMonth();

                    if ( toDay > 2){
                        startDay = toDay-1;
                    }else if( toDay <= 1){
                        startDay = toDay;
                    }

                    ManagementViewModel.setEndDate(String.format("%04d%02d%02d",year, month,toDay));
                }

                mViewModel.getQueryISalesPurchaseDetail();
            }
        });

        mViewModel.getMerchantInfoMutableLiveData().observe(getViewLifecycleOwner(),merchantInfo -> {

        });
    }

    @Override
    public void onBackPressed() {
        mainActivity.fragmentChange(1,new Bundle());
    }
}