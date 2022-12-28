package com.mpas.mvp.management.ui.payments.pays;

import static com.mpas.mvp.management.ui.payments.pays.ProtocolConfig.*;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Credit extends PayMart{

    LinkedHashMap<String, byte[]> paySet = new LinkedHashMap<String, byte[]>();
    PayData payData;

    public Credit() {

        paySet.put("WCC", "A".getBytes());
        paySet.put("TRACK_DATA", new byte[100]);
        paySet.put("CREDIT_MONTH", "00".getBytes());
        paySet.put("PRICE", new byte[9]);
        paySet.put("TAX", new byte[9]);
        paySet.put("TIP", new byte[9]);
        paySet.put("CURRENT", "KRW".getBytes());
        paySet.put("AUTH_DATE", new byte[6]);
        paySet.put("AUTH_NUM", new byte[12]);
        paySet.put("AUTH_UNIQUE", new byte[12]);
        paySet.put("FALLBACK_INFO", new byte[3]);
        paySet.put("PAY_CODE", new byte[2]);
        paySet.put("SERVICE_CODE", new byte[3]);
        paySet.put("NO_TAX", new byte[9]);
        paySet.put("PW", new byte[18]);
        paySet.put("OIL_INFO",  new byte[3]);
        paySet.put("SUB_BIZ_NUM", new byte[10]);
        paySet.put("POS_UNIQUE_NUM", new byte[16]);
        paySet.put("EXTRA_2", new byte[32]);
        paySet.put("EXTRA_3", new byte[128]);
        paySet.put("FS1", new byte[]{(byte) 28});
        paySet.put("SIGN", "A".getBytes());
        paySet.put("FS2", new byte[]{(byte) 28});
        paySet.put("EMV_DATA", new byte[690]);
        paySet.put("FS3", new byte[]{(byte) 28});
        paySet.put("POS_MAC_ADDRESS", new byte[40]);
        paySet.put("CR", new byte[]{(byte) 13});

    }


    @Override
    public String getJobCode() {
        String jobCode = null;

        try {
            if (payData.getPayClass().equals(ProtocolConfig.PayClass.AUTH)) {
                jobCode = _JOB_CODE_CREDIT;
            }else if (payData.getPayClass().equals(ProtocolConfig.PayClass.CANCEL)) {
                jobCode = _JOB_CODE_CREDIT_CANCEL;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return jobCode;
    }

    @Override
    public LinkedHashMap<String, byte[]> set(PayData payData) {

        this.payData = payData;
        Log.e("Credit", "setPayMap" );

        putPrice(String.valueOf(payData.getPrice()));
        putTax(String.valueOf(payData.getTax()));
        putTip(String.valueOf(payData.getTip()));
        putNoTax(String.valueOf(payData.getNoTax()));

        if (payData.getCurrent() != null) {
            putCurrent(payData.getCurrent());
        }

        if (payData.getAuth_date() != null) {
            putAuth_date(payData.getAuth_date());
        }

        if (payData.getAuth_num() != null) {
            putAuth_num(payData.getAuth_num());
        }

        if (payData.getAuth_unique() != null) {
            putAuth_unique(payData.getAuth_unique());
        }

        if (payData.getCreditMonth() != null) {
            putCreditMonth(payData.getCreditMonth());
        }

        return paySet;
    }

    @Override
    public ArrayList<PayParser> setResItem() {

        ArrayList<PayParser> parserArrayList = new ArrayList<>();

        parserArrayList.add(new PayParser(KeyStore.RES_CODE, 4));
        parserArrayList.add(new PayParser(KeyStore.RES_MSG, 36));
        parserArrayList.add(new PayParser(KeyStore.TID, 15));
        parserArrayList.add(new PayParser(KeyStore.ISSUER_CODE, 4));
        parserArrayList.add(new PayParser(KeyStore.ISSUER, 20));
        parserArrayList.add(new PayParser(KeyStore.PURCHASER_CODE, 4));
        parserArrayList.add(new PayParser(KeyStore.PURCHASER, 20));
        parserArrayList.add(new PayParser(KeyStore.CARD_CLASS, 2));
        parserArrayList.add(new PayParser(KeyStore.PURCHASE_CLASS, 1));
        parserArrayList.add(new PayParser(KeyStore.EMV_OPTION, 1));
        parserArrayList.add(new PayParser(KeyStore.BILL_PRINT, 1));
        parserArrayList.add(new PayParser(KeyStore.DISCOUNT, 9));
        parserArrayList.add(new PayParser(KeyStore.USE_POINT, 9));
        parserArrayList.add(new PayParser(KeyStore.AVAILABLE_POINT, 9));
        parserArrayList.add(new PayParser(KeyStore.ACCRUE_POINT, 9));
        parserArrayList.add(new PayParser(KeyStore.GIFT_BALANCE, 9));
        parserArrayList.add(new PayParser(KeyStore.CARD_POINT, 9));
        parserArrayList.add(new PayParser(KeyStore.POINT_INFO, 60));
        parserArrayList.add(new PayParser(KeyStore.EXTRA1, 16));
        parserArrayList.add(new PayParser(KeyStore.EXTRA2, 64));
        parserArrayList.add(new PayParser(KeyStore.EXTRA3, 128));
        parserArrayList.add(new PayParser(KeyStore.TRACK_DATA, 20));
        parserArrayList.add(new PayParser(KeyStore.PRINT1, 28));
        parserArrayList.add(new PayParser(KeyStore.PRINT2, 28));
        parserArrayList.add(new PayParser(KeyStore.PRINT3, 28));
        parserArrayList.add(new PayParser(KeyStore.PRINT4, 28));
        parserArrayList.add(new PayParser(KeyStore.PRINT5, 28));

        return parserArrayList;
    }


    public void putPrice (String data) {
        byte[] bytes = paySet.get("PRICE");
        byte[] newData = data.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes), (bytes.length-newData.length), newData.length);
            paySet.put("PRICE",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putTax (String data) {
        byte[] bytes = paySet.get("TAX");
        byte[] newData = data.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes), (bytes.length-newData.length), newData.length);
            paySet.put("TAX",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putTip (String data) {
        byte[] bytes = paySet.get("TIP");
        byte[] newData = data.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes), (bytes.length-newData.length), newData.length);
            paySet.put("TIP",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putNoTax (String data) {
        byte[] bytes = paySet.get("TIP");
        byte[] newData = data.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes), (bytes.length-newData.length), newData.length);
            paySet.put("TIP",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putCurrent (String data) {
        byte[] bytes = new byte[Objects.requireNonNull(paySet.get("CURRENT")).length];
        byte[] newData = data.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes), 0, newData.length);
            paySet.put("CURRENT",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putAuth_date (String data) {
        byte[] bytes = paySet.get("AUTH_DATE");
        byte[] newData = data.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes),0, newData.length);
            paySet.put("AUTH_DATE",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putAuth_num (String data) {
        byte[] bytes = paySet.get("AUTH_NUM");
        byte[] newData = data.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes),0, newData.length);
            paySet.put("AUTH_NUM",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putAuth_unique (String data) {
        byte[] bytes = paySet.get("AUTH_UNIQUE");
        byte[] newData = data.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes),0, newData.length);
            paySet.put("AUTH_UNIQUE",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putCreditMonth (String data) {
        String month = data;

        if (month.length() == 1 ) {
            month = "0"+ month;
        }

        byte[] bytes = paySet.get("CREDIT_MONTH");
        byte[] newData = month.getBytes();

        try {
            System.arraycopy(newData, 0, Objects.requireNonNull(bytes),0, newData.length);
            paySet.put("CREDIT_MONTH",bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class KeyStore {
        public static final  String RES_CODE = "RES_CODE";
        public static final  String RES_MSG = "RES_MSG";
        public static final  String TID = "TID";
        public static final  String ISSUER_CODE = "ISSUER_CODE";
        public static final  String ISSUER = "ISSUER";
        public static final  String PURCHASER_CODE = "PURCHASER_CODE";
        public static final  String PURCHASER = "PURCHASER";
        public static final  String CARD_CLASS = "CARD_CLASS";
        public static final  String PURCHASE_CLASS = "PURCHASE_CLASS";
        public static final  String EMV_OPTION = "EMV_OPTION";
        public static final  String BILL_PRINT = "BILL_PRINT";
        public static final  String DISCOUNT = "DISCOUNT";
        public static final  String USE_POINT = "USE_POINT"; // 발생
        public static final  String AVAILABLE_POINT = "AVAILABLE_POINT"; // 가용
        public static final  String ACCRUE_POINT = "ACCRUE_POINT"; // 누적
        public static final  String GIFT_BALANCE = "GIFT_BALANCE";
        public static final  String CARD_POINT = "CARD_POINT";
        public static final  String POINT_INFO = "POINT_INFO";
        public static final  String EXTRA1 = "EXTRA1";
        public static final  String EXTRA2 = "EXTRA2";
        public static final  String EXTRA3 = "EXTRA3";
        public static final  String TRACK_DATA = "TRACK_DATA";
        public static final  String PRINT1 = "PRINT1";
        public static final  String PRINT2 = "PRINT2";
        public static final  String PRINT3 = "PRINT3";
        public static final  String PRINT4 = "PRINT4";
        public static final  String PRINT5 = "PRINT5";
    }

}
