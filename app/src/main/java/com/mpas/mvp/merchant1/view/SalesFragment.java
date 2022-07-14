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
import com.mpas.mvp.databinding.SalesFfragmentBinding;
import com.mpas.mvp.databinding.SalesFragmentBinding;
import com.mpas.mvp.merchant1.model.SalesDetailModel;

import java.util.List;

public class SalesFragment extends Fragment {

    private static final String TAG = SalesFragment.class.getSimpleName();
    private List<SalesDetailModel.SalesDetailDB> data;
    private Integer tPrice;
    private SalesFragmentBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SalesFragment(Activity activity){
        Log.e(TAG,"SalesFragment()");

        if(activity != null ) {

            SalesViewModel mViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SalesViewModel.class);
            mViewModel.getSale_purchase();

            mViewModel.getSaleDetailDbMutableLiveData().observe((LifecycleOwner) activity, db->{
                data = db;
            });

            mViewModel.getSalesSumPriceMutableLiveData().observe((LifecycleOwner) activity, price ->{
                tPrice = price;
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static SalesFragment newInstance(Activity activity) {
        return new SalesFragment(activity);
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //com.mpas.mvp.databinding.SalesFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.sales_ffragment, container, false);
        //mViewModel = new ViewModelProvider(requireActivity()).get(SalesViewModel.class);
        binding = DataBindingUtil.inflate(inflater,R.layout.sales_fragment,container,false);

        binding.salesDetailRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.salesDetailRecyclerview.setAdapter( new SaleDetailRecyclerViewAdapter(data));
        binding.salesSumPrice.setText(toPrice(tPrice));

        return binding.getRoot();
    }
}