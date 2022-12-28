package com.mpas.mvp.merchant1.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentMerchantBinding;
import com.mpas.mvp.management.ui.sales.MerchantViewModel;

import java.util.List;
import java.util.Objects;

public class MerchantFragment extends Fragment {

    private static final String TAG = MerchantFragment.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private  static FragmentMerchantBinding binding;
    private static MerchantViewModel mViewModel;
    private ArrayAdapter<String> stringArrayAdapter;

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
        binding.recyclerViewMerchantDetail.setLayoutManager(new LinearLayoutManager(requireActivity()));

        binding.downButton.setOnClickListener(view -> {
            mViewModel.checkMerchant(Objects.requireNonNull(binding.businessIdText.getText()).toString(),
                    Objects.requireNonNull(binding.merchantIdText.getText()).toString());

        });

        mViewModel.MerchantDownLoad().observe(requireActivity(), merchants->{
            List<String> strings = merchants.getKetSet();
            stringArrayAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_expandable_list_item_1, strings);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
            binding.merchantList.setAdapter(stringArrayAdapter);
        });

        binding.merchantList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("성공","성공");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mViewModel.MerchantDownLoad().observe(requireActivity(), merchant ->{
            //binding.recyclerViewMerchantDetail.setAdapter(new MerchantRecyclerViewAdapter(merchant));
        });



        return binding.getRoot();
    }


    @Override
    public void onResume() {
        //mViewModel.DeleteAll();
        //mViewModel.InitMerchantFactory();
        super.onResume();
    }
}