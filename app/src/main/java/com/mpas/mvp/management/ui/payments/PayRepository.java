package com.mpas.mvp.management.ui.payments;

import static com.mpas.mvp.management.ui.payments.pays.ProtocolConfig.*;

import android.util.Log;

import com.mpas.mvp.management.ui.payments.pays.Credit;
import com.mpas.mvp.management.ui.payments.pays.NiceProtocol;
import com.mpas.mvp.management.ui.payments.pays.PayData;
import com.mpas.mvp.management.ui.payments.pays.PayMart;
import com.mpas.mvp.management.ui.payments.pays.ProtocolConfig;
import com.mpas.mvp.management.ui.payments.pays.ZeroPay;

import java.util.HashMap;

public class PayRepository {

    private PayMart payMart = null;
    private final PayData payData;
    private final NiceProtocol nice;
    private final PayRequest request;

    public PayRepository(PayData payData) {
        this.request = new PayRequest();
        this.payData = payData;
        setMart(payData.getPay());
        this.nice = new NiceProtocol(payMart.get());
    }

    private void setMart(Pays pays) {

        switch (pays) {
            case CREDIT:
                this.payMart = new Credit();
                break;
            case ZERO:
                this.payMart = new ZeroPay();
                break;
            case KAKAO:

            case SSG:

            case LPAY:

            case CASH:

            case PAYPRO:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pays);
        }

        payMart.set(payData);
    }


    public void Request(PayRequest.OnVanResponse onVanResponse) {

        Log.e("request", "in");
        PayRequest request = new PayRequest();
        request.execute(nice.get(), onVanResponse, payMart);

    }

}
