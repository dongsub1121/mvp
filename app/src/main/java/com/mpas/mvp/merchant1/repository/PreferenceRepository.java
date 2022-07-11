package com.mpas.mvp.merchant1.repository;

import static com.mpas.mvp.merchant1.Config.*;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.mpas.mvp.merchant1.Config;
import com.mpas.mvp.merchant1.model.MerchantInfoModel;

public class PreferenceRepository {

    private SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public PreferenceRepository(Context context) {
        this.sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sharedPreferences.edit();
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences updateSharedPreference(MerchantInfoModel merchantInfoModel){

        MerchantInfoModel.Result result= merchantInfoModel.getResult();

        editor.putString(BUSINESS_ID,result.getBusinessnumber());
        editor.putString(MERCHANT_ID,result.getSiteid());
        editor.putString(MERCHANT_NAME,result.getSitename());
        editor.putString(MERCHANT_ADDRESS,result.getSiteaddress());
        editor.putString(MERCHANT_OWNER,result.getMerchantowner());
        editor.putString(MERCHANT_PHONE,result.getSitephone());
        editor.putString(MERCHANT_BUSINESS_TYPE,result.getBiztype());
        editor.putString(MERCHANT_BUSINESS_DOMAIN,result.getBizdomain());
        editor.putString(MERCHANT_AGENT,result.getAgentname());
        editor.putString(MERCHANT_AGENT_PHONE,result.getAgentphone());
        editor.apply();

        return sharedPreferences;
    }

    public String getBusinessId() {
        return sharedPreferences.getString(BUSINESS_ID,"");
    }

    public String getMerchantId() {
        return sharedPreferences.getString(MERCHANT_ID,"");
    }

    public boolean isMerchantEmpty() {
        return (sharedPreferences.getString(Config.MERCHANT_NAME,"").length() > 1);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for( String s : sharedPreferences.getAll().keySet()){
            sb.append(s+":").append(sharedPreferences.getString(s,"")).append('\n');
        }
        return sb.toString();
    }
}
