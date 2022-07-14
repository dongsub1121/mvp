package com.mpas.mvp.merchant1.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesDetailModel {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("detail")
    String detail;
    @SerializedName("result")
    List<SalesDetailDB> salesDetailData;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public List<SalesDetailDB> getSalesDetailData() {
        return salesDetailData;
    }

    public static class SalesDetailDB {
        @SerializedName("transdate")
        String transdate;
        @SerializedName("transdatelabel")
        String transdatelabel;
        @SerializedName("ficode")
        String ficode;
        @SerializedName("bankname")
        String bankname;
        @SerializedName("usncnt")
        Integer usncnt;
        @SerializedName("uvnamt")
        Integer uvnamt;
        @SerializedName("trcnt")
        Integer trcnt;
        @SerializedName("tramt")
        Integer tramt;

        public String getTransdate() {
            return transdate;
        }

        public String getTransdatelabel() {
            return transdatelabel;
        }

        public String getFicode() {
            return ficode;
        }

        public String getBankname() {
            return bankname;
        }

        public Integer getUsncnt() {
            return usncnt;
        }

        public Integer getUvnamt() {
            return uvnamt;
        }

        public Integer getTrcnt() {
            return trcnt;
        }

        public Integer getTramt() {
            return tramt;
        }

        @NonNull
        @Override
        public String toString() {
            return "transdate:"+transdate
                    +"transdatelabel:"+transdatelabel
                    +"ficode:"+ficode
                    +"bankname:"+bankname
                    +"usncnt:"+usncnt
                    +"uvnamt:"+uvnamt
                    +"uvnamt:"+uvnamt
                    +"trcnt:"+trcnt
                    +"tramt:"+tramt;
        }
    }

    public int getTotalPrice() {
        int tot = 0;

        for(SalesDetailDB db : salesDetailData) {
            tot += db.tramt;
        }

        return tot;
    }

    public int getListSize() {
        return salesDetailData.size();
    }
}
