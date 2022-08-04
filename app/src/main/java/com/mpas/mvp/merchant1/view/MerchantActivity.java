package com.mpas.mvp.merchant1.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayoutMediator;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.ActivityMerchantBinding;

import java.util.ArrayList;
import java.util.List;

public class MerchantActivity extends AppCompatActivity {

    private static final String TAG = MerchantActivity.class.getSimpleName();
    private static ActivityMerchantBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_merchant);

        setupViewPager(binding.viewPager);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupViewPager(ViewPager2 viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.addFragment(MerchantFragment.newInstance(),"Setting");
        adapter.addFragment(SalesFragment.newInstance(),"Sales");
        adapter.addFragment(SalesSummaryFragment.newInstance(),"Summary");
        viewPager.setAdapter(adapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            tab.setText(adapter.getTag(position));
        }).attach();
    }


    private static class ViewPagerAdapter  extends FragmentStateAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTagList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }

        public void addFragment( Fragment fragment , String tag){
            mFragmentList.add(fragment);
            mFragmentTagList.add(tag);
        }

        public String getTag(int position) {
            return mFragmentTagList.get(position);
        }

    }
}