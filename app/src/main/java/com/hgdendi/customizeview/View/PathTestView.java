package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hg.dendi on 18/12/2016.
 */

public class PathTestView extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Point controlPoint;

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

        controlPoint.x = (int) event.getX();
        controlPoint.y = (int) event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(0, 0);
        path.quadTo(controlPoint.x, controlPoint.y, mWidth, mHeight);

        canvas.drawPath(path, mPaint);

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        mPaint.setStrokeWidth(10);

        controlPoint = new Point(0, 0);
    }
}
