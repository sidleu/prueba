package com.reconosersdk.reconosersdk.utils.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomNotSwipeableViewPager extends ViewPager {

    Context context;

    public CustomNotSwipeableViewPager(@NonNull Context context) {
        super(context);
        if (isInEditMode()) {
            return;
        }
        this.context = context;
    }

    public CustomNotSwipeableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
