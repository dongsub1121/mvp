package com.mpas.mvp.management.ui.payments.pays;

import java.util.LinkedHashMap;

public class Header {

    public  LinkedHashMap<String, byte[]> map;

    public Header() {
        map = new LinkedHashMap<String, byte[]>();
        map.put("JOB_CODE", new byte[4]);
        map.put("TID", new byte[10]);
        map.put("VER", new byte[12]);
        map.put("SW_VER", new byte[10]);
        map.put("HW_VER", new byte[10]);
        map.put("HW", new byte[10]);
        map.put("CERTIFICATION", new byte[32]);
        map.put("DATE", new byte[12]);
        map.put("FILLER", new byte[5]);
        map.put("PACKET", new byte[1]);

        setHeaderMap();
    }

    public void setHeaderMap(){
        map.put("SW_VER","1100110001".getBytes());
        map.put("HW_VER","PF19080001".getBytes());
        map.put("CERTIFICATION","####PF-5300B1003JTTPAYMOBADR1003".getBytes());
        map.put("PACKET","R".getBytes());

    }

    public LinkedHashMap<String, byte[]> setHeader(String tid, String jobCode) {
        map.put("JOB_CODE", jobCode.getBytes());
        map.put("TID", tid.getBytes());

        return map;
    }

}