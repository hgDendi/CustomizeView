package com.hgdendi.customizeview.ViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dendich on 21/03/2017.
 */

public class FakeScrollView extends ViewGroup {
    private static final String TAG = "FakeScrollView";

    private int mScreenHeight;

    public FakeScrollView(Context context) {
        super(context);
        init();
    }

    public FakeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FakeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = childCount * mScreenHeight;
        setLayoutParams(mlp);

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
        }
    }

    private int mLastY;
    private int mStartY;
    private int mEndY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "====onTouchEvent: ACTION_DOWN");
                mLastY = y;
                mStartY = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "====onTouchEvent: MOVE" + y);
                Log.d(TAG, "====onTouchEvent: getScrollY() = " + getScrollY());
                Log.d(TAG, "====onTouchEvent: getHeight() = " + getHeight());
                Log.d(TAG, "====onTouchEvent: getHeight() = " + mScreenHeight);
                int dy = y - mLastY;
                if (getScrollY() < 0
                        || getScrollY() > (getChildCount() - 1) * mScreenHeight) {
                    dy = 0;
                }
                scrollBy(0, -dy);
                Log.d(TAG, "====onTouchEvent: SCROLL -" + dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "====onTouchEvent: UOr" + y);
                mEndY = getScrollY();
                dy = mEndY - mStartY;
                if (Math.abs(dy) < mScreenHeight / 3) {
                    scrollBy(0, dy);
                } else {
                    scrollBy(0, dy > 0 ? mScreenHeight - dy : dy + mScreenHeight);
                }
                break;
        }
        postInvalidate();
        return true;
    }
}
