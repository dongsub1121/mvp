package com.mpas.mvp;

public class GetItem {

    private String tdx;
    private String tkn;
    private String mil;
    private String uid;
    private String nid;
    private String cid;
    private String did;
    private String ono;
    private String ptz;
    private String lev;
    private String hit;
    private String lke;
    private String pnm;
    private String prev;
    private String rec;
    private String rem;
    private String type;
    private String pack;

    public String getRec() {
        return rec;
    }

    public String getRem() {
        return rem;
    }

    public class Prev {
        private String menu;
        private int cnt;
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

    public class pack{

  /*      private String authDate;
        private String authNum;
        private String authTime;
        private String authUniqueNum;
        private int price;
        private String result;
        private String resultCode;*/

        private String app_tm;
        private String app_no;
        private String authTime;
        private String tran_un;
        private int price;
        private String res_cd;
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
