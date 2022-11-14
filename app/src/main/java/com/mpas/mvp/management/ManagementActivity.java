package com.mpas.mvp.management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.ActivityManagementBinding;
import com.mpas.mvp.management.ui.settings.SettingsFragment;
import com.mpas.mvp.merchant1.view.MerchantViewModel;
import com.mpas.mvp.merchant1.view.PaymentsFragment;
import com.mpas.mvp.merchant1.view.SalesFragment;
import com.mpas.mvp.merchant1.view.SalesLIstFragment;
import com.mpas.mvp.merchant1.view.SalesSummaryFragment;
import com.mpas.mvp.merchant1.view.SalesViewModel;

public class ManagementActivity extends AppCompatActivity {

    private static MerchantViewModel merchantViewModel;
    private static SalesViewModel salesViewModel;
    private static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        merchantViewModel = new ViewModelProvider(this).get(MerchantViewModel.class);
        salesViewModel = new ViewModelProvider(this).get(SalesViewModel.class);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(SalesFragment.newInstance(),"summary");
        fragmentTransaction.add(SalesFragment.newInstance(),"sales");

        //getSupportFragmentManager().beginTransaction().show(SalesSummaryFragment.newInstance()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                goPage(item);
                return false;
            }
        });
    }

    public static MerchantViewModel getMerchantViewModel() {
        return merchantViewModel;
    }

    public static SalesViewModel getSalesViewModel() {
        return salesViewModel;
    }

    @SuppressLint("NonConstantResourceId")
    private void goPage(MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.payment_category:
                fragment = SettingsFragment.newInstance();
                break;
            case R.id.manage_category:
                fragment = SalesSummaryFragment.newInstance();
                Log.e("go : ","SalesSummaryFragment");
                break;
            case R.id.sales_category:
                fragment = SalesLIstFragment.newInstance();
                Log.e("go : ","SalesLIstFragment");
                break;
            default:
                fragment = PaymentsFragment.newInstance();
                Log.e("go : ","PaymentsFragment");
        }

       getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.manage_frame,fragment).commit();
    }

    public void goSales() {
        getSupportFragmentManager().beginTransaction().addToBackStack("summary").replace(R.id.manage_frame,SalesFragment.newInstance()).commit();
    }

}