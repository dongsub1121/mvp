package com.mpas.mvp.merchant1.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mpas.mvp.merchant1.model.Merchant;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DatabaseRepository {

    private final Context mContext;
    private final RoomDB roomDB;

    public DatabaseRepository(Context context) {
        this.mContext = context;
        this.roomDB = RoomDB.getInstance(mContext);
    }

    public void toMerchantEntity(Merchant.Result merchant) {
        MerchantEntity mer = new MerchantEntity();
        mer.setBusinessNo(merchant.getBusinessnumber());
        mer.setMerchantNo(merchant.getSiteid());
        mer.setSitename(merchant.getSitename());
        mer.setSiteaddress(merchant.getSiteaddress());
    }

    public void setMainMerchant(MerchantEntity entity) {
        entity.setMaster(true);
    }

    @SuppressLint("CheckResult")
    public void AddMerchant(Merchant.Result merchant){
        MerchantEntity mer = new MerchantEntity();
        mer.setBusinessNo(merchant.getBusinessnumber());
        mer.setMerchantNo(merchant.getSiteid());
        mer.setSitename(merchant.getSitename());
        mer.setSiteaddress(merchant.getSiteaddress());

        roomDB.merchantDao().insert(mer).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Toast.makeText(mContext, "저장", Toast.LENGTH_SHORT).show();
                });
    }

    @SuppressLint("CheckResult")
    public void SetMerchant(String siteName){
        roomDB.merchantDao().loadById(siteName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_item->{
                    if(merchant_item.size() > 0){
                        String businessNo = merchant_item.get(0).getBusinessNo();
                        String MerchantNo = merchant_item.get(0).getMerchantNo();
                    } else {
                        Toast.makeText(mContext, "가맹점 없음", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void DeleteAll(){
        roomDB.merchantDao().deleteAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Toast.makeText(mContext, "DB 전체 삭제", Toast.LENGTH_SHORT).show();
                });
    }

    @SuppressLint("CheckResult")
    public ArrayList<String> getMerchantList() {
        ArrayList<String>arrayList = new ArrayList<>();
        roomDB.merchantDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_list->{
                    for( MerchantEntity merchantEntity : merchant_list) {
                        Log.e("getMerchantList",merchantEntity.getSitename());
                        arrayList.add(merchantEntity.getSitename());
                    }
                });
        return arrayList;
    }

}
