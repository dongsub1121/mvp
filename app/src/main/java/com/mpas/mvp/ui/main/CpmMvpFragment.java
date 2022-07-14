package com.mpas.mvp.ui.main;

import static androidx.databinding.DataBindingUtil.inflate;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mpas.mvp.OnBackPressedListener;
import com.mpas.mvp.R;
import com.mpas.mvp.databinding.FragmentCpmMvpBinding;

import java.util.Objects;
import java.util.Random;


public class CpmMvpFragment extends Fragment implements OnBackPressedListener {

    @SuppressLint("StaticFieldLeak")
    private static FragmentCpmMvpBinding binding;

    public static CpmMvpFragment newInstance() { return new CpmMvpFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding =  inflate(inflater, R.layout.fragment_cpm_mvp,container,false);
        setBarcode("Credit");

        return binding.getRoot();
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
            case "Credit":
                barcode =  "999999999999999999999";
                qr =  "999999999999999999999";
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

    @Override
    public void onBackPressed() {

    }
}
