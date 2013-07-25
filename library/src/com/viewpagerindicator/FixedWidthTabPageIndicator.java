package com.viewpagerindicator;

import android.content.Context;
import android.util.AttributeSet;

public class FixedWidthTabPageIndicator extends TabPageIndicator {

    private int mFixedTabWidth;

    public FixedWidthTabPageIndicator(Context context) {
        super(context);
    }

    public FixedWidthTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);
        mFixedTabWidth = (mTabLayout.getChildCount() > 1) ? MeasureSpec.getSize(widthMeasureSpec) / 2 : -1;

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
