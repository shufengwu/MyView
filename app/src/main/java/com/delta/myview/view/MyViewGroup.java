package com.delta.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;

/**
 * Created by Shufeng.Wu on 2017/8/21.
 */

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int count = getChildCount();
        int prevChildRight = 0;
        int prevChildBottom = 0;
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int width = MeasureSpec.makeMeasureSpec(270, EXACTLY);
            int height = MeasureSpec.makeMeasureSpec(150, AT_MOST);
            child.measure(width, height);
            if (prevChildRight + child.getMeasuredWidth() > getWidth()) {
                maxWidth = Math.max(maxWidth, prevChildRight);
                prevChildRight = 0;
                prevChildBottom += maxHeight;
                maxHeight = 0;
            } else {
                prevChildRight += child.getMeasuredWidth();
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
        }
        /*if(widthMode== AT_MOST&&heightMode==AT_MOST){
            setMeasuredDimension(Math.max(maxWidth, prevChildRight), maxHeight + prevChildBottom);
        }else if(widthMode== AT_MOST){
            setMeasuredDimension(Math.max(maxWidth, prevChildRight), heightSize);
        }else if(heightMode==AT_MOST){
            setMeasuredDimension(widthSize, maxHeight + prevChildBottom);
        }*/
        if (widthMode == EXACTLY && heightMode == EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else if (heightMode == EXACTLY) {
            setMeasuredDimension(Math.max(maxWidth, prevChildRight), heightSize);
        } else if (widthMode == EXACTLY) {
            setMeasuredDimension(widthSize, maxHeight + prevChildBottom);
        } else {
            setMeasuredDimension(Math.max(maxWidth, prevChildRight), maxHeight + prevChildBottom);
        }



    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int prevChildRight = 0;
        int prevChildBottom = 0;
        int maxHeight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (prevChildRight + child.getMeasuredWidth() > getWidth()) {
                prevChildRight = 0;
                prevChildBottom += maxHeight;
                maxHeight = 0;
            }

            child.layout(prevChildRight,
                    prevChildBottom,
                    prevChildRight + child.getMeasuredWidth(),
                    prevChildBottom + child.getMeasuredHeight());
            prevChildRight += child.getMeasuredWidth();
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
        }
    }


}
