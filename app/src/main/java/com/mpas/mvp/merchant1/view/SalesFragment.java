package com.mpas.mvp.merchant1.view;

import static com.mpas.mvp.merchant1.util.TextConvert.toPrice;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.SalesFragmentBinding;
import com.mpas.mvp.merchant1.model.SalesDetailModel;

import java.util.List;

public class SalesFragment extends Fragment {

    private static final String TAG = SalesFragment.class.getSimpleName();
    private List<SalesDetailModel.SalesDetailDB> data;
    private  SalesViewModel viewModel;
    private SalesFragmentBinding binding;

    public SalesFragment(){
        Log.e(TAG,"SalesFragment()");

    }

    public static SalesFragment newInstance() {
        return new SalesFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.sales_fragment, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SalesViewModel.class);

        binding.salesDetailRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));

        viewModel.getSaleDetailDbMutableLiveData().observe(requireActivity(), db->{
            binding.salesDetailRecyclerview.setAdapter( new SaleDetailRecyclerViewAdapter(db));
        });

        viewModel.getSalesSumPriceMutableLiveData().observe(requireActivity(), price ->{
            binding.salesSumPrice.setText(toPrice(price));
        });

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        viewModel.getSale_purchase();
    }
}