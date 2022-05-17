package com.mpas.mvp;


import static com.mpas.mvp.PayUtil.setIntegerToStringFormat;

import com.mpas.mvp.PayUtil.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;


public class ZeroPay {
    public static LinkedHashMap<String, byte[]> map;

    public ZeroPay() {

        map = new LinkedHashMap<String, byte[]>();
        mapInitialize("WCC", 1);
        mapInitialize("BARCODE", 100);
        mapInitialize("PAY_TYPE", 3);
        mapInitialize("AMOUNT", 12);
        mapInitialize("TAX", 12);
        mapInitialize("TIP", 12);
        mapInitialize("AUTH_DATE", 6);
        mapInitialize("AUTH_NUM", 12);
        mapInitialize("NO_TAX", 12);
        mapInitialize("SUB_BUSINESS", 10);
        mapInitialize("MERCHANT_DATA", 50);
        mapInitialize("RESERVED1", 64);
        mapInitialize("RESERVED2", 64);
        mapInitialize("CR", 1);

    }

    public static LinkedHashMap<String , byte[]> getMap(){
        new ZeroPay();
        map.put("WCC","Q".getBytes());
        map.put("PAY_TYPE","MPM".getBytes());
        byte[] cr = {0x0D};
        map.put("CR",cr);

        return map;
    }

    public static ArrayList<Item> setResItem() {
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item("result",1));
        items.add(new Item("resultCode",4));
        items.add(new Item("authNum",12));
        items.add(new Item("authDate",12));
        items.add(new Item("authUniqueNum",12));
        items.add(new Item("가맹점번호",15));
        items.add(new Item("발급사코드",4));
        items.add(new Item("발급사명",20));
        items.add(new Item("매입사코드",4));
        items.add(new Item("매입사명",20));
        items.add(new Item("누적환불금액",12));
        items.add(new Item("가맹점수수료",12));
        items.add(new Item("펌뱅킹 대표기관코드",3));
        items.add(new Item("펌뱅킹 이용기관코드",20));
        items.add(new Item("펌뱅킹 하위기관코드",10));
        items.add(new Item("펌뱅킹 전문번호",20));
        items.add(new Item("펌뱅킹 부가정보",20));
        items.add(new Item("가맹점 임의 데이터",50));
        items.add(new Item("RESERVED1",64));
        items.add(new Item("RESERVED1",64));
        items.add(new Item("PRINT MSG1",28));
        items.add(new Item("PRINT MSG2",28));
        items.add(new Item("PRINT MSG3",28));
        items.add(new Item("PRINT MSG4",28));
        items.add(new Item("PRINT MSG5",28));
        items.add(new Item("PRINT MSG6",28));

        return items;
    }


    public static void mapInitialize(String key, int length) {

        byte[] bytes = new byte[length];

        switch (DataKey.getDataKeyType(key)) {
            case Alpha_Numeric:
                Arrays.fill(bytes, (byte) 32);
                break;
            case Numeric:
                bytes = setIntegerToStringFormat(0, length).getBytes();
                break;
            case Alpha_Numeric_Special:
            case Hex:
            case Varialbe:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + DataKey.getDataKeyType(key));
        }

        map.put(key, bytes);
    }

    public static class DataKey {
        public DataKey() {
        }

        public static Type keyType;

        public  enum Type {
            Numeric,
            Alpha_Numeric,
            Alpha_Numeric_Special,
            Hex,
            Varialbe
        }

        public static Type getDataKeyType(String sKey){

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
                    keyType = Type.Numeric;
                    break;
                }else {
                    keyType = Type.Alpha_Numeric;
                }
            }

            return keyType;
        }
    }
}
