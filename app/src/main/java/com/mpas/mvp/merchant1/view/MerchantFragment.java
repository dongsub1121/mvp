package com.mpas.mvp.merchant1.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import com.mpas.mvp.merchant1.util.Config;
import com.mpas.mvp.merchant1.model.BanksModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MerchantFragment extends Fragment {

    private static final String TAG = MerchantFragment.class.getSimpleName();
    private  static FragmentMerchantBinding binding;
    private static MerchantViewModel mViewModel;

    public static MerchantFragment newInstance() {
        return new MerchantFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.e(TAG,"onAttach");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_merchant, container,false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MerchantViewModel.class);

        binding.downButton.setOnClickListener(view -> {
            mViewModel.getMerchant(Objects.requireNonNull(binding.businessIdText.getText()).toString(),
                    Objects.requireNonNull(binding.merchantIdText.getText()).toString());

        });

        mViewModel.getBanksMutableLiveData().observe(requireActivity(),banks ->{
            RecyclerView recyclerView = binding.banksRecyclerView;
            List<BanksModel> list = new ArrayList<>();
            list.addAll(banks);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(list);
            recyclerView.setAdapter(mAdapter);
             });

        mViewModel.getSharedPreferencesMutableLiveData().observe(requireActivity(),sharedPreferences -> {
            binding.merchantOwner.setText(sharedPreferences.getString(Config.MERCHANT_OWNER,""));
            binding.merchantAddress.setText(sharedPreferences.getString(Config.MERCHANT_ADDRESS,""));
            binding.merchantPhone.setText(sharedPreferences.getString(Config.MERCHANT_PHONE,""));
            binding.merchantName.setText(sharedPreferences.getString(Config.MERCHANT_NAME,""));
        });
        return binding.getRoot();
    }

}