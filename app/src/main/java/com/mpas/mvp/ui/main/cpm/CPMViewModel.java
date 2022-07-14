package com.mpas.mvp.ui.main.cpm;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Objects;
import java.util.Random;

import io.reactivex.disposables.CompositeDisposable;

public class CPMViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<Bitmap> qrLiveData = new MutableLiveData<>();
    public MutableLiveData<Bitmap> bcdLiveData = new MutableLiveData<>();

    public MutableLiveData<Bitmap> getQrLiveData() {
        return qrLiveData;
    }

    public MutableLiveData<Bitmap> getBcdLiveData() {
        return bcdLiveData;
    }

    public void getBarcodeData(String pay) {
        String barcode = null;
        String qr = null;

        switch (pay){
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
                barcode =  "5182160000000001";
                qr =  "518216000000000199999";
        }

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {

            Bitmap qrBitmap, bcdBitmap;

            BitMatrix qr_bitMatrix = multiFormatWriter.encode(Objects.requireNonNull(qr), BarcodeFormat.QR_CODE, 800, 800);
            BarcodeEncoder qr_barcodeEncoder = new BarcodeEncoder();
            qrBitmap = qr_barcodeEncoder.createBitmap(qr_bitMatrix);

            BitMatrix bcd_bitMatrix = multiFormatWriter.encode(Objects.requireNonNull(barcode), BarcodeFormat.CODE_128, 1200, 400);
            BarcodeEncoder bcd_barcodeEncoder = new BarcodeEncoder();
            bcdBitmap = bcd_barcodeEncoder.createBitmap(bcd_bitMatrix);

            qrLiveData.setValue(qrBitmap);
            bcdLiveData.setValue(bcdBitmap);

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