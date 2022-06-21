package com.mpas.mvp.merchant.model;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MerchantInfoModel {

    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("detail")
    String detail;
    @SerializedName("result")
    Result result;

    @NonNull
    @Override
    public String toString() {
        return status+"_"+message+"_"+detail+"_"+result;
    }

    public Result getResult() {
        return result;
    }

    public static class Result{
        @SerializedName("businessnumber")
        @Expose
        String businessnumber;
        @SerializedName("siteid")
        @Expose
        String siteid;
        @SerializedName("useyn")
        @Expose
        String useyn;
        @SerializedName("sitebizno")
        @Expose
        String sitebizno;
        @SerializedName("sitename")
        @Expose
        String sitename;
        @SerializedName("siteaddress")
        @Expose
        String siteaddress;
        @SerializedName("merchantowner")
        @Expose
        String merchantowner;
        @SerializedName("sitephone")
        @Expose
        String sitephone;
        @SerializedName("biztype")
        @Expose
        String biztype;
        @SerializedName("bizdomain")
        @Expose
        String bizdomain;
        @SerializedName("agentname")
        @Expose
        String agentname;
        @SerializedName("agentphone")
        @Expose
        String agentphone;
        @SerializedName("banks")
        List<Banks> banks;

        public String getBusinessnumber() {
            return businessnumber;
        }

        public String getSiteid() {
            return siteid;
        }

        public String getUseyn() {
            return useyn;
        }

        public String getSitebizno() {
            return sitebizno;
        }

        public String getSitename() {
            return sitename;
        }

        public String getSiteaddress() {
            return siteaddress;
        }

        public String getMerchantowner() {
            return merchantowner;
        }

        public String getSitephone() {
            return sitephone;
        }

        public String getBiztype() {
            return biztype;
        }

        public String getBizdomain() {
            return bizdomain;
        }

        public String getAgentname() {
            return agentname;
        }

        public String getAgentphone() {
            return agentphone;
        }

        public List<Banks> getBanks() {
            return banks;
        }

        @NonNull
        @Override
        public String toString() {
            return businessnumber+"_"+
                    siteid+"_"+
                    siteid+"_"+
                    banks
                    ;
        }

        public static class Banks{
            @SerializedName("ficode")
            @Expose
            String ficode;
            @SerializedName("bankname")
            @Expose
            String bankname;
            @SerializedName("merchantnumber")
            @Expose
            String merchantnumber;

            public void setFicode(String ficode) {
                this.ficode = ficode;
            }

            public void setBankname(String bankname) {
                this.bankname = bankname;
            }

            public void setMerchantnumber(String merchantnumber) {
                this.merchantnumber = merchantnumber;
            }

            public String getFicode() {
                return ficode;
            }

            public String getBankname() {
                return bankname;
            }

            public String getMerchantnumber() {
                return merchantnumber;
            }
        }
    }



}
