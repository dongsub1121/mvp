package com.mpas.mvp.merchant.view;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import com.mpas.mvp.merchant.model.BanksModel;

import java.util.ArrayList;
import java.util.List;


public class ModelInfoFragment extends Fragment implements OnBackPressedListener {

    private static final String TAG = ModelInfoFragment.class.getSimpleName();
    private static ManagementViewModel mViewModel;
    @SuppressLint("StaticFieldLeak")
    private static ManagementFragmentBinding binding;
    private static ManageActivity manageActivity;

    public static ModelInfoFragment newInstance() {
        return new ModelInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "Fragment Create");

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


/*        List<BanksModel> list = new ArrayList<>();
        list.add(new BanksModel("8080","제로페이","1234573830"));
        list.add(new BanksModel("8080","제로페이","1234573830"));
        list.add(new BanksModel("8080","제로페이","1234573830"));
        RecyclerView recyclerView = binding.banksRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(list);
        recyclerView.setAdapter(mAdapter);*/

      mViewModel.getBanksMutableLiveData().observe(manageActivity,banks->{
           Log.e(TAG,"getBanksMutableLiveData");

           RecyclerView recyclerView = binding.banksRecyclerView;
           List<BanksModel> list = new ArrayList<>();
           list.addAll(banks);
           recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
           RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(list);
           recyclerView.setAdapter(mAdapter);
        });
    }


    @Override
    public void onBackPressed() {
        manageActivity.fragmentChange(1,new Bundle());
    }
}