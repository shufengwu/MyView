package com.delta.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

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
        /*int count = getChildCount();
        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }*/
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /*int totalWidth = 0;
        int totalHeight = 0;
        int count = getChildCount();
        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            totalWidth += child.getMeasuredWidth();
            totalHeight = Math.max(totalHeight,child.getMeasuredHeight());
        }
        setMeasuredDimension(totalWidth,totalHeight);*/

        int count = getChildCount();
        int prevChildRight = 0;
        int prevChildBottom = 0;
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (prevChildRight + child.getMeasuredWidth() > getWidth()) {
                maxWidth = Math.max(maxWidth, prevChildRight + child.getMeasuredWidth());
                prevChildRight = 0;
                prevChildBottom += maxHeight;
                maxHeight = 0;
            }

            prevChildRight += child.getMeasuredWidth();
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
        }
        setMeasuredDimension(Math.max(maxWidth, prevChildRight), maxHeight + prevChildBottom);


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
