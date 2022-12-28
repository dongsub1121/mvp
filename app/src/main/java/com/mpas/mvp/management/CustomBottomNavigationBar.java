package com.mpas.mvp.management;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mpas.mvp.R;

public class CustomBottomNavigationBar extends BottomNavigationView {

    private final Path mPath = new Path();
    private final Paint mPaint = new Paint();
    private final Context mContext;

    private final int CURVE_CIRCLE_RADIUS = 190 / 2;

    private final Point mFirstCurveStartPoint = new Point();
    private final Point mFirstCurveEndPoint = new Point();
    private final Point mFirstCurveControlPoint1 = new Point();
    private final Point mFirstCurveControlPoint2 = new Point();

    private Point mSecondCurveStartPoint = new Point();
    private final Point mSecondCurveEndPoint = new Point();
    private final Point mSecondCurveControlPoint1 = new Point();
    private final Point mSecondCurveControlPoint2 = new Point();
    private int mNavigationBarWidth= 0;
    private int mNavigationBarHeight= 0;

    public CustomBottomNavigationBar(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomBottomNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CustomBottomNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public CustomBottomNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    private void init() {
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.white));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mNavigationBarWidth = w;
        mNavigationBarHeight = h;

        mFirstCurveStartPoint.set(mNavigationBarWidth / 2 - CURVE_CIRCLE_RADIUS * 2 - CURVE_CIRCLE_RADIUS / 3, 0);
        mFirstCurveEndPoint.set(mNavigationBarWidth / 2, CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4);
        mSecondCurveStartPoint = mFirstCurveEndPoint;
        mSecondCurveEndPoint.set(mNavigationBarWidth / 2 + CURVE_CIRCLE_RADIUS * 2 + CURVE_CIRCLE_RADIUS / 3, 0);

        mFirstCurveControlPoint1.set(
                mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4,
                mFirstCurveStartPoint.y
        );

        mFirstCurveControlPoint2.set(
                mFirstCurveEndPoint.x - CURVE_CIRCLE_RADIUS * 2 + CURVE_CIRCLE_RADIUS,
                mFirstCurveEndPoint.y
        );

        mSecondCurveControlPoint1.set(
                mSecondCurveStartPoint.x + CURVE_CIRCLE_RADIUS * 2 - CURVE_CIRCLE_RADIUS,
                mSecondCurveStartPoint.y
        );
        mSecondCurveControlPoint2.set(
                mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4),
                mSecondCurveEndPoint.y
        );

        mPath.reset();
        mPath.moveTo(0F, 0F);
        mPath.lineTo((float) mFirstCurveStartPoint.x, (float) mFirstCurveStartPoint.y);

        mPath.cubicTo(
                (float) mFirstCurveControlPoint1.x, (float) mFirstCurveControlPoint1.y,
                (float) mFirstCurveControlPoint2.x, (float) mFirstCurveControlPoint2.y,
                (float) mFirstCurveEndPoint.x, (float) mFirstCurveEndPoint.y
        );

        mPath.cubicTo(
                (float) mSecondCurveControlPoint1.x, (float) mSecondCurveControlPoint1.y,
                (float) mSecondCurveControlPoint2.x, (float) mSecondCurveControlPoint2.y,
                (float) mSecondCurveEndPoint.x, (float) mSecondCurveEndPoint.y
        );

        mPath.lineTo((float) mNavigationBarWidth, 0F);
        mPath.lineTo((float) mNavigationBarWidth, mNavigationBarHeight);
        mPath.lineTo(0F, (float) mNavigationBarHeight);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }

}
