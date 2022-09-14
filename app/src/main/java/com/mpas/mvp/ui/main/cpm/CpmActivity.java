package com.mpas.mvp.ui.main.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.tabs.TabLayoutMediator;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.ActivityCpmBinding;
import com.mpas.mvp.merchant1.view.SalesFragment;
import com.mpas.mvp.merchant1.view.SalesSummaryFragment;

import java.util.ArrayList;
import java.util.List;

public class CpmActivity extends AppCompatActivity {

    ActivityCpmBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cpm);
        setupViewPager(binding.viewPager);

        binding.tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tab", String.valueOf(binding.tabLayout.getSelectedTabPosition()));
                binding.viewPager.setHorizontalScrollBarEnabled(false);
            }
        });

        binding.viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("motionEvent",motionEvent.toString());
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager2 viewPager) {

        CpmActivity.ViewPagerAdapter adapter = new CpmActivity.ViewPagerAdapter(this);
        adapter.addFragment(CreditPayFragment.newInstance(),"CREDIT");
        adapter.addFragment(KakaoPayFragment.newInstance(),"KAKAO PAY");
        adapter.addFragment(ZeroPayFragment.newInstance(),"ZERO PAY");
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