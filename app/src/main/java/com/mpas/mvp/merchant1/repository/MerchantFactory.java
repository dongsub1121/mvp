package com.mpas.mvp.merchant1.repository;

import android.content.Context;

import com.mpas.mvp.merchant1.model.MerchantInfoModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MerchantFactory {

    HashMap<String, MerchantInfoModel.Result> factory = new HashMap<>();


    public MerchantFactory() {
    }


    public  void add(MerchantInfoModel.Result infoModel) {
        factory.put(infoModel.getSitename(),infoModel);
    }

    public void delete(String siteName){
        factory.remove(siteName);
    }

    public  HashMap<String, MerchantInfoModel.Result> getFactory() {
        return factory;
    }

    public  Set<String> getKeySet() {
        return factory.keySet();
    }
}

