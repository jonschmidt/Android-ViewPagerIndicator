package com.viewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

public class FixedWidthTabPageIndicator extends TabPageIndicator {

    private final float mWeight;
    private int mFixedTabWidth;

    public FixedWidthTabPageIndicator(Context context) {
        this(context, null);
    }

    public FixedWidthTabPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.vpiFixedWidthIndicatorStyle);
    }

    public FixedWidthTabPageIndicator(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixedWidthTabPageIndicator, defStyle, 0);
        mWeight = a.getFloat(R.styleable.FixedWidthTabPageIndicator_tabWeight, .5F);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);
        mFixedTabWidth = (mTabLayout.getChildCount() > 1) ? (int) (MeasureSpec.getSize(widthMeasureSpec) * mWeight) : -1;

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentItem(mSelectedTabIndex);
        }
    }

    @Override
    protected TabViewBase createTabView() {
        return new FixedTabView(getContext());

    }

    private class FixedTabView extends TabViewBase {
        public FixedTabView(Context context) {
            super(context);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (mFixedTabWidth > 0) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(mFixedTabWidth, MeasureSpec.EXACTLY),
                        heightMeasureSpec);
            }
        }
    }

}
