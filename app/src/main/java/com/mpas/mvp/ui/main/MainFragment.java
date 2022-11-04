package com.mpas.mvp.ui.main;

import static com.mpas.mvp.databinding.MainFragmentBinding.bind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.mpas.mvp.MainActivity;
import com.mpas.mvp.OnBackPressedListener;
import com.mpas.mvp.R;
import com.mpas.mvp.barcode;
import com.mpas.mvp.databinding.MainFragmentBinding;
import com.mpas.mvp.merchant1.view.ManagementActivity;
import com.mpas.mvp.merchant1.view.SalesActivity;
import com.mpas.mvp.merchant1.view.TestActivity;
import com.mpas.mvp.ui.main.cpm.CpmActivity;

public class MainFragment extends Fragment implements OnBackPressedListener {

    private static final String TAG = MainFragment.class.getSimpleName();

    private MainActivity mainActivity;
    private MainFragmentBinding binding;
    private boolean back;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.e(TAG,"Fragment Create");
        back = false;
        binding = bind(inflater.inflate(R.layout.main_fragment, container, false));

        binding.btCpm.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(),CpmActivity.class));
        });

        binding.btMpm.setOnClickListener(view -> {
            IntentIntegrator qrScan = new IntentIntegrator(getActivity());
            qrScan.setPrompt("Barcode Scanner");
            qrScan.setRequestCode(100);
            qrScan.setOrientationLocked(false);
            qrScan.setCaptureActivity(barcode.class);
            qrScan.initiateScan();
        });


        binding.biLSymbol.setOnClickListener(view -> {
            //Intent intent = new Intent(getActivity(), MerchantActivity.class);
            Intent intent = new Intent(getActivity(), ManagementActivity.class);
            //Intent intent = new Intent(getActivity(), TestActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if ( context instanceof Activity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }


    @Override
    public void onBackPressed() {
        mainActivity.fragmentChange(1,new Bundle());
    }
}