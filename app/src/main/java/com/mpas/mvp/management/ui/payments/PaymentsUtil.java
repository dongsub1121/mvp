package com.mpas.mvp.management.ui.payments;

import static com.mpas.mvp.management.ui.payments.pays.PayParser.*;
import static java.lang.String.format;

import android.annotation.SuppressLint;

import com.mpas.mvp.management.ui.payments.pays.PayParser;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class PaymentsUtil {

    public static String getPosUniCode() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat set = new SimpleDateFormat("ddhhmmss");
        Calendar time = Calendar.getInstance();

        return "POS0" + set.format(time.getTime());

    }

    public static String setIntegerToStringFormat(int value, int length) {
        String format = "%0" + length + "d";
        return format(format, value);
    }

    public static byte[] initializeMap(int length , Gravity type) {

        byte[] bytes = new byte[length];

        switch (type) {
            case Alpha_Numeric:
                Arrays.fill(bytes, (byte) 32);
                break;
            case Numeric:
                bytes = setIntegerToStringFormat(0, length).getBytes();
                break;
            case Alpha_Numeric_Special:
            case Hex:
                break;
        }
        return bytes;
    }

/*    public static byte[] setDataToByte (PayParser box, String Data) {
        byte[] bytes = new byte[box.getLength()];

        switch (box.getPutType()) {
            case Alpha_Numeric:
                Arrays.fill(bytes, (byte) 32);
                break;
            case Numeric:
                bytes = setIntegerToStringFormat(0, box.getLength()).getBytes();
                break;
            case Alpha_Numeric_Special:
            case Hex:
                break;
        }
        return bytes;
    }*/


}
