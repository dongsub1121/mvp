package com.mpas.mvp;

import androidx.annotation.NonNull;

public class AuthData {

    private int Price;
    private String menu;
    private String jobCode;
    private String authDate;
    private String authNum;
    private String authUID;

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
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

    public String getAuthUID() {
        return authUID;
    }

    public void setAuthUID(String authUID) {
        this.authUID = authUID;
    }

    @NonNull
    @Override
    public String toString() {
        return '{'+"Price="+Price+
                ','+"menu="+menu+
                ','+"authDate="+authDate+
                ','+"authNum="+authNum+
                ','+"authUID="+authUID+
                '}';
    }
}
