package com.mpas.mvp.merchant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BanksModel {

    String ficode;
    String bankname;
    String merchantnumber;

    public BanksModel(String fic, String bnk, String mid) {
        this.bankname = bnk;
        this.ficode = fic;
        this.merchantnumber = mid;
    }

    public String getFicode() {
        return ficode;
    }

    public void setFicode(String ficode) {
        this.ficode = ficode;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getMerchantnumber() {
        return merchantnumber;
    }

    public void setMerchantnumber(String merchantnumber) {
        this.merchantnumber = merchantnumber;
    }
}
