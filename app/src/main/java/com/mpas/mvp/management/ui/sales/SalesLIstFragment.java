package com.mpas.mvp.management.ui.sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpas.mvp.R;

import java.util.ArrayList;

public class SalesLIstFragment extends Fragment {

    public static SalesLIstFragment newInstance() {
        return new SalesLIstFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_sales_list,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.sales_recyclerview);

        ArrayList<String> items = new ArrayList<>();
        items.add("90000");
        items.add("80000");
        items.add("70000");
        items.add("60000");items.add("50000");items.add("40000");items.add("30000");items.add("20000");

        SalesListAdapter salesListAdapter = new SalesListAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(salesListAdapter);


        return view.getRootView();
    }
}
