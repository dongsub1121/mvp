package com.mpas.mvp.management;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpas.mvp.databinding.FragmentPayHomeBinding;
import com.mpas.mvp.management.ui.sales.MerchantViewModel;
import com.mpas.mvp.management.ui.settings.SettingActivity;

public class PayHomeFragment extends Fragment {

    private FragmentPayHomeBinding binding;
    private static MerchantViewModel viewModel;

    public PayHomeFragment() {
        // Required empty public constructor
    }

    public static PayHomeFragment newInstance() {
        return new PayHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPayHomeBinding.inflate(inflater,container,false);
        //viewModel = ManagementActivity.getMerchantViewModel();
        viewModel = new ViewModelProvider(requireActivity()).get(MerchantViewModel.class);

        RecyclerView firstRecycler = binding.firstPaysRecyclerView;
        RecyclerView secondRecycler = binding.secondPaysRecyclerView;

        PayItemAdapter firstAdapter = new PayItemAdapter("first");
        PayItemAdapter secondAdapter = new PayItemAdapter("second");

        firstRecycler.setAdapter(firstAdapter); // Credit
        secondRecycler.setAdapter(secondAdapter); // Link

        binding.imgSetting.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), SettingActivity.class));
        });

        viewModel.getMasterMerchant().observe(getViewLifecycleOwner(), merchantEntity -> {
            if(merchantEntity != null) {
                Log.e("home:getMasterMerchant",merchantEntity.getSitename());
                binding.merchantTitle.setText(merchantEntity.getSitename());
            }else {
                binding.merchantTitle.setText("가맹점이 없어요");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        viewModel.refreshMerchant();
    }
}