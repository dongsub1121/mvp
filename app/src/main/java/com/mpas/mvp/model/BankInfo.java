package com.mpas.mvp.model;

import com.google.gson.annotations.SerializedName;

public class BankInfo {

    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("detail")
    String detail;
    @SerializedName("result")
    Result result;

    public BankInfo(String status, String message, String detail, Result result){
        this.status = status;
        this.message = message;
        this.detail = detail;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSuccess(){
        return Integer.parseInt(getStatus()) == 200;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public Result getResult() {
        return result;
    }

    static class Result {
        @SerializedName("fiCode")
        String fiCode;
        @SerializedName("bankName")
        String bankName;
        @SerializedName("merchantNumber")
        String merchantNumber;

        public String getFiCode() {
            return fiCode;
        }

        public String getBankName() {
            return bankName;
        }

        public String getMerchantNumber() {
            return merchantNumber;
        }
    }
}
