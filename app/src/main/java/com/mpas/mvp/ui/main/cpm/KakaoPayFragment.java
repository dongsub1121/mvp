package com.mpas.mvp.ui.main.cpm;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.KakaoFragmentBinding;

public class KakaoPayFragment extends Fragment {

    private CPMViewModel mViewModel;
    private KakaoFragmentBinding binding;

    public static KakaoPayFragment newInstance() {
        return new KakaoPayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.kakao_fragment,container,false);
        mViewModel = new ViewModelProvider(this).get(CPMViewModel.class);
        mViewModel.getBarcodeData("KaKao_Money");

        binding.btCredit.setOnClickListener(view -> {
            mViewModel.getBarcodeData("KaKao_Credit");
            Toast.makeText(requireActivity(), "I'm' KaKao_Credit.", Toast.LENGTH_SHORT).show();
        });

        binding.btMoney.setOnClickListener(view -> {
            mViewModel.getBarcodeData("KaKao_Money");
            Toast.makeText(requireActivity(), "I'm' KaKao_Money", Toast.LENGTH_SHORT).show();
        });


        mViewModel.getBcdLiveData().observe(requireActivity(),bcd -> {
            binding.ivBcdGenerate.setImageBitmap(bcd);
        });

        mViewModel.getQrLiveData().observe(requireActivity(),qr -> {
            binding.ivQrGenerate.setImageBitmap(qr);
        });

        return binding.getRoot();
    }

}