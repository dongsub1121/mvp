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
    private String MerchantNo;
    private String siteaddress;


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
        return MerchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        MerchantNo = merchantNo;
    }

    public String getSiteaddress() {
        return siteaddress;
    }

    public void setSiteaddress(String siteaddress) {
        this.siteaddress = siteaddress;
    }

}
