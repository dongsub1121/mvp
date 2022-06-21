package com.mpas.mvp.merchant.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "MerchantInfoTable")
public class MerchantInfoData implements Serializable {
    public MerchantInfoData() {
        super();
    }

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @ColumnInfo(name = "businessnumber")
    protected String businessnumber;
    @ColumnInfo(name = "siteid")
    protected String siteid;
    @ColumnInfo(name = "useyn")
    protected String useyn;
    @ColumnInfo(name = "sitebizno")
    protected String sitebizno;
    @ColumnInfo(name = "sitename")
    protected String sitename;
    @ColumnInfo(name = "siteaddress")
    protected  String siteaddress;
    @ColumnInfo(name = "merchantowner")
    protected String merchantowner;
    @ColumnInfo(name = "sitephone")
    protected String sitephone;
    @ColumnInfo(name = "biztype")
    protected String biztype;
    @ColumnInfo(name = "bizdomain")
    protected String bizdomain;
    @ColumnInfo(name = "agentname")
    protected String agentname;
    @ColumnInfo(name = "agentphone")
    protected String agentphone;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusinessnumber() {
        return businessnumber;
    }

    public void setBusinessnumber(String businessnumber) {
        this.businessnumber = businessnumber;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getUseyn() {
        return useyn;
    }

    public void setUseyn(String useyn) {
        this.useyn = useyn;
    }

    public String getSitebizno() {
        return sitebizno;
    }

    public void setSitebizno(String sitebizno) {
        this.sitebizno = sitebizno;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getSiteaddress() {
        return siteaddress;
    }

    public void setSiteaddress(String siteaddress) {
        this.siteaddress = siteaddress;
    }

    public String getMerchantowner() {
        return merchantowner;
    }

    public void setMerchantowner(String merchantowner) {
        this.merchantowner = merchantowner;
    }

    public String getSitephone() {
        return sitephone;
    }

    public void setSitephone(String sitephone) {
        this.sitephone = sitephone;
    }

    public String getBiztype() {
        return biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public String getBizdomain() {
        return bizdomain;
    }

    public void setBizdomain(String bizdomain) {
        this.bizdomain = bizdomain;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getAgentphone() {
        return agentphone;
    }

    public void setAgentphone(String agentphone) {
        this.agentphone = agentphone;
    }
}
