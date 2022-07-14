package com.mpas.mvp.merchant1.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TextConvert {

    public static String toPrice(Object num) {

        String var = null;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        if(num instanceof String) {
            var = numberFormat.format(Integer.parseInt((String)num));
        }else if(num instanceof Integer){
            var = numberFormat.format(num);
        }

        return  MessageFormat.format("{0}원", var);
    }

    public static String toCount(Object count) {

        return MessageFormat.format("{0}건",count);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate toDay() {

        return LocalDate.now();
    }

    public static String toString(LocalDate localDate) {
        return String.valueOf(localDate).replace("-","");
    }
}
