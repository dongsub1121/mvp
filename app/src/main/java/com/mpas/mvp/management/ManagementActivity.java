package com.mpas.mvp.management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.ActivityManagementBinding;
import com.mpas.mvp.management.ui.liquid.LiquidViewModel;
import com.mpas.mvp.management.ui.settings.SettingsFragment;
import com.mpas.mvp.management.ui.sales.MerchantViewModel;
import com.mpas.mvp.merchant1.view.PaymentsFragment;
import com.mpas.mvp.management.ui.sales.SalesFragment;
import com.mpas.mvp.management.ui.sales.SalesLIstFragment;
import com.mpas.mvp.management.ui.sales.SalesSummaryFragment;
import com.mpas.mvp.management.ui.sales.SalesViewModel;

public class ManagementActivity extends AppCompatActivity {

    private static MerchantViewModel merchantViewModel;
    private static SalesViewModel salesViewModel;
    private static LiquidViewModel liquidViewModel;
    private  NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityManagementBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_management);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_management);
        merchantViewModel = new ViewModelProvider(this).get(MerchantViewModel.class);
        salesViewModel = new ViewModelProvider(this).get(SalesViewModel.class);
        liquidViewModel = new ViewModelProvider(this).get(LiquidViewModel.class);

        //##################################
           /* setSupportActionBar(binding.mainToolbar);
            binding.mainToolbar.setTitle("가맹점");*/
        //##################################

        goHome();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                goPage(item);
                return false;
            }
        });

        binding.fab.setOnClickListener(view -> {
            //TODO 모든 백스택을 지우기
            goHome();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar_menu, menu);

        return true;
    }

    public static MerchantViewModel getMerchantViewModel() {
        return merchantViewModel;
    }
    public static SalesViewModel getSalesViewModel() {
        return salesViewModel;
    }
    public static LiquidViewModel getLiquidViewModel() {
        return liquidViewModel;
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

    public void goHome() {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.manage_frame,PayHomeFragment.newInstance()).commit();
    }

    public void setBottomNavigation(boolean b) {

        if(b) {
            binding.layoutBottom.setVisibility(View.VISIBLE);
        } else {
            binding.layoutBottom.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("zxing","333");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
                resultCode,
                data);


        if (result != null) {

            if (result.getContents() == null) { //스캔 취소시

                Log.e("zxing", "Canceled scan");

            } else { //스캔 완료시

                Log.e("zxing", result.getContents().toString());

            }

        } else {

// This is important, otherwise the result will not be passed to the fragment

            super.onActivityResult(requestCode, resultCode, data);

        }
    }

}