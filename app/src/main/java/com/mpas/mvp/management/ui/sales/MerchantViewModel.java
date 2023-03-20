package com.mpas.mvp.management.ui.sales;

import static com.mpas.mvp.merchant1.repository.ApiRepository.getInstance;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mpas.mvp.merchant1.model.Merchant;
import com.mpas.mvp.merchant1.repository.ApiRepository;
import com.mpas.mvp.merchant1.repository.MerchantEntity;
import com.mpas.mvp.merchant1.repository.MerchantFactory;
import com.mpas.mvp.merchant1.repository.RoomDB;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MerchantViewModel extends AndroidViewModel {

    private final ApiRepository apiRepository = getInstance(getApplication());
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MerchantFactory merchantFactory = new MerchantFactory();
    private final RoomDB roomDB = RoomDB.getInstance(getApplication());

    public MutableLiveData<MerchantFactory> merchant_factory = new MutableLiveData<>();
    public MutableLiveData<List<MerchantEntity>> merchantEntities = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>();

    public MutableLiveData<String> notificationMessage = new MutableLiveData<>();
    public MutableLiveData<String> successMessage = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<MerchantEntity> masterMerchant =  new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> merchantSignList = new MutableLiveData<>();
    public MutableLiveData<MerchantEntity> targetMerchant = new MutableLiveData<>();

    public MutableLiveData<MerchantEntity> getTargetMerchant() {
        return targetMerchant;
    }

    public MutableLiveData<String> getNotificationMessage() {
        return notificationMessage;
    }

    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MerchantViewModel(@NonNull Application application) {
        super(application);
        initialize();
        initMessage();
    }


    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<MerchantFactory> MerchantDownLoad() {
        return merchant_factory;
    }

    public MutableLiveData<MerchantEntity> getMasterMerchant() {
        return masterMerchant;
    }

    public MutableLiveData<ArrayList<String>> getMerchant_list() {
        return merchantSignList;
    }

    public MutableLiveData<List<MerchantEntity>> getMerchantAll() { return merchantEntities;}

    public void checkMerchant (String biz, String mid) {
        Log.e("checkMerchant",biz+" :: "+mid);
        disposable.add(apiRepository.getMerchantInfo(biz, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Merchant>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onSuccess(Merchant merchant) {
                        if (merchant.getResult().getSitename() == null) {
                            //message.setValue("가맹점 정보 오류");
                            errorMessage.setValue("가맹점 정보를 확인해 주세요");
                        } else {
                            MerchantEntity entity = toMerchantEntity(merchant);
                            roomDB.merchantDao().loadById(entity.getSitename())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(mer -> {
                                        if (mer.size() > 0) {
                                            errorMessage.setValue("가맹점이 이미 추가되어 있어요");
                                        } else {
                                            insertMerchant(entity);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    public MerchantEntity toMerchantEntity(Merchant merchant) {

        MerchantEntity mer = new MerchantEntity();
        mer.setBusinessNo(merchant.getResult().getBusinessnumber());
        mer.setMerchantNo(merchant.getResult().getSiteid());
        mer.setSitename(merchant.getResult().getSitename());
        mer.setSiteaddress(merchant.getResult().getSiteaddress());

        return mer;
    }

    @SuppressLint("CheckResult")
    public void insertMerchant(MerchantEntity entity) {
        if(masterMerchant.getValue() == null) {
            entity.setMaster(true);
        }else {
            entity.setMaster(false);
        }
        roomDB.merchantDao().insert(entity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Log.e("insertMerchant","ok");
                    successMessage.setValue("가맹점이 추가 되었어요");
                    refreshMerchant();
                });
    }

    @SuppressLint("CheckResult")
    public void initialize() {

        ArrayList<String> list = new ArrayList<>();

        roomDB.merchantDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entityList -> {

                    if(entityList.size() > 0) {
                        merchantEntities.setValue(entityList);
                        for (MerchantEntity e: entityList) {
                            Log.e("merchant_entity", e.toString());
                            list.add(e.getSitename());
                            if (e.getMaster()) {
                                masterMerchant.setValue(e);
                            }
                        }
                        setMerchantArray(entityList);
                        //merchantSignList.setValue(list);
                    }else {
                        Log.e("merchantDao().getAll()", "Null");
                        masterMerchant.setValue(null);
                        merchantEntities.setValue(null);
                        merchantSignList.setValue(null);
                        targetMerchant.setValue(null);
                    }
                });

        initMessage();
    }

    public void initMessage() {
        successMessage.setValue("");
        errorMessage.setValue("");
        notificationMessage.setValue("");
    }

    @SuppressLint("CheckResult")
    public void setMaster (MerchantEntity e) {
        roomDB.merchantDao().loadByIMaster(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity->{
                    for(MerchantEntity et : entity) {
                        Log.e("MasterEntity",et.getSitename());
                        Log.e("MasterEntity_is", String.valueOf(et.getMaster()));
                        roomDB.merchantDao().update(0, et.getSitename())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(()->{
                                    masterMerchant.setValue(null);
                            });
                    }
                    roomDB.merchantDao().update(1, e.getSitename())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(()->{
                            masterMerchant.setValue(e);
                    });
                });
    }

    @SuppressLint("CheckResult")
    public void deleteMerchant(String siteName) {
        if(targetMerchant.getValue() != null && siteName.equals(targetMerchant.getValue().getSitename())) {
            targetMerchant.setValue(null);
        }
        roomDB.merchantDao().delete(siteName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Toast.makeText(getApplication(), "가맹점 삭제", Toast.LENGTH_SHORT).show();
                    initialize();
                });


    }

    @SuppressLint("CheckResult")
    public void getMaster() {
        Log.e("getMaster","getMaster");
        roomDB.merchantDao().loadByIMaster(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant->{
                    if( merchant.size() > 0) {
                        Log.e("Master","Master");
                        masterMerchant.setValue(merchant.get(0));
                    }else {
                        Log.e("Master","Null");
                        masterMerchant.setValue(null);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void setTargetMerchant(String sign) {

        Log.e("changeMerchant : ",sign);
        roomDB.merchantDao().loadById(sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(changeMerchant->{
                    if (changeMerchant.size() > 0) {
                        targetMerchant.setValue(changeMerchant.get(0));
                        Log.e("setTarget",changeMerchant.get(0).toString());
                    }else {
                        targetMerchant.setValue(null);
                    }
                });
    }

    ///////////////////////////////////////////////

    @SuppressLint("CheckResult")
    public void SetMerchant(String siteName){
        roomDB.merchantDao().loadById(siteName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_item->{
                    if(merchant_item.size() > 0){
                        masterMerchant.setValue(merchant_item.get(0));
                        Log.e("SetMerchant",merchant_item.get(0).getSitename());
                    } else {
                    }
                });
    }

    public void refreshMerchant() {
        getMerchantList();
        getMerchantEntities();
        getMaster();
    }

    @SuppressLint("CheckResult")
    public void DeleteAll(){
        roomDB.merchantDao().deleteAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    Toast.makeText(getApplication(), "DB 전체 삭제", Toast.LENGTH_SHORT).show();
                });
    }

    @SuppressLint("CheckResult")
    public void getMerchantList() {
        ArrayList<String> arrayList = new ArrayList<>();

        roomDB.merchantDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_list->{
                    Log.e("가맹점 개수", String.valueOf(merchant_list.size()));
                    if( merchant_list.size() > 0) {
                        for( MerchantEntity merchantEntity : merchant_list) {
                            Log.e("getMerchantList",merchantEntity.getSitename());
                            arrayList.add(merchantEntity.getSitename());
                            if(merchantEntity.getMaster()){
                                masterMerchant.setValue(merchantEntity);
                                targetMerchant.setValue(merchantEntity);
                            }
                        }
                        merchantSignList.setValue(arrayList);
                    } else {
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getMerchantEntities(){
        roomDB.merchantDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(merchant_list->{
                    Log.e("getMerchantEntities count", String.valueOf(merchant_list.size()));
                    if( merchant_list.size() == 0) {
                        masterMerchant.setValue(null);
                        merchantEntities.setValue(null);
                    }
                    merchantEntities.setValue(merchant_list);
                    setMerchantArray(merchant_list);
                });
    }

    public void setMerchantArray(List<MerchantEntity> list) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();

        if (masterMerchant != null) {
            linkedHashSet.add(Objects.requireNonNull(masterMerchant.getValue()).getSitename());
        }
        if (list.size() > 0) {
            for(MerchantEntity entity : list) {
                linkedHashSet.add(entity.getSitename());
            }
        }
        ArrayList<String> stringList = new ArrayList<>(linkedHashSet);
        merchantSignList.setValue(stringList);
    }
    //////////////////////////////////////////////////////////////////
}
