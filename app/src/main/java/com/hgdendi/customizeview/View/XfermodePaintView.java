package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hgdendi.customizeview.R;

/**
 * Created by dendich on 23/03/2017.
 */

public class XfermodePaintView extends AppCompatImageView {
    private Bitmap mSrc;
    private Bitmap mGrayMask;
    private Paint mPaint;
    private Canvas mCanvas;

    public XfermodePaintView(Context context) {
        super(context);
        init();
    }

    public XfermodePaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XfermodePaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSrc = BitmapFactory.decodeResource(getResources(), R.drawable.pic_wuyanzu);
        mGrayMask = Bitmap.createBitmap(mSrc.getWidth(), mSrc.getHeight(),
                Bitmap.Config.ARGB_8888);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mCanvas = new Canvas(mGrayMask);
        mCanvas.drawColor(Color.GRAY);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mSrc, 0, 0, null);
        canvas.drawBitmap(mGrayMask, 0, 0, null);
    }

    private Path mPath = new Path();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                mPath.reset();
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
        }
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
    }
}
