package com.mpas.mvp.merchant1.repository;

import com.mpas.mvp.merchant1.model.Merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MerchantFactory {

    HashMap<String, Merchant.Result> factory = new HashMap<>();

    public MerchantFactory() {
    }

    public  void add(Merchant.Result merchant) {
        factory.put(merchant.getSitename(),merchant);
    }

    public void delete(String siteName){
        factory.remove(siteName);
    }

    public void deleteAll() {factory.clear();}

    public  HashMap<String, Merchant.Result> getFactory() {
        return factory;
    }

    public Merchant.Result getFactoryItem (String key) {
        return factory.get(key);
    }

    public void Initialize() {
        factory.clear();
    }

    public List<String> getDetail() {
        List<String> detail = new ArrayList<String>();

        for( String item : factory.keySet()){
            Objects.requireNonNull(detail).add((Objects.requireNonNull(factory.get(item)).toString()));
        }

        return detail;
    }

    public List<String> getKetSet() {

        return new ArrayList<String>(factory.keySet());
    }


}

