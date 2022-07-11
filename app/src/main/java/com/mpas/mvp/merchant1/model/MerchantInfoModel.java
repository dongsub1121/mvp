package com.mpas.mvp.merchant1.model;

import androidx.annotation.NonNull;

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

        List<BanksModel> banks;

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

        public List<BanksModel> getBanks() {
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

    }



}
