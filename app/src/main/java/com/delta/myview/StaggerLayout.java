package com.delta.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Shufeng.Wu on 2017/8/11.
 */

public class StaggerLayout extends ViewGroup {
    public StaggerLayout(Context context) {
        this(context, null);
    }

    public StaggerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StaggerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
