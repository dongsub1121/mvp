package com.mpas.mvp.merchant.view;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpas.mvp.OnBackPressedListener;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.ManagementFragmentBinding;
import com.mpas.mvp.merchant.ManageActivity;
import com.mpas.mvp.merchant.ManagementViewModel;
import com.mpas.mvp.merchant.model.MerchantInfoModel;

import java.util.ArrayList;


public class ModelInfoFragment extends Fragment implements OnBackPressedListener {

    private static final String TAG = ModelInfoFragment.class.getSimpleName();
    private static ManagementViewModel mViewModel;
    private static ManagementFragmentBinding binding;
    private static ManageActivity manageActivity;
    private static RecyclerView recyclerView;
    private static BanksAdapter mAdapter;

    public static ModelInfoFragment newInstance() {
        return new ModelInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.management_fragment,container,false);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if ( context instanceof Activity){
            manageActivity = (ManageActivity) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(manageActivity).get(ManagementViewModel.class);
        mViewModel.refresh();

       mViewModel.getMerchantInfoMutableLiveData().observe(manageActivity,info->{
           Log.e(TAG,"todo");

           binding.banksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
           //mAdapter = new MerchantInfoAdapter(manageActivity,info.getBanks());
           mAdapter = new BanksAdapter(info.getBanks());
           Log.e("dddddddddddddd", String.valueOf(info.getBanks()));
           binding.banksRecyclerView.setAdapter(mAdapter);
        });
    }

    @Override
    public void onBackPressed() {
        manageActivity.fragmentChange(1,new Bundle());
    }
}