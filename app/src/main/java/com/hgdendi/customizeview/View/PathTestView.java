package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by hg.dendi on 18/12/2016.
 */

public class PathTestView extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Point controlPoint;
    private Path mPath;

    public PathTestView(Context context) {
        super(context);
        init();
    }

    public PathTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "onTouchEvent: cancel");
                return false;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }


        controlPoint.x = (int) event.getX();
        controlPoint.y = (int) event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(0, mHeight / 2);
        mPath.quadTo(controlPoint.x, controlPoint.y, mWidth, mHeight / 2);

        canvas.drawPath(mPath, mPaint);

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        mPaint.setStrokeWidth(10);

        mPath = new Path();

        controlPoint = new Point(0, 0);
    }
}
