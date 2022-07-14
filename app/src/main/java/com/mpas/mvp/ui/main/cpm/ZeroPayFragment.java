package com.mpas.mvp.ui.main.cpm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentZeroPayBinding;
import com.mpas.mvp.databinding.KakaoFragmentBinding;

public class ZeroPayFragment extends Fragment {

    private FragmentZeroPayBinding binding;

    public static ZeroPayFragment newInstance() {  return new ZeroPayFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_zero_pay,container,false);
        CPMViewModel mViewModel = new ViewModelProvider(this).get(CPMViewModel.class);

        mViewModel.getBcdLiveData().observe(requireActivity(), bcd -> {
            binding.ivBcdGenerate.setImageBitmap(bcd);
        });

        mViewModel.getQrLiveData().observe(requireActivity(), qr -> {
            binding.ivQrGenerate.setImageBitmap(qr);
        });

        mViewModel.getBarcodeData("ZeroPay");

        return binding.getRoot();
    }
}