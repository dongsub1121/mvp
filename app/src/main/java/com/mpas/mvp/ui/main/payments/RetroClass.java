package com.mpas.mvp.ui.main.payments;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class RetroClass extends ArrayList<MpasApiModel> {
    private ArrayList<MpasApiModel> apiModels;

    public ArrayList<MpasApiModel> getApiModels() {
        return apiModels;
    }

    @NonNull
    @Override
    public Stream<MpasApiModel> stream() {
        return super.stream();
    }
}
