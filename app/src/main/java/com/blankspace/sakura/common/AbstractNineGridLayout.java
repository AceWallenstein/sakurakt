package com.blankspace.sakura.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankspace.sakura.R;

import java.lang.reflect.Array;

/**
 * 抽象九宫格-具体的布局和测量在这里完成
 */
public abstract class AbstractNineGridLayout<T> extends ViewGroup {

    private static final int MAX_CHILDREN_COUNT = 9;
    private int itemWidth;
    private int itemHeight;
    private int horizontalSpacing;
    private int verticalSpacing;
    private boolean singleMode;
    private boolean fourGridMode;
    private int singleWidth;
    private int singleHeight;
    private boolean singleModeOverflowScale;

    public AbstractNineGridLayout(Context context) {
        this(context, null);
    }

    public AbstractNineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridLayout);
            int spacing = a.getDimensionPixelSize(R.styleable.NineGridLayout_spacing, 0);
            horizontalSpacing = a.getDimensionPixelSize(R.styleable.NineGridLayout_horizontal_spacing, spacing);
            verticalSpacing = a.getDimensionPixelSize(R.styleable.NineGridLayout_vertical_spacing, spacing);
            singleMode = a.getBoolean(R.styleable.NineGridLayout_single_mode, true);
            fourGridMode = a.getBoolean(R.styleable.NineGridLayout_four_gird_mode, true);
            singleWidth = a.getDimensionPixelSize(R.styleable.NineGridLayout_single_mode_width, 0);
            singleHeight = a.getDimensionPixelSize(R.styleable.NineGridLayout_single_mode_height, 0);
            singleModeOverflowScale = a.getBoolean(R.styleable.NineGridLayout_single_mode_overflow_scale, true);
            a.recycle();
        }

        //优先填充布局，再执行测量和绘制
        fillChildView();
    }

    /**
     * 设置显示的数量
     */
    public void setDisplayCount(int count) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setVisibility(i < count ? VISIBLE : GONE);
        }
    }

    /**
     * 设置单独布局的宽和高
     */
    public void setSingleModeSize(int w, int h) {
        if (w != 0 && h != 0) {
            this.singleWidth = w;
            this.singleHeight = h;
        }
    }

    /**
     * 一般用这个方法填充布局，每一个小布局的布局文件
     */
    protected void inflateChildLayout(int layoutId) {
        removeAllViews();
        for (int i = 0; i < MAX_CHILDREN_COUNT; i++) {
            LayoutInflater.from(getContext()).inflate(layoutId, this);
        }
    }

    /**
     * 返回每一个小布局的内部控件ID，用数组包装返回
     */
    @SuppressWarnings("unchecked")
    protected <V extends View> V[] findInChildren(int viewId, Class<V> clazz) {
        V[] result = (V[]) Array.newInstance(clazz, getChildCount());
        for (int i = 0; i < result.length; i++) {
            result[i] = (V) getChildAt(i).findViewById(viewId);
        }
        return result;
    }

    //子类去实现-填充布局文件
    protected abstract void fillChildView();

    //子类去实现-对布局文件赋值数据（一般专门去给adapter去调用的）
    public abstract void renderData(T data);


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();

        int notGoneChildCount = getNotGoneChildCount();

        if (notGoneChildCount == 1 && singleMode) {
            itemWidth = singleWidth > 0 ? singleWidth : widthSize;
            itemHeight = singleHeight > 0 ? singleHeight : widthSize;
            if (itemWidth > widthSize && singleModeOverflowScale) {
                itemWidth = widthSize;  //单张图片先定宽度。
                itemHeight = (int) (widthSize * 1f / singleWidth * singleHeight);  //根据宽度计算高度
            }
        } else {
            itemWidth = (widthSize - horizontalSpacing * 2) / 3;
            itemHeight = itemWidth;
        }

        //测量子布局
        measureChildren(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));


        if (heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        } else {
            notGoneChildCount = Math.min(notGoneChildCount, MAX_CHILDREN_COUNT);
            int height = ((notGoneChildCount - 1) / 3 + 1) * (itemHeight + verticalSpacing) - verticalSpacing + getPaddingTop() + getPaddingBottom();

            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        int notGoneChildCount = getNotGoneChildCount();
        int position = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            int row = position / 3;
            int col = position % 3;

            if (notGoneChildCount == 4 && fourGridMode) {
                row = position / 2;
                col = position % 2;
            }

            int x = col * itemWidth + getPaddingLeft() + horizontalSpacing * col;
            int y = row * itemHeight + getPaddingTop() + verticalSpacing * row;

            child.layout(x, y, x + itemWidth, y + itemHeight);

            //最多只摆放9个
            position++;
            if (position == MAX_CHILDREN_COUNT) {
                break;
            }
        }
    }

    //获取真正显示的子布局
    private int getNotGoneChildCount() {
        int childCount = getChildCount();
        int notGoneCount = 0;
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).getVisibility() != View.GONE) {
                notGoneCount++;
            }
        }
        return notGoneCount;
    }

}