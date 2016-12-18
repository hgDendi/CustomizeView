package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hgDendi on 17/12/2016.
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
        mBlackPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        mBlackPaint.setStyle(Paint.Style.FILL);


        mWhitePaint = new Paint();
        mWhitePaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
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
        Canvas canvas = mPicture.beginRecording(mWidth, mHeight);
        int radius = Math.min(mWidth, mHeight) / 2;

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
        mPicture.endRecording();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.rotate(mRotateDegrees, mWidth / 2, mHeight / 2);
        canvas.drawPicture(mPicture);

        mRotateDegrees += 5;
        invalidate();
    }
}
