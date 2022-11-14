package com.mpas.mvp.merchant1.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.mpas.mvp.R;

public class ManamodiActivity extends AppCompatActivity {

    private static NavController navController;
    private static MerchantViewModel merchantViewModel;
    private static SalesViewModel salesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manamodi);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        merchantViewModel = new ViewModelProvider(this).get(MerchantViewModel.class);
        salesViewModel = new ViewModelProvider(this).get(SalesViewModel.class);

    }

    public static MerchantViewModel getMerchantViewModel() {
        return merchantViewModel;
    }

    public static SalesViewModel getSalesViewModel() {
        return salesViewModel;
    }

}