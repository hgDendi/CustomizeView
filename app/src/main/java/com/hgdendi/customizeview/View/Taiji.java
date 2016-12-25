package com.hgdendi.customizeview.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by hg.Dendi on 17/12/2016.
 */

public class Taiji extends View {

    private Region mClickRegion;
    private Path mClickRegionPath;
    private Paint mBlackPaint;
    private Paint mWhitePaint;
    private float mRotateDegrees;
    private Picture mPicture;
    private int mWidth;
    private int mHeight;
    private ValueAnimator valueAnimator;

    public Taiji(Context context) {
        super(context);
        init();
    }

    public Taiji(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Taiji(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBlackPaint = new Paint();
        mBlackPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        mBlackPaint.setStyle(Paint.Style.FILL);


        mWhitePaint = new Paint();
        mWhitePaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
        mWhitePaint.setStyle(Paint.Style.FILL);

        mRotateDegrees = 0;

        mPicture = new Picture();
        mClickRegion = new Region();
        mClickRegionPath = new Path();

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private Object value;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRotateDegrees = value * 360;
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(2000);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            valueAnimator.start();
        } else {
            valueAnimator.end();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        int radius = Math.min(mWidth, mHeight) / 2;
        mClickRegionPath.reset();
        mClickRegionPath.addCircle(mWidth / 2, mHeight / 2, radius, Path.Direction.CW);
        initPicture(radius);
        mClickRegion.setPath(mClickRegionPath, new Region(0, 0, w, h));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        return mClickRegion.contains(x, y) && super.onTouchEvent(event);
    }

    private void initPicture(int radius) {
        Canvas canvas = mPicture.beginRecording(mWidth, mHeight);
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF circleRect = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(circleRect, 270, 180, true, mBlackPaint);
        canvas.drawArc(circleRect, 90, 180, true, mWhitePaint);
        canvas.save();
        canvas.translate(0, -radius / 2);
        canvas.drawCircle(0, 0, radius / 2, mBlackPaint);
        canvas.drawCircle(0, 0, radius / 8, mWhitePaint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, radius / 2);
        canvas.drawCircle(0, 0, radius / 2, mWhitePaint);
        canvas.drawCircle(0, 0, radius / 8, mBlackPaint);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.rotate(mRotateDegrees, mWidth / 2, mHeight / 2);
        canvas.drawPicture(mPicture);

        mRotateDegrees -= 5;
        invalidate();
    }
}
