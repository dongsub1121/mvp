package com.mpas.mvp.merchant1.repository;

import android.app.Application;
import android.content.Context;

import com.mpas.mvp.merchant1.db.MerchantInfoDao;
import com.mpas.mvp.merchant1.db.MerchantInfoData;
import com.mpas.mvp.merchant1.db.MerchantInfoDataBase;
import com.mpas.mvp.merchant1.model.MerchantInfoModel;

public class DataRepository {

    private final MerchantInfoDao dao;
    private final MerchantInfoDataBase dataBase;
    private final Context mContext;

    public DataRepository(Application application) {

        this.dataBase = MerchantInfoDataBase.getInstance(application);
        this.dao = dataBase.merchantInfoDao();

        mContext = application;
    }

    public void insert(MerchantInfoModel.Result result){

        MerchantInfoData merchantInfoData1 = new MerchantInfoData();
        merchantInfoData1.setBusinessnumber(result.getBusinessnumber());
                merchantInfoData1.setSiteid(result.getSiteid());
                merchantInfoData1.setUseyn(result.getUseyn());
                merchantInfoData1.setSitebizno(result.getSitebizno());
                merchantInfoData1.setSitename(result.getSitename());
                merchantInfoData1.setSiteaddress(result.getSiteaddress());
                merchantInfoData1.setMerchantowner(result.getMerchantowner());
                merchantInfoData1.setSitephone(result.getSitephone());
                merchantInfoData1.setBiztype(result.getBiztype());
                merchantInfoData1.setBizdomain(result.getBizdomain());
                merchantInfoData1.setAgentname(result.getAgentname());
                merchantInfoData1.setAgentphone(result.getAgentphone());
                merchantInfoData1.setBanks(result.getBanks());


                dataBase.merchantInfoDao().insert(merchantInfoData1);

    }
}
