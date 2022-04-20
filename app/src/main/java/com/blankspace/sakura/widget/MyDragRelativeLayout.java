package com.blankspace.sakura.widget;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

import com.blankspace.sakura.MainActivity;
import com.blankspace.sakura.R;


public class MyDragRelativeLayout extends RelativeLayout {


    private ViewDragHelper mDragger;
    private int mLeft;
    private int mTop;
    private boolean isFirst = false;
    private MainActivity mainActivity;
    private float startX;


    public MyDragRelativeLayout(Context context) {
        this(context, null);
    }

    public MyDragRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDragRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public int getViewHorizontalDragRange(View child) {
                //返回可拖动的子视图的水平运动范围(以像素为单位)的大小。
                //对于不能垂直移动的视图，此方法应该返回0。
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                //返回可拖动的子视图的竖直运动范围(以像素为单位)的大小。
                //对于不能垂直移动的视图，此方法应该返回0。
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //返回true表view示捕获当前touch到的
                return child.getId() == R.id.iv_drag;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left > getWidth() - child.getMeasuredWidth()) {
                    //超出左侧边界处理
                    left = getWidth() - child.getMeasuredWidth();
                } else if (left < 0) {
                    //超出右侧边界处理
                    left = 0;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top > getHeight() - child.getMeasuredHeight() - 150) {
                    //超出下边界处理
                    top = getHeight() - child.getMeasuredHeight() - 150;
                } else if (top < 200) {
                    //超出上边界处理
                    top = 200;
                }
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                float x = releasedChild.getX();
                float y = releasedChild.getY();
                if (x < (getMeasuredWidth() / 2f - releasedChild.getMeasuredWidth() / 2f)) {
                    x = 0;
                } else {
                    x = getMeasuredWidth() - releasedChild.getMeasuredWidth() - 50;
                }
                Log.e("dbj", "x=" + x + ",y=" + y);
                // 移动到指定位置
                mDragger.settleCapturedViewAt((int) x, (int) y);
                mLeft = (int) x;
                mTop = (int) y;
                saveState();
                invalidate();
                super.onViewReleased(releasedChild, xvel, yvel);
            }


        });
    }


    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        saveState();
        return super.onSaveInstanceState();

    }

    public void saveState() {
        mainActivity.mLeft = mLeft;
        mainActivity.mTop = mTop;
        mainActivity.isFirst = isFirst;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        restoreState();

    }

    public void restoreState() {
        this.mLeft = mainActivity.mLeft;
        this.mTop = mainActivity.mTop;
        this.isFirst = mainActivity.isFirst;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getId() == R.id.iv_drag) {
                if (!isFirst) {
                    //设置view的位置
                    isFirst = true;
                } else {
                    if (mTop != 0)
                        child.layout(mLeft, mTop, mLeft + child.getMeasuredWidth(), mTop + child.getMeasuredHeight());
                }

            }
        }
    }

    @Override
    public void computeScroll() {
        if (mDragger != null && mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = mDragger.shouldInterceptTouchEvent(ev);
        float curX = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = curX;
                break;


        }

        return result;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX = event.getX();
        if (Math.abs(curX - startX) < 0.1) {
            return super.onTouchEvent(event);
        } else {
            mDragger.processTouchEvent(event);
            return true;
        }
    }


}