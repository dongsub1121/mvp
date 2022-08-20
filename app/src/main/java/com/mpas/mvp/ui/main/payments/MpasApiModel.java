package com.mpas.mvp.ui.main.payments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MpasApiModel {

    @SerializedName("tdx") @Expose
    private String tdx;
    @SerializedName("tkn") @Expose
    private String tkn;
    @SerializedName("mil") @Expose
    private String mil;
    @SerializedName("uid") @Expose
    private String uid;
    @SerializedName("nid") @Expose
    private String nid;
    @SerializedName("cid") @Expose
    private String cid;
    @SerializedName("did") @Expose
    private String did;
    @SerializedName("ono") @Expose
    private String ono;
    @SerializedName("ptz") @Expose
    private Integer ptz;
    @SerializedName("lev") @Expose
    private String lev;
    @SerializedName("hit") @Expose
    private String hit;
    @SerializedName("lke") @Expose
    private String lke;
    @SerializedName("pnm") @Expose
    private String pnm;
    @SerializedName("rec") @Expose
    private Integer rec;
    @SerializedName("rem") @Expose
    private String rem;
    @SerializedName("type") @Expose
    private String type;
    @SerializedName("pack") @Expose
    private List<Pack> pack;
    @SerializedName("prev") @Expose
    private List<Prev> prev;

    public List<Prev> getPrev() {return prev;}

    public List<Pack> getPack() {
        return pack;
    }

    public String getTdx() {
        return tdx;
    }

    public String getTkn() {
        return tkn;
    }

    public String getMil() {
        return mil;
    }

    public String getUid() {
        return uid;
    }

    public String getNid() {
        return nid;
    }

    public String getCid() {
        return cid;
    }

    public String getDid() {
        return did;
    }

    public String getOno() {
        return ono;
    }

    public Integer getPtz() {
        return ptz;
    }

    public String getLev() {
        return lev;
    }

    public String getHit() {
        return hit;
    }

    public String getLke() {
        return lke;
    }

    public String getPnm() {
        return pnm;
    }

    public Integer getRec() {
        return rec;
    }

    public String getRem() {
        return rem;
    }

    public String getType() {
        return type;
    }


    public class Prev {

        @SerializedName("menu") @Expose
        private String menu;
        @SerializedName("cnt") @Expose
        private int cnt;
        @SerializedName("price") @Expose
        private int price;

        public String getMenu() {
            return menu;
        }

        public int getCnt() {
            return cnt;
        }

        public int getPrice() {
            return price;
        }
    }

    public class Pack {

        @SerializedName("app_tm") @Expose
        private String app_tm;
        @SerializedName("app_no") @Expose
        private String app_no;
        @SerializedName("authTime") @Expose
        private String authTime;
        @SerializedName("tran_un") @Expose
        private String tran_un;
        @SerializedName("price") @Expose
        private int price;
        @SerializedName("res_cd") @Expose
        private String res_cd;
        @SerializedName("res_mg1") @Expose
        private String res_mg1;

        public String getApp_tm() {
            return app_tm;
        }

        public String getApp_no() {
            return app_no;
        }

        public String getAuthTime() {
            return authTime;
        }

        public String getTran_un() {
            return tran_un;
        }

        public int getPrice() {
            return price;
        }

        public String getRes_cd() {
            return res_cd;
        }

        public String getRes_mg1() {
            return res_mg1;
        }
    }
}