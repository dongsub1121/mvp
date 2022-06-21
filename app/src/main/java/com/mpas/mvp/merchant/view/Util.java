package com.mpas.mvp.merchant.view;

import android.content.Context;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class Util {

    // 이미지 로딩 중 보여줄 원형 프로그레스 바를 만들어 리턴하는 메서드
    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        // 길이, 높이 지정
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        // 프로그레스 바를 작동시키고
        progressDrawable.start();
        // 이 프로그레스 바를 리턴시킨다
        return progressDrawable;
    }
}
