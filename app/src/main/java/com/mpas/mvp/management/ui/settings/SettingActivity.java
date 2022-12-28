package com.mpas.mvp.management.ui.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;

import com.mpas.mvp.R;
import com.mpas.mvp.management.ui.sales.MerchantViewModel;
import com.mpas.mvp.management.ui.sales.SalesViewModel;

public class SettingActivity extends AppCompatActivity {

    private static MerchantViewModel merchantViewModel;
    private static SalesViewModel salesViewModel;
    private static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("SettingsActivity","start");
        setContentView(R.layout.activity_setting);

        merchantViewModel = new ViewModelProvider(this).get(MerchantViewModel.class);
        salesViewModel = new ViewModelProvider(this).get(SalesViewModel.class);

        navController = Navigation.findNavController(this,R.id.nav_host_settings);

    }

    public static MerchantViewModel getMerchantViewModel() {
        return merchantViewModel;
    }
    public static SalesViewModel getSalesViewModel() {
        return salesViewModel;
    }
    public static NavController getNavController() {
        return navController;
    }

}