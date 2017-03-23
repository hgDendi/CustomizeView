package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by dendich on 23/03/2017.
 */

public class SurfaceViewDemo extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private boolean mIsDrawing;

    public SurfaceViewDemo(Context context) {
        super(context);
        init();
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (mIsDrawing) {
                draw();
            }
            long end = System.currentTimeMillis();
            if (end - start < 100) {
                try {
                    Thread.sleep(100-(end-start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void draw() {
        mCanvas = mHolder.lockCanvas();
        // do drawing
        mCanvas.drawColor(Color.WHITE);
        mCanvas.drawPath(mPath, mPaint);
        mHolder.unlockCanvasAndPost(mCanvas);
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(mRunnable).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

}
