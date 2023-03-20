package com.mpas.mvp.management.ui.payments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mpas.mvp.R;
import com.mpas.mvp.databinding.CalculatorFragmentBinding;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CalculatorFragment extends Fragment implements View.OnClickListener {

    private CalculatorViewModel mViewModel;
    private CalculatorFragmentBinding binding;
    private TextView sic,value;

    public static CalculatorFragment newInstance() {
        return new CalculatorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.calculator_fragment, container, false);
        sic = binding.calculator;
        value = binding.editTextNumberDecimal;
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        // TODO: Use the ViewModel


    }

    @Override
    public void onClick(View view) {
        if (binding.buttonCancel.equals(view)) {
            sic.setText("");
        } else if (binding.button1.equals(view)) {
            sic.setText(sic.getText().toString() + "1");
        } else {
            throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    protected String makeStringComma(String inputStr) {    // 천단위 콤마 처리
        if (TextUtils.isEmpty(inputStr))
            return inputStr;

        String str = inputStr.replace(",", ""); //기존 컴마 제거
        BigDecimal bigDecimal = new BigDecimal(str);
        DecimalFormat format = new DecimalFormat("###,###"); //포맷팅

        String returnStr = format.format(bigDecimal);
        return returnStr; //리턴
    }
}