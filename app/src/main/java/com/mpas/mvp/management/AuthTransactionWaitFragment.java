package com.mpas.mvp.management;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mpas.mvp.databinding.FragmentAuthTransactionWaitBinding;


public class AuthTransactionWaitFragment extends Fragment {

    private FragmentAuthTransactionWaitBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAuthTransactionWaitBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

}