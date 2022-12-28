package com.mpas.mvp.management.ui.payments.pays;

public class PayParser {
    String tag;
    int length;
    //Gravity putType;


    public PayParser(String tag , int len) {
        this.tag = tag;
        this.length = len;
    }

    public static enum Gravity {
        Numeric,
        Alpha_Numeric,
        Alpha_Numeric_Special,
        Hex,
        Variable;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

/*    public Gravity getPutType() {
        return putType;
    }

    public void setPutType(Gravity putType) {
        this.putType = putType;
    }*/
}
