package com.delta.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by Shufeng.Wu on 2017/8/8.
 */

public class MyTitleView extends View implements View.OnClickListener {

    public static final String WSF = "wsf";
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private Rect mBound;
    private Paint mPaint;

    public MyTitleView(Context context) {
        this(context, null);
    }

    public MyTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "MyTitleView() called with: context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTitleView, defStyleAttr, 0);

        //遍历属性
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.MyTitleView_titleText:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.MyTitleView_titleTextColor:
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyTitleView_titleTextSize:
                    //???
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
        this.setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = getPaddingLeft() + getPaddingRight() + widthSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = getPaddingTop() + getPaddingBottom() + heightSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
            height = desired;
        }
        setMeasuredDimension(width, height);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]");
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout() called with: changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
    }

    @Override
    public void onClick(View v) {
        mTitleText = getRandomText();
        Log.i(TAG, "onClick: " + mTitleText);
        requestLayout(); // 执行onMeasure方法和onLayout方法
        MyTitleView.this.postInvalidate(); // 仅仅执行onDraw方法
        //MyTitleView.this.invalidate();

    }

    public String getRandomText() {
        return WSF + "-" + new Random().nextInt(100);
    }
}
