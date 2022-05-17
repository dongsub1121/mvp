package com.mpas.mvp;

import static com.mpas.mvp.PayUtil.setIntegerToStringFormat;

import java.util.Arrays;
import java.util.LinkedHashMap;


public class Header {

    public static LinkedHashMap<String, byte[]> map;

    public Header() {
        map = new LinkedHashMap<String, byte[]>();
        mapInitialize("JOB_CODE", 4);
        mapInitialize("TID", 10);
        mapInitialize("MANAGE_NUM", 12);
        mapInitialize("SW_VER", 10);
        mapInitialize("HW_VER", 10);
        mapInitialize("CERTIFICATION", 32);
        mapInitialize("DATE", 12);
        mapInitialize("FILLER", 1);
        mapInitialize("PACKET", 1);
    }

    public static LinkedHashMap<String, byte[]> getHeaderMap(){
        new Header();
        map.put("TID","1814119990".getBytes());
        map.put("MANAGE_NUM","000000000001".getBytes());
        map.put("SW_VER","1100110001".getBytes());
        map.put("HW_VER","PF19080001".getBytes());
        map.put("CERTIFICATION","####PF-5300B1003JTTPAYMOBADR1003".getBytes());
        map.put("DATE", PayUtil.setTime("yymmddhhmmss").getBytes());
        map.put("FILLER","     ".getBytes());
        map.put("PACKET","R".getBytes());
        return map;
    }

    public static void mapInitialize(String key, int length) {

        byte[] bytes = new byte[length];

        switch (ZeroPay.DataKey.getDataKeyType(key)) {
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
                throw new IllegalStateException("Unexpected value: " + ZeroPay.DataKey.getDataKeyType(key));
        }

        map.put(key, bytes);
    }

}
