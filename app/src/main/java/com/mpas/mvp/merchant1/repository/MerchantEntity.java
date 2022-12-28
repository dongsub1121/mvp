package com.mpas.mvp.merchant1.repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MerchantEntity {
    @PrimaryKey
    @NonNull
    private String sitename;
    @ColumnInfo
    private String businessNo;
    @ColumnInfo
    private String merchantNo;
    @ColumnInfo
    private String siteaddress;
    @ColumnInfo
    private Boolean master;

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    @NonNull
    public String getSitename() {
        return sitename;
    }

    public void setSitename(@NonNull String sitename) {
        this.sitename = sitename;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getSiteaddress() {
        return siteaddress;
    }

    public void setSiteaddress(String siteaddress) {
        this.siteaddress = siteaddress;
    }

    @NonNull
    public String toString() {

        return '\n'+"sitename : " + sitename + '\n'+
                "businessNo : " + businessNo + '\n'+
                "merchantNo : " + merchantNo + '\n'+
                "siteaddress : " + siteaddress +'\n'+
                "master : " + master;
    }

}
