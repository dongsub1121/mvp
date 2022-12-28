package com.mpas.mvp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaydaPostMPMCommit {

    @SerializedName("clientID") @Expose
    private String clientID;
    @SerializedName("result") @Expose
    private String result;
    @SerializedName("resultCode") @Expose
    private String resultCode;
    @SerializedName("resultMsg") @Expose
    private String resultMsg;
    @SerializedName("totalPrice") @Expose
    private Integer totalPrice;
    @SerializedName("price") @Expose
    private Integer price;
    @SerializedName("noTaxPrice") @Expose
    private Integer noTaxPrice;
    @SerializedName("taxPrice") @Expose
    private Integer taxPrice;
    @SerializedName("tipPrice") @Expose
    private Integer tipPrice;
    @SerializedName("app_tm") @Expose
    private String app_tm;
    @SerializedName("app_no") @Expose
    private String app_no;
    @SerializedName("tran_un") @Expose
    private String tran_un;
    @SerializedName("isur_cd") @Expose
    private String isur_cd;
    @SerializedName("isur_nm") @Expose
    private String isur_nm;
    @SerializedName("acqu_cd") @Expose
    private String acqu_cd;
    @SerializedName("acqu_nm") @Expose
    private String acqu_nm;
    @SerializedName("res_cd") @Expose
    private String res_cd;

    public String getIsur_cd() {
        return isur_cd;
    }

    public void setIsur_cd(String isur_cd) {
        this.isur_cd = isur_cd;
    }

    public String getIsur_nm() {
        return isur_nm;
    }

    public void setIsur_nm(String isur_nm) {
        this.isur_nm = isur_nm;
    }

    public String getAcqu_cd() {
        return acqu_cd;
    }

    public void setAcqu_cd(String acqu_cd) {
        this.acqu_cd = acqu_cd;
    }

    public String getAcqu_nm() {
        return acqu_nm;
    }

    public void setAcqu_nm(String acqu_nm) {
        this.acqu_nm = acqu_nm;
    }

    public String getRes_cd() {
        return res_cd;
    }

    public void setRes_cd(String res_cd) {
        this.res_cd = res_cd;
    }

    public String getRes_mg1() {
        return res_mg1;
    }

    public void setRes_mg1(String res_mg1) {
        this.res_mg1 = res_mg1;
    }

    @SerializedName("res_mg1") @Expose
    private String res_mg1;

    public String getApp_tm() {
        return app_tm;
    }

    public void setApp_tm(String app_tm) {
        this.app_tm = app_tm;
    }

    public String getApp_no() {
        return app_no;
    }

    public void setApp_no(String app_no) {
        this.app_no = app_no;
    }

    public String getTran_un() {
        return tran_un;
    }

    public void setTran_un(String tran_un) {
        this.tran_un = tran_un;
    }

    @SerializedName("authDate") @Expose
    private String authDate;
    @SerializedName("authTime") @Expose
    private String authTime;
    @SerializedName("authNum") @Expose
    private String authNum;
    @SerializedName("authUniqueNum") @Expose
    private String authUniqueNum;
    @SerializedName("clientData") @Expose
    private ClientData clientData;

    public ClientData getClientData() {
        return clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNoTaxPrice() {
        return noTaxPrice;
    }

    public void setNoTaxPrice(Integer noTaxPrice) {
        this.noTaxPrice = noTaxPrice;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Integer taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Integer getTipPrice() {
        return tipPrice;
    }

    public void setTipPrice(Integer tipPrice) {
        this.tipPrice = tipPrice;
    }

    public String getAuthDate() {
        return app_tm;
    }

    public void setAuthDate(String authDate) {
        this.app_tm = authDate;
    }

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }

    public String getAuthNum() {
        return authNum;
    }

    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }

    public String getAuthUniqueNum() {
        return authUniqueNum;
    }

    public void setAuthUniqueNum(String authUniqueNum) {
        this.authUniqueNum = authUniqueNum;
    }


    public static class ClientData {
        @SerializedName("issuerCode") @Expose
        private String issuerCode;
        @SerializedName("issuerName") @Expose
        private String issuerName;
        @SerializedName("purchaseCode") @Expose
        private String purchaseCode;
        @SerializedName("purchaseName") @Expose
        private String purchaseName;

        public String getIssuerCode() {
            return issuerCode;
        }

        public void setIssuerCode(String issuerCode) {
            this.issuerCode = issuerCode;
        }

        public String getIssuerName() {
            return issuerName;
        }

        public void setIssuerName(String issuerName) {
            this.issuerName = issuerName;
        }

        public String getPurchaseCode() {
            return purchaseCode;
        }

        public void setPurchaseCode(String purchaseCode) {
            this.purchaseCode = purchaseCode;
        }

        public String getPurchaseName() {
            return purchaseName;
        }

        public void setPurchaseName(String purchaseName) {
            this.purchaseName = purchaseName;
        }
    }
}

