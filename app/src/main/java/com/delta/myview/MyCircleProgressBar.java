package com.delta.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Shufeng.Wu on 2017/8/10.
 */

public class MyCircleProgressBar extends View {

    private int mCircleColor1;
    private int mCircleColor2;
    private float mSmallCirclePercent;
    private int mCircleSpeed;
    //private int mCircleWidth;
    private Paint mPaint;
    private boolean isNext;
    private int mProgress;

    public MyCircleProgressBar(Context context) {
        this(context, null);
    }

    public MyCircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyCircleProgressBar, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.MyCircleProgressBar_circleColor1:
                    mCircleColor1 = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.MyCircleProgressBar_circleColor2:
                    mCircleColor2 = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.MyCircleProgressBar_smallCirclePercent:
                    mSmallCirclePercent = typedArray.getFraction(attr, 1, 1, 0f);
                    break;
                case R.styleable.MyCircleProgressBar_circleSpeed:
                    mCircleSpeed = typedArray.getInt(attr, 1);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;

                    postInvalidate();
                    if (mProgress == 360) {
                        mProgress = 0;
                        isNext = !isNext;
                    }
                    try {
                        Thread.sleep(mCircleSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        //Log.i(TAG, "onDraw: "+mSmallCirclePercent);
        int radius = (int) ((float) center * mSmallCirclePercent);
        mPaint.setStrokeWidth(center - radius);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        if (isNext) {
            mPaint.setColor(mCircleColor1);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mCircleColor2);
            canvas.drawArc(rectF, 0, mProgress, false, mPaint);
        } else {
            mPaint.setColor(mCircleColor2);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mCircleColor1);
            canvas.drawArc(rectF, 0, mProgress, false, mPaint);
        }
    }
}
