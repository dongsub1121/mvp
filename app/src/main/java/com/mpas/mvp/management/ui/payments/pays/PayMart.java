package com.mpas.mvp.management.ui.payments.pays;


import android.util.Log;

import com.mpas.mvp.PayUtil;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public abstract class PayMart {

    public static final String TAG = PayMart.class.getSimpleName();

    LinkedHashMap<String,byte[]> payMap;
    LinkedHashMap<String,byte[]> headerMap;
    ArrayList<PayParser> resList;

    public PayMart() {
    }

    public abstract String getJobCode();
    public abstract LinkedHashMap<String,byte[]> set(PayData payData);
    public abstract ArrayList<PayParser> setResItem();

    public void setData (PayData payData) {
        this.payMap = set(payData);
        this.headerMap = new Header().setHeader(payData.tid,  getJobCode() );
        this.resList = setResItem();
    }


    public byte[] get() {
        ByteBuffer authData = ByteBuffer.allocate(3072);
        authData.put(getMapToByte(headerMap));
        authData.put(getMapToByte(payMap));

        return (byte[]) authData.flip().array();
    }

    public byte[] getMapToByte(LinkedHashMap<String,byte[]> map) {

        ByteBuffer authData = ByteBuffer.allocate(3072);


        try {
            for (String key : map.keySet()) {
                authData.put(Objects.requireNonNull(map.get(key)));
                Log.e("AUTH_DATA", "// key : " + key + "// len : " + Objects.requireNonNull(map.get(key)).length + "// value : " + new String(map.get(key)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        authData.flip();
        Log.e("PAY.getAuthData",new String(authData.array()));
        return authData.array();
    }

    public LinkedHashMap<String, String> getResAuthData(byte[] resData) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        int position = 0;
        StringBuilder result = new StringBuilder();

        try {
            String res = new String(resData,"EUC-KR");
            Log.e(" String value : ", res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (PayParser box : resList) {
            int len = box.getLength();
            byte[] val = new byte[len];
            String value = null;
            System.arraycopy(resData,position, val,0,len);

            try {
                value = new String(val, "EUC-KR");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.e("ITEM", "// key : " + box.getTag() + "// len : " + len + "// value : " + value);
            map.put(box.getTag(), value);

            result.append("[").append(box.getTag()).append("] :").append(value).append('\n');
            position += box.getLength();
        }

        map.put("result",result.toString());

        return map;
    }

}
