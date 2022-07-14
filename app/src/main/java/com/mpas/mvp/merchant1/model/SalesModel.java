package com.mpas.mvp.merchant1.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesModel {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("detail")
    String detail;
    @SerializedName("result")
    List<SaleDB> saleDBList;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public List<SaleDB> getSaleDBList() {
        return saleDBList;
    }

    public static class SaleDB {
        @SerializedName("transdate")
        String transdate;
        @SerializedName("transdatelabel")
        String transdatelabel;
        @SerializedName("usncnt")
        Integer usncnt;
        @SerializedName("usnamt")
        Integer usnamt;
        @SerializedName("uvncnt")
        Integer uvncnt;
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

        public Integer getUsncnt() {
            return usncnt;
        }

        public Integer getUsnamt() {
            return usnamt;
        }

        public Integer getUvncnt() {
            return uvncnt;
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
            return "transdate:" +transdate
                    +"transdatelabel:" +transdatelabel
                    +"usncnt:" +usncnt
                    +"usnamt:" +usnamt
                    +"uvncnt:" +uvncnt
                    +"uvnamt:" +uvnamt
                    +"trcnt:" +trcnt
                    +"tramt:" +tramt;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
