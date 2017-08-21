package com.delta.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.delta.myview.R;

/**
 * Created by Shufeng.Wu on 2017/8/8.
 */

public class MyImageView extends View {
    int width = 0;
    int height = 0;
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private Bitmap mImage;
    private int mImageScaleType;
    private Rect rect;
    private Paint mPaint;
    private Rect mTextRect;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyImageView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.MyImageView_titleText:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.MyImageView_titleTextColor:
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyImageView_titleTextSize:
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
                case R.styleable.MyImageView_imageScaleType:
                    mImageScaleType = typedArray.getInt(attr, 0);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mTextRect = new Rect();

        mPaint.setTextSize(mTitleTextSize);
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextRect);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);


        if (specMode == MeasureSpec.EXACTLY) { //判断是否是明确的值
            width = specSize;
        } else {
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextRect.width();
            if (specMode == MeasureSpec.AT_MOST) { // wrap_content
                int desire = Math.max(desireByImg, desireByTitle);
                width = Math.min(specSize, desire);
            }
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mTextRect.height() + mImage.getHeight();
            if (specMode == MeasureSpec.AT_MOST) { // wrap_content
                height = Math.min(specSize, desire);
            }
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //控件外矩形边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = width - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = height - getPaddingBottom();

        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mTextRect.width() > width) {
            //?????
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitleText, paint, (float) width - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(mTitleText, getPaddingLeft(), height - getPaddingBottom(), mPaint);
        } else {
            canvas.drawText(mTitleText, width / 2 - mTextRect.width() * 1.0f / 2, height - getPaddingBottom(), mPaint);
        }

        rect.bottom -= mTextRect.height();
        if (mImageScaleType == 0) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            //计算居中的矩形范围
            rect.left = width / 2 - mImage.getWidth() / 2;
            rect.right = width / 2 + mImage.getWidth() / 2;
            rect.top = (height - mTextRect.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (height - mTextRect.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }


    }
}
