package com.mpas.mvp.merchant1.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentMerchantBinding;
import com.mpas.mvp.merchant.ManageActivity;
import com.mpas.mvp.merchant.ManagementViewModel;
import com.mpas.mvp.merchant.model.BanksModel;
import com.mpas.mvp.merchant.view.ModelInfoFragment;
import com.mpas.mvp.merchant.view.RecyclerViewAdapter;
import com.mpas.mvp.merchant1.db.MerchantInfoData;
import com.mpas.mvp.merchant1.db.MerchantInfoDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MerchantFragment extends Fragment {

    private static final String TAG = MerchantFragment.class.getSimpleName();
    private  static FragmentMerchantBinding binding;
    private static MerchantViewModel mViewModel;
    private static MerchantActivity merchantActivity;

    public static MerchantFragment newInstance() {
        return new MerchantFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.e(TAG,"onAttach");
        super.onAttach(context);

        if ( context instanceof Activity){
            merchantActivity = (MerchantActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_merchant, container,false);
        mViewModel = new ViewModelProvider(merchantActivity).get(MerchantViewModel.class);

        binding.downButton.setOnClickListener(view -> {
            mViewModel.getMerchant(Objects.requireNonNull(binding.businessIdText.getText()).toString(),
                    Objects.requireNonNull(binding.merchantIdText.getText()).toString());

        });

        mViewModel.getBanksMutableLiveData().observe(requireActivity(),banks ->{
            RecyclerView recyclerView = binding.banksRecyclerView;
            List<BanksModel> list = new ArrayList<>();
            list.addAll(banks);
            recyclerView.setLayoutManager(new LinearLayoutManager(merchantActivity));
            RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(list);
            recyclerView.setAdapter(mAdapter);
             });

        return binding.getRoot();
    }

}