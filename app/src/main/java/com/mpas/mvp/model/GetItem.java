package com.mpas.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetItem {

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
    @SerializedName("prev") @Expose
    private String prev;
    @SerializedName("rec") @Expose
    private Integer rec;
    @SerializedName("rem") @Expose
    private String rem;
    @SerializedName("type") @Expose
    private String type;
    @SerializedName("pack") @Expose
    private String pack;

    public Integer getPtz() {
        return ptz;
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
            System.out.println(price);
            return price;
        }
    }

    public class Pack {

  /*      private String authDate;
        private String authNum;
        private String authTime;
        private String authUniqueNum;
        private int price;
        private String result;
        private String resultCode;*/
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

        public String getAuthDate() {
            return app_tm;
        }

        public String getAuthNum() {
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

    public String getPack() {
        return pack;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrev() {
        return prev;
    }
}
