package com.mpas.mvp.ui.main;

import static androidx.databinding.DataBindingUtil.inflate;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mpas.mvp.CpmItem;
import com.mpas.mvp.CpmRecyclerAdapter;
import com.mpas.mvp.MainActivity;
import com.mpas.mvp.OnBackPressedListener;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.CpmFragmentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CpmFragment extends Fragment implements OnBackPressedListener {

    private CpmViewModel mViewModel;

    @SuppressLint("StaticFieldLeak")
    private static CpmFragmentBinding binding;
    private MainActivity mainActivity;


    public static CpmFragment newInstance() {
        return new CpmFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = inflate(inflater, R.layout.cpm_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CpmViewModel.class);

        mainActivity.setOnBackPressedListener(this);

        RecyclerView recyclerView = binding.recyclerView ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<CpmItem> items = new ArrayList<>();
        items.add(new CpmItem(R.drawable.zeropay,"ZeroPay"));
        items.add(new CpmItem(R.drawable.kakao_credit,"KaKao_Credit"));
        items.add(new CpmItem(R.drawable.kakao_money,"KaKao_Money"));

        recyclerView.setAdapter(new CpmRecyclerAdapter(getContext(), items, R.layout.cpm_fragment));

    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onBackPressed() {
        mainActivity.fragmentChange(1,new Bundle());
    }

    public static void setBarcode(String title) {
        String barcode = null;
        String qr = null;

        switch (title){
            case "ZeroPay":
                barcode = "800088123456789012345678";
                qr = getZero_qr();
                break;
            case "KaKao_Credit":
                barcode =  "281006020000000000367403";
                qr =  "281006020000000000367403";
                break;
            case "KaKao_Money":
                barcode =  "281006020000000000857324";
                qr =  "281006020000000000857324";
                break;
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {

            if (Objects.requireNonNull(qr).length() > 0) {
                BitMatrix qr_bitMatrix = multiFormatWriter.encode(Objects.requireNonNull(qr), BarcodeFormat.QR_CODE, 800, 800);
                BarcodeEncoder qr_barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = qr_barcodeEncoder.createBitmap(qr_bitMatrix);
                binding.ivQrGenerate.setImageBitmap(bitmap);
            }


            if (barcode.length() > 0) {
                BitMatrix bcd_bitMatrix = multiFormatWriter.encode(Objects.requireNonNull(barcode), BarcodeFormat.CODE_128, 1200, 400);
                BarcodeEncoder bcd_barcodeEncoder = new BarcodeEncoder();
                Bitmap bcd_bitmap = bcd_barcodeEncoder.createBitmap(bcd_bitMatrix);
                binding.ivBcdGenerate.setImageBitmap(bcd_bitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getZero_qr() {

        Random rnd = new Random();
        StringBuilder qr = new StringBuilder();

        qr.append("3-");
        qr.append((char) ((int) (rnd.nextInt(26)) + 65));
        qr.append((char) ((int) (rnd.nextInt(26)) + 65));
        qr.append("-01234567891234-");
        qr.append((char) ((int) (rnd.nextInt(26)) + 65));
        qr.append((char) ((int) (rnd.nextInt(26)) + 65));
        qr.append((char) ((int) (rnd.nextInt(26)) + 65));
        qr.append((char) ((int) (rnd.nextInt(26)) + 97));

        Log.e("getZero_qr",qr.toString());
        return qr.toString();
    }

}