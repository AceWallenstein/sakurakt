package com.blankspace.sakura.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class SppcLayout extends ViewGroup {
    private boolean canMeasureLayout = false;


    public SppcLayout(Context context) {
        super(context);
    }

    public SppcLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SppcLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!canMeasureLayout) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}