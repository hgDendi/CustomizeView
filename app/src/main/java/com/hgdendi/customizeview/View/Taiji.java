package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dendich on 17/12/2016.
 */

public class Taiji extends View {

    private Paint mBlackPaint;
    private Paint mWhitePaint;
    private float mRotateDegrees;
    private Picture mPicture;
    private int mWidth;
    private int mHeight;

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
        mBlackPaint.setColor(getResources().getColor(android.R.color.black));
        mBlackPaint.setStyle(Paint.Style.FILL);


        mWhitePaint = new Paint();
        mWhitePaint.setColor(getResources().getColor(android.R.color.white));
        mWhitePaint.setStyle(Paint.Style.FILL);

        mRotateDegrees = 0;

        mPicture = new Picture();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        initPicture();
    }

    private void initPicture() {
        mPicture.beginRecording(mWidth, mHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float radius;
        int width = getWidth();
        int height = getHeight();
        radius = Math.min(width, height) / 2;

        canvas.translate(width / 2, height / 2);
        canvas.rotate(mRotateDegrees);
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

        mRotateDegrees += 5;
        invalidate();
    }
}
