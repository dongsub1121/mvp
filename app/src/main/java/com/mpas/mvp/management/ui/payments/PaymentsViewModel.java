package com.mpas.mvp.management.ui.payments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mpas.mvp.management.ui.payments.pays.PayData;

import java.util.HashMap;

public class PaymentsViewModel extends ViewModel {

    private  MutableLiveData<String> successMessage;
    private  MutableLiveData<Integer> responseCode;
    private  MutableLiveData<PayData> responseData;
    private PayRepository payRepository;
    private PayData payData = null;

    public PaymentsViewModel() {
        successMessage = new MutableLiveData<>();
        responseCode = new MutableLiveData<>();
        responseData = new MutableLiveData<>();
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }
    public MutableLiveData<Integer> getResponseCode() { return responseCode; }
    public MutableLiveData<PayData> getResponseData() { return responseData; }

    public void goPay() {
        payRepository = new PayRepository(payData);
        payRepository.Request(onVanResponse);
    }

    public void setPayData(PayData payData) {
        this.payData = payData;
    }

    private void init() {
        payData = null;
        payRepository = null;
    }

    PayRequest.OnVanResponse onVanResponse = new PayRequest.OnVanResponse() {
        @Override
        public void onResponse(HashMap<String, String> responseMap) {

        }
    };
}