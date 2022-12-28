<<<<<<<< HEAD:app/src/main/java/com/mpas/mvp/ui/main/mpm/PaymentFragment.java
package com.mpas.mvp.ui.main.mpm;
========
package com.mpas.mvp.ui.main.payments;
>>>>>>>> origin/1005:app/src/main/java/com/mpas/mvp/ui/main/payments/PaymentFragment.java

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

<<<<<<<< HEAD:app/src/main/java/com/mpas/mvp/ui/main/mpm/PaymentFragment.java
import android.annotation.SuppressLint;
========
>>>>>>>> origin/1005:app/src/main/java/com/mpas/mvp/ui/main/payments/PaymentFragment.java
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mpas.mvp.MainActivity;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.PaymentFragmentBinding;

import java.text.MessageFormat;

public class PaymentFragment extends Fragment {

    private static final String TAG = PaymentFragment.class.getSimpleName();
    //private PaymentViewModel paymentViewModel;
    private PaymentsViewModel paymentViewModel;
    private PaymentFragmentBinding binding;

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.payment_fragment,container,false);
        //paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        paymentViewModel = new ViewModelProvider(this).get(PaymentsViewModel.class);

        layoutSetVisibility("READY");

        paymentViewModel.retrofitGetInfo(String.valueOf(getArguments().get("_uid")),String.valueOf(getArguments().get("uid")));
        //paymentViewModel.retrofitGetInfo(String.valueOf(getArguments().get("_uid")), String.valueOf(getArguments().get("uid")));

        paymentViewModel.getMenu().observe(getViewLifecycleOwner(),menu->{
            binding.menu.setText(menu);
        });

        paymentViewModel.getPrice().observe(getViewLifecycleOwner(),price->{
            binding.price.setText(price+" 원");
        });

        paymentViewModel.getAuthMessage().observe(getViewLifecycleOwner(),msg->{
            binding.tvReady.setText(msg);
        });


        paymentViewModel.getAuthNum().observe(getViewLifecycleOwner(),authNum->{
            binding.authNumber.setText(authNum);
            layoutSetVisibility("DONE");
        });

        paymentViewModel.getAuthUniqueNum().observe(getViewLifecycleOwner(),uniqueNum->{
            binding.authUniqueNumber.setText(uniqueNum);
            layoutSetVisibility("DONE");
        });


        paymentViewModel.getAuthDate().observe(getViewLifecycleOwner(),date ->{
            binding.authDate.setText(date);
            layoutSetVisibility("DONE");
        });

        paymentViewModel.getSuccessMessage().observe(getViewLifecycleOwner(),successMessage->{
            binding.authOkResult.setText(successMessage);
            layoutSetVisibility("DONE");
        });

        paymentViewModel.getErrMessage().observe(getViewLifecycleOwner(),errMessage ->{
            Toast.makeText(getContext(),errMessage,Toast.LENGTH_LONG).show();
        });

        binding.cancelButton.setOnClickListener(view -> startActivity( new Intent(requireActivity(),MainActivity.class)));

        binding.authButton.setOnClickListener(view -> paymentViewModel.vanRequest());
        return binding.getRoot();
    }


    public void layoutSetVisibility(String status){

        binding.layoutReady.setVisibility(View.INVISIBLE);
        binding.layoutSuccess.setVisibility(View.INVISIBLE);

        switch (status){
            case "READY":
                binding.layoutReady.setVisibility(View.VISIBLE);
                binding.layoutSuccess.setVisibility(View.INVISIBLE);
                break;

            case "DONE":
                binding.layoutReady.setVisibility(View.INVISIBLE);
                binding.layoutSuccess.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

}