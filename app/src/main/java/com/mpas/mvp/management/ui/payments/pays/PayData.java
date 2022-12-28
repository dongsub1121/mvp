package com.mpas.mvp.management.ui.payments.pays;

import static com.mpas.mvp.management.ui.payments.pays.ProtocolConfig.Pays;
import static com.mpas.mvp.management.ui.payments.pays.ProtocolConfig.PayClass;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class PayData {

    int price = 0;
    int tax = 0;
    int noTax = 0;
    int tip = 0;
    String JobCode = null;
    Pays Pay = null;
    PayClass payClass = null;
    String tid = null;
    String biz = null;
    String creditMonth = null;
    String auth_date = null;
    String auth_num = null;
    String auth_unique = null;
    String item = null;
    String current = null;

    public PayData() {
        super();
    }

    public PayData(HashMap<String,byte[]> map){

    }

    public String getJobCode() {
        return JobCode;
    }

    public void setJobCode(String jobCode) {
        JobCode = jobCode;
    }

    public PayClass getPayClass() {
        return payClass;
    }

    public void setPayClass(PayClass payClass) {
        this.payClass = payClass;
    }

    public Pays getPay() {
        return Pay;
    }

    public void setPay(Pays pay) {
        Pay = pay;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getNoTax() {
        return noTax;
    }

    public void setNoTax(int noTax) {
        this.noTax = noTax;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public String getAuth_date() {
        return auth_date;
    }

    public void setAuth_date(String auth_date) {
        this.auth_date = auth_date;
    }

    public String getAuth_num() {
        return auth_num;
    }

    public void setAuth_num(String auth_num) {
        this.auth_num = auth_num;
    }

    public String getAuth_unique() {
        return auth_unique;
    }

    public void setAuth_unique(String auth_unique) {
        this.auth_unique = auth_unique;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getCreditMonth() {
        return creditMonth;
    }

    public void setCreditMonth(String creditMonth) {
        this.creditMonth = creditMonth;
    }
}
