package com.mpas.mvp.management.ui.settings;

import android.content.Context;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentSettingMerchantHomeBinding;
import com.mpas.mvp.management.MerchantRecyclerViewAdapter;
import com.mpas.mvp.management.ui.sales.MerchantViewModel;

public class SettingMerchantHomeFragment extends Fragment {

    MerchantViewModel viewModel;
    FragmentSettingMerchantHomeBinding binding;
    MerchantRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SettingActivity settingActivity;
    NavController navController;

    public static SettingMerchantHomeFragment newInstance() {
        SettingMerchantHomeFragment fragment = new SettingMerchantHomeFragment();

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        settingActivity = (SettingActivity) getActivity();
        navController = SettingActivity.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = SettingActivity.getMerchantViewModel();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_merchant_home,container,false);

        viewModel.getMerchantAll().observe(getViewLifecycleOwner(), entityList -> {

            recyclerView = binding.recyclerViewMerchantDetail;
            adapter = new MerchantRecyclerViewAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(settingActivity));
            recyclerView.setAdapter(adapter);

            if(entityList == null) {
                binding.statusMessageLayout.setVisibility(View.GONE);
                binding.statusMessageCaution.setVisibility(View.GONE);
                binding.initMessage.setVisibility(View.VISIBLE);

            }else {
                if(entityList.size() == 0) {
                    binding.statusMessageLayout.setVisibility(View.GONE);
                    binding.statusMessageCaution.setVisibility(View.GONE);
                    binding.initMessage.setVisibility(View.VISIBLE);
                } else {
                    binding.initMessage.setVisibility(View.GONE);
                    adapter.setMerchantEntities(entityList);
                    //binding.statusMessageCaution.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getMasterMerchant().observe(getViewLifecycleOwner(), merchantEntity -> {

            String msg;
            String caution;

            if(merchantEntity == null) {
                binding.statusMessageLayout.setVisibility(View.GONE);
                binding.statusMessageCaution.setVisibility(View.VISIBLE);
                binding.statusMessageCaution.setText("메인 가맹점을 선택해 주세요 \n 결제대상 가맹점이 없어요");


            } else {
                binding.statusMessageLayout.setVisibility(View.VISIBLE);
                binding.statusMessageCaution.setVisibility(View.GONE);
                binding.statusMerchant.setText(merchantEntity.getSitename());
            }
        });

        binding.addMerchant.setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction().replace(R.id.nav_host_settings, SettingMerchantFragment.newInstance()).addToBackStack("home").commit();
        });

        viewModel.getMerchantEntities();

        return binding.getRoot();
    }


}