package com.mpas.mvp.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class MerchantInfo {

    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("detail")
    String detail;
    @SerializedName("result")
    JsonObject result;

    public JsonObject getResult() {
        return result;
    }

    public static class result{
        String businessnumber;
        String siteid;
        String useyn;
        String sitebizno;
        String sitename;
        String siteaddress;
        String merchantowner;
        String sitephone;
        String biztype;
        String bizdomain;
        String agentname;
        String agentphone;
        Banks banks;

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

        public Banks getBanks() {
            return banks;
        }
    }

    public static class Banks{
        @SerializedName("ficode")
        String ficode;
        @SerializedName("bankname")
        String bankname;
        @SerializedName("merchantnumber")
        String merchantnumber;

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
