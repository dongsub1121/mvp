package com.mpas.mvp.merchant1.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class MerchantFragment extends Fragment {

    private  static FragmentMerchantBinding binding;
    private static MerchantViewModel mViewModel;
    private static MerchantActivity merchantActivity;

    public static MerchantFragment newInstance() {
        return new MerchantFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
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

        binding = DataBindingUtil.setContentView(merchantActivity,R.layout.fragment_merchant);
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