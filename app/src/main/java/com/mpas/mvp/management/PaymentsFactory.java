package com.mpas.mvp.management;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.mpas.mvp.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentsFactory {

    ArrayList<Item> credit_items = new ArrayList<>();
    ArrayList<Item> link_items = new ArrayList<>();

    public PaymentsFactory() {

        setCredit_items();
        setLink_items();

    }

    public ArrayList<Item> getCredit_items() {
        return credit_items;
    }

    public ArrayList<Item> getLink_items() {
        return link_items;
    }

    private void setCredit_items() {
        credit_items.add(new PaymentsFactory.Item(R.drawable.samsung_pay,"삼성페이"));
        credit_items.add(new PaymentsFactory.Item(R.drawable.qr_pay,"QR결제"));
        credit_items.add(new PaymentsFactory.Item(R.drawable.account_pay,"계좌결제"));
        credit_items.add(new PaymentsFactory.Item(R.drawable.cash_bill,"현금영수증"));
    }

    private void setLink_items() {
        link_items.add(new PaymentsFactory.Item(R.drawable.sms_pay,"SMS"));
        link_items.add(new PaymentsFactory.Item(R.drawable.kakao_linkpay,"카카오톡"));
    }

    public static class Item {
        int resId;
        String text;

        public Item(int img, String item ) {

            resId = img;
            text = item;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
