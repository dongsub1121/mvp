package com.mpas.mvp.model;

import com.google.gson.JsonObject;

public class TransSum {

    String status;
    String message;
    String detail;
    JsonObject result;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public JsonObject getResult() {
        return result;
    }

    class Result{

        String transdate;
        String transdatelabel;
        Integer usncnt;
        Integer usnamt;
        Integer uvncnt;
        Integer uvnamt;
        Integer trcnt;
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
    }
}
