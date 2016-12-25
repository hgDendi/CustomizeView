package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by dendich on 25/12/2016.
 */

public class ScratchImageView extends ImageView {

    private Paint mPaint;
    private Path mScratchPath;
    private Paint mMaskPaint;
    private Canvas mMaskCanvas;
    private Bitmap mMaskBitmap;

    public ScratchImageView(Context context) {
        super(context);
        init();
    }

    public ScratchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScratchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);
        mMaskPaint.setStrokeWidth(20);
        mMaskPaint.setStyle(Paint.Style.STROKE);
        mMaskPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mScratchPath = new Path();
        mScratchPath.reset();

        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mMaskCanvas = new Canvas(mMaskBitmap);
        mMaskCanvas.drawColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mMaskCanvas.drawPath(mScratchPath, mMaskPaint);
        canvas.drawBitmap(mMaskBitmap,0,0,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                mScratchPath.reset();
                mScratchPath.moveTo(x, y);
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mScratchPath.lineTo(x, y);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
                return false;
        }
        return true;
    }
}
