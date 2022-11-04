package com.mpas.mvp.merchant1.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.ActivityTestBinding;
import com.mpas.mvp.merchant1.model.CalendarItem;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ActivityTestBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_test);

        InfoAdapter infoAdapter = new InfoAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        binding.calRecycler.setLayoutManager(gridLayoutManager);
        binding.calRecycler.setAdapter(infoAdapter);
        binding.calRecycler.scrollToPosition(infoAdapter.getItemCount()-1);


    }
}