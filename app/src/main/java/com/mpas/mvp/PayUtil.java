package com.mpas.mvp;

import static com.mpas.mvp.ZeroPay.DataKey.Type.Alpha_Numeric;
import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class PayUtil {
    public static final String TAG = PayUtil.class.getSimpleName();

    public static LinkedHashMap<String, byte[]> map;

    public static String setIntegerToStringFormat(int value, int length) {
        String format = "%0" + length + "d";
        return format(format, value);
    }

    public static LinkedHashMap<String, byte[]> getMap(String pay){
        if(pay.equals("ZeroPay")){
            map = ZeroPay.getMap();
        }else{
            Log.e(TAG,"None");
        }
        return map;
    }


    public static void mapInitialize(Map<String, byte[]> map, String key, int length) {

        byte[] bytes = new byte[length];

        switch (ZeroPay.DataKey.getDataKeyType(key)) {
            case Alpha_Numeric:
                Arrays.fill(bytes, (byte) 32);
                break;
            case Numeric:
                bytes = setIntegerToStringFormat(0, length).getBytes();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ZeroPay.DataKey.getDataKeyType(key));
        }

        map.put(key, bytes);
    }

    public static class DataKey {
        public DataKey() {
        }

        public static ZeroPay.DataKey.Type keyType;

        public  enum Type {
            Numeric,
            Alpha_Numeric,
            Alpha_Numeric_Special,
            Hex,
            Varialbe
        }

        public static ZeroPay.DataKey.Type getDataKeyType(String sKey){

            String[] Numeric = {
                    "AMOUNT",
                    "TAX",
                    "TAX_FREE",
                    "TIP",
                    "CREDIT_MONTH",
                    "POINT_AMOUNT",
            };

            for ( String key : Numeric){
                if (key.equals(sKey)){
                    keyType = ZeroPay.DataKey.Type.Numeric;
                    break;
                }else {
                    keyType = Alpha_Numeric;
                }
            }

            return keyType;
        }
    }

    public static String setTime(String format) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat set = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();

        return set.format(calendar.getTime());
    }

    public static void putMapData(String key, Object data) {

        ByteBuffer previous = ByteBuffer.wrap(Objects.requireNonNull(ZeroPay.getMap().get(key)));
        ZeroPay.DataKey.Type keyType = DataKey.getDataKeyType(key);
        byte[] current = null;

        if (data instanceof String) {

            current = ((String) data).getBytes();

        } else if (data instanceof Integer) {

            current = String.valueOf(data).getBytes();

        } else if (data instanceof byte[]) {

            current = (byte[]) data;

        }

        if (keyType.equals(DataKey.Type.Numeric)) {

            previous.position(previous.limit() - Objects.requireNonNull(current).length);
            previous.put(current);

        } else if (keyType.equals(DataKey.Type.Alpha_Numeric)) {

            previous.put(Objects.requireNonNull(current));

        }

        previous.flip();
        map.put(key, previous.array());

    }

    public static byte[] getMapToByte(HashMap<String, byte[]> map) {
        ByteBuffer authData = ByteBuffer.allocate(1024);
        byte[] data;

        try {
            for (String key : map.keySet()) {
                data = map.get(key);

                authData.put(Objects.requireNonNull(data));
                Log.e("GET_DATA", "// key : " + key + "// len : " + data.length + "// value : " + new String(data));
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            //TODO 데이터가 맞지 않을때 체크동작 구현 ( 파싱해서 불일치한 데이터 UI에 표시, 팝업 )

            e.printStackTrace();
        }
        authData.flip();
        byte[] tmp = new byte[authData.limit()];
        authData.get(tmp,0,authData.limit());

        Log.e("PAY.getAuthData", MessageFormat.format("Len : {0}\n data : {1}", String.valueOf(tmp.length), new String(tmp)));
        return tmp;
    }

    public static LinkedHashMap<String, String> getResAuthData(ArrayList<Item> arrayList, byte[] resData) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        int position = 0;
        StringBuilder result = new StringBuilder();

        try {
            String res = new String(resData,"EUC-KR");
            Log.e(" String value : ", res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (Item item : arrayList) {
            int len = item.getLength();
            byte[] val = new byte[len];
            String value = null;
            System.arraycopy(resData,position, val,0,len);

            try {
                value = new String(val, "EUC-KR");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.e("ITEM", "// key : " + item.getName() + "// len : " + len + "// value : " + value);
            map.put(item.getName(), value);


            if(item.getName().equals("result") || item.getName().equals("resultCode") || item.getName().equals("authNum") || item.getName().equals("authDate") || item.getName().equals("authUniqueNum")){

            }
            //result.append("[").append(item.getName()).append("] :").append(value).append('\n');
            position += item.getLength();
        }

        map.put("result",result.toString());

        return map;
    }

    public static class Item {
        String name;
        int length;

        public Item(String key, int len) {
            this.name = key;
            this.length = len;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

    }

    public static class AuthItem {
        int amount;
        String jobCode;
        String authDate;
        String authNum;
        String authUniqueNum;
        String pay;
        String type;

        public String getAuthUniqueNum() {
            return authUniqueNum;
        }

        public void setAuthUniqueNum(String authUniqueNum) {
            this.authUniqueNum = authUniqueNum;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getJobCode() {
            return jobCode;
        }

        public void setJobCode(String type) {
            String Code = "";
            if (type.equals("A")){
                Code = "8050";
            } else if (type.equals("C")){
                Code = "8051";
            }
            this.jobCode = Code;
        }

        public String getAuthDate() {
            return authDate;
        }

        public void setAuthDate(String authDate) {
            this.authDate = authDate;
        }

        public String getAuthNum() {
            return authNum;
        }

        public void setAuthNum(String authNum) {
            this.authNum = authNum;
        }

        @NonNull
        @org.jetbrains.annotations.NotNull
        @Override
        public String toString() {
            return "amount=" + amount +
                    " jobCode=" + jobCode +
                    " authDate=" + authDate +
                    " authNum=" + authNum;
        }
    }


}
