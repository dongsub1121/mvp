package com.mpas.mvp.merchant1.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.mpas.mvp.R;

public class ManagementActivity extends AppCompatActivity {

    private static NavController navController;
    private static MerchantViewModel merchantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        merchantViewModel = new ViewModelProvider(this).get(MerchantViewModel.class);

    }

    public static MerchantViewModel getViewModel() {
        return merchantViewModel;
    }
    public static void goFragment(int n){

        switch (n){
            case 0:
                navController.navigate(R.id.action_MerchantFragment_to_SalesSummaryFragment);
                break;
            case 1:
                navController.navigate(R.id.action_SalesSummaryFragment_to_SalesFragment);
                break;
        }

    }
}