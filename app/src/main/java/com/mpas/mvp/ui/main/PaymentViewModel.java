package com.mpas.mvp.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpas.mvp.model.GetItem;
import com.mpas.mvp.PayUtil;
import com.mpas.mvp.PaydaPostMPMCommit;
import com.mpas.mvp.RetrofitAPI;
import com.mpas.mvp.VanRequest;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentViewModel extends ViewModel {
    private static final String TAG = PaymentViewModel.class.getSimpleName();
    private static String uid = "";
    private static String _uid = "";
    private final PayUtil.AuthItem item ;

    // TODO: Implement the ViewModel

    private final MutableLiveData<String> menu = new MutableLiveData<>();
    private final MutableLiveData<String> price = new MutableLiveData<>();
    private final MutableLiveData<String> authNum = new MutableLiveData<>();
    private final MutableLiveData<String> authUniqueNum = new MutableLiveData<>();
    private final MutableLiveData<String> authDate = new MutableLiveData<>();
    private final MutableLiveData<String> errMessage = new MutableLiveData<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<String> authMessage = new MutableLiveData<>();

    public MutableLiveData<String> getAuthMessage() { return authMessage; }
    public MutableLiveData<String> getAuthNum() {
        return authNum;
    }
    public MutableLiveData<String> getAuthUniqueNum() {
        return authUniqueNum;
    }
    public MutableLiveData<String> getMenu() { return menu; }
    public MutableLiveData<String> getPrice() { return price; }
    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }
    public MutableLiveData<String> getErrMessage() {
        return errMessage;
    }

    public PaymentViewModel() {
        item = new PayUtil.AuthItem();
    }

    public MutableLiveData<String> getAuthDate() {
        return authDate;
    }

    public void vanRequest(){
        Log.e(TAG,"vanRequest");
        VanRequest vanRequest = new VanRequest();
        vanRequest.execute(item,onVanResponse);

    }

    public void retrofitGetInfo(String _uid, String  uid){
        Log.e(TAG,"retrofitGetInfo");


        PaymentViewModel._uid = _uid;
        //PaymentViewModel._uid = "MzQ4NTUwOTk5EUGB";
        PaymentViewModel.uid = uid;

        Log.e("_uid : " , PaymentViewModel._uid);
        Log.e("uid : ", PaymentViewModel.uid);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://211.32.72.243:3000/")
                .baseUrl("http://61.33.183.227:3000/")    //http://61.33.183.227:3000/       "https://d-pay.mpas.co.kr/"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        retrofitAPI.posGetInfoBody(_uid, uid).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {

                String s = new Gson().toJson(response.body());
                String body = s.substring(1, s.length() - 1);
                Log.e("body",s);
                GetItem getItem = gson.fromJson(body, GetItem.class);
                String prevString = getItem.getPrev().substring(1, getItem.getPrev().length() - 1);
                GetItem.Prev prev = gson.fromJson(prevString, GetItem.Prev.class);

                try{
                    if(getItem.getRem().length() > 3) {
                        errMessage.setValue(getItem.getRem()+":"+getItem.getRec());
                    }else{
                        item.setAmount(prev.getPrice());
                        item.setType(getItem.getType());
                        item.setJobCode(getItem.getType());
                        item.setPay("ZeroPay");

                        Log.e("retrofitGetInfo",prev.getMenu()+":::"+prev.getCnt()+":::"+prev.getPrice());
                        menu.setValue(prev.getMenu());
                        menu.postValue(prev.getMenu());
                        price.setValue(String.valueOf(prev.getPrice()));
                        price.postValue(String.valueOf(prev.getPrice()));

                        if ( getItem.getType().equals("A")) {
                            authMessage.setValue("결제를 진행 하시겠어요?");
                        } else if ( getItem.getType().equals("C")) {
                            authMessage.setValue("결제 취소를 진행 하시겠어요?");
                        } else {
                            authMessage.setValue("승인 취소를 구분 할 수 없이 결제가 불가능 합니다");
                        }

                        if(getItem.getPack() != null){
                            String pac = getItem.getPack().substring(1,getItem.getPack().length()-1);
                            Log.e("pac",pac);
                            GetItem.pack pack = gson.fromJson(pac,GetItem.pack.class);
                            Log.e("pack.getAuthDate()",pack.getAuthDate());
                            Log.e("pack.getAuthNum()",pack.getAuthNum());
                            item.setAuthDate(pack.getAuthDate());
                            item.setAuthNum(pack.getAuthNum());
                        }

                        Log.e(TAG,item.toString());
                    }

                } catch (NullPointerException e) {
                    menu.setValue(getItem.getPnm());
                    menu.postValue(getItem.getPnm());
                    price.setValue(String.valueOf(getItem.getPtz()));
                    price.postValue(String.valueOf(getItem.getPtz()));
                    item.setAmount(getItem.getPtz());
                    item.setType(getItem.getType());
                    item.setJobCode(getItem.getType());
                    item.setPay("ZeroPay");

                    if ( getItem.getType().equals("A")) {
                        authMessage.setValue("결제를 진행 하시겠어요?");
                    } else if ( getItem.getType().equals("C")) {
                        authMessage.setValue("결제 취소를 진행 하시겠어요?");
                    } else {
                        authMessage.setValue("승인 취소를 구분 할 수 없이 결제가 불가능 합니다");
                    }

                    if(getItem.getPack() != null){
                        String pac = getItem.getPack().substring(1,getItem.getPack().length()-1);
                        Log.e("pac",pac);
                        GetItem.pack pack = gson.fromJson(pac,GetItem.pack.class);
                        Log.e("pack.getAuthDate()",pack.getAuthDate());
                        Log.e("pack.getAuthNum()",pack.getAuthNum());
                        item.setAuthDate(pack.getAuthDate());
                        item.setAuthNum(pack.getAuthNum());
                    }

                } catch (Exception e) {
                    errMessage.setValue("정보 불러오기 실패"+'\n'+body);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                Log.e("GET INFO FAIL", t.getMessage());
                errMessage.setValue(t.getMessage());
                Log.e("GET INFO FAIL", t.getMessage());
            }
        });
    }

    public void retrofitCommit(String pack){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://61.33.183.227:3000/")   //"http://61.33.183.227:3000/"   http://61.33.183.227:3000/       "https://d-pay.mpas.co.kr/"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        retrofitAPI.mpmCommit(_uid, uid, pack).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                String s = new Gson().toJson(response.body());
                String body = s.substring(1, s.length() - 1);
                Log.e("onResponse", body);
            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {

            }
        });
    }


    VanRequest.OnVanResponse onVanResponse = new VanRequest.OnVanResponse() {
        @Override
        public void onResponse(HashMap<String, String> responseMap) {

            try {
                PaydaPostMPMCommit mpmCommit = new PaydaPostMPMCommit();
                PaydaPostMPMCommit.ClientData clientData = new PaydaPostMPMCommit.ClientData();

                if (Objects.requireNonNull(responseMap.get("resultCode")).equals("0000") || Objects.requireNonNull(responseMap.get("resultCode")).equals("0100")) {

                    Gson gson1 = new Gson();

                    mpmCommit.setAuthNum((Objects.requireNonNull(responseMap.get("authNum"))).trim());
                    mpmCommit.setAuthUniqueNum((Objects.requireNonNull(responseMap.get("authUniqueNum"))).trim());
                    mpmCommit.setAuthDate(Objects.requireNonNull(responseMap.get("authDate")).substring(0, 6));
                    mpmCommit.setAuthTime((Objects.requireNonNull(responseMap.get("authDate"))).substring(6, 12));
                    mpmCommit.setResultCode(responseMap.get("resultCode"));
                    mpmCommit.setResult(responseMap.get("result"));
                    clientData.setIssuerCode(Objects.requireNonNull(responseMap.get("발급사코드")).trim());
                    clientData.setIssuerName(Objects.requireNonNull(responseMap.get("발급사명")).trim());
                    clientData.setPurchaseCode(Objects.requireNonNull(responseMap.get("매입사코드")).trim());
                    clientData.setPurchaseName(Objects.requireNonNull(responseMap.get("매입사명")).trim());

                    authNum.postValue(mpmCommit.getAuthNum());
                    authUniqueNum.postValue(mpmCommit.getAuthUniqueNum());
                    authDate.postValue((mpmCommit.getAuthDate())+" : "+mpmCommit.getAuthTime());

                    mpmCommit.setApp_tm((Objects.requireNonNull(responseMap.get("authDate"))).trim());
                    mpmCommit.setApp_no((Objects.requireNonNull(responseMap.get("authNum"))));
                    mpmCommit.setTran_un((Objects.requireNonNull(responseMap.get("authUniqueNum"))).trim());
                    mpmCommit.setIsur_cd(Objects.requireNonNull(responseMap.get("발급사코드")).trim());
                    mpmCommit.setIsur_nm(Objects.requireNonNull(responseMap.get("발급사명")).trim());
                    mpmCommit.setAcqu_cd((Objects.requireNonNull(responseMap.get("매입사코드"))).trim());
                    mpmCommit.setAcqu_nm((Objects.requireNonNull(responseMap.get("매입사명"))).trim());
                    mpmCommit.setRes_cd(responseMap.get("resultCode"));
                    mpmCommit.setRes_mg1(responseMap.get("result"));

                    mpmCommit.setPrice(item.getAmount());

                    String cld = gson1.toJson(clientData, PaydaPostMPMCommit.ClientData.class);
                    Log.e("clientData", cld);
                    mpmCommit.setClientData(clientData);

                    authNum.postValue(mpmCommit.getApp_no());
                    authUniqueNum.postValue(mpmCommit.getTran_un());
                    authDate.postValue((mpmCommit.getApp_tm()));

                    if (item.getJobCode().equals("8050")) {
                        successMessage.postValue("결제 완료");
                    } else if (item.getJobCode().equals("8051")) {
                        successMessage.postValue("결제 취소 완료");
                    }

                    String pack = "[" + gson1.toJson(mpmCommit, PaydaPostMPMCommit.class) + "]";
                    Log.e("pack", pack);
                    retrofitCommit(pack);

                } else {
                    errMessage.postValue("결제 실패");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}