package com.mpas.mvp.management.ui.sales;

import static com.mpas.mvp.merchant1.repository.ApiRepository.getInstance;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mpas.mvp.merchant1.model.SalesModel;
import com.mpas.mvp.merchant1.model.SalesDetailModel;
import com.mpas.mvp.merchant1.repository.ApiRepository;
import com.mpas.mvp.merchant1.repository.MerchantEntity;
import com.mpas.mvp.merchant1.repository.RoomDB;
import com.mpas.mvp.merchant1.util.TextConvert;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class SalesViewModel extends AndroidViewModel {

    private final RoomDB roomDB = RoomDB.getInstance(getApplication());

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SalesViewModel(@NonNull Application application) {

        super(application);
        salesDate.setValue(LocalDate.now().minusDays(1));
    }

    private final ApiRepository apiRepository = getInstance(getApplication());
    private final CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<List<SalesDetailModel.SalesDetailDB>> saleDetailDbMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<SalesModel.SaleDB>> salesDbMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> salesSumPriceMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<LocalDate> salesDate = new MutableLiveData<>();

    public MutableLiveData<LocalDate> getSalesDate() {
        return salesDate;
    }

    public MutableLiveData<List<SalesModel.SaleDB>> getSalesDb() {
        return salesDbMutableLiveData;
    }

    public MutableLiveData<Integer> getSalesSumPriceMutableLiveData() {
        return salesSumPriceMutableLiveData;
    }

    public MutableLiveData<List<SalesDetailModel.SalesDetailDB>> getSaleDetailDbMutableLiveData() {
        return saleDetailDbMutableLiveData;
    }

    public void setSalesDate(LocalDate localDate) {
        salesDate.setValue(localDate);
        Log.e("setSalesDate",localDate.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getSale_purchase(String biz, String mid, String date) {

        String day;

        Log.e("getSale_purchase",biz+"::"+mid+"::"+date);
        if(date == null || date.equals("")) {
            Log.e("getSale_purchase", "null or empty");
            day = TextConvert.localDateToString(Objects.requireNonNull(salesDate.getValue()));
        }else {
            Log.e("getSale_purchase", date);
            day = date;
        }

        disposable.add(apiRepository.getSale_purchase(biz, mid, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SalesDetailModel>(){

                    @Override
                    public void onSuccess(SalesDetailModel salseDetailModel) {
                        Log.e("viewmodel","onSuccess");
                        if ( salseDetailModel.getStatus().equals("200")) {
                            saleDetailDbMutableLiveData.setValue(salseDetailModel.getSalesDetailData());
                            salesSumPriceMutableLiveData.setValue(salseDetailModel.getTotalPrice());
                        } else {
                            Log.e("ErrorMessage" , salseDetailModel.getStatus() + ": "+ salseDetailModel.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }));

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getSales(String startDate, String endDate, String biz, String mid) {
       disposable.add(apiRepository.getSalesSummary(startDate,endDate,biz,mid)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(new DisposableSingleObserver<SalesModel>() {

           @Override
           public void onSuccess(SalesModel salesModel) {

               if ( salesModel.getStatus().equals("200")) {
                   for(SalesModel.SaleDB db :salesModel.getSaleDBList()) {
                       Log.e("getSalesonSuccess",db.toString());
                   }
                   salesDbMutableLiveData.setValue(salesModel.getSaleDBList());
               } else {
                   Log.e("Error",salesModel.getStatus()+":"+salesModel.getDetail()+":"+salesModel.getMessage());
               }
           }

           @Override
           public void onError(Throwable e) {

           }
       }));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void todaySet() {
        LocalDate localDate = LocalDate.now();
        String.format("%s년 %s월 %s일)", localDate.getYear(),localDate.getMonth(),localDate.getDayOfMonth());
    }

    private void preMerchant() {

    }

    @Override
    protected void onCleared() {
        super.onCleared();

        // Memory Initialize
        disposable.clear();
    }
}