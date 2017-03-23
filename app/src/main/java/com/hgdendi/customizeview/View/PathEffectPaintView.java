package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dendich on 23/03/2017.
 */

public class PathEffectPaintView extends AppCompatImageView {
    private Bitmap mContent;
    private Paint mPaint;
    private Canvas mCanvas;
    private PathEffect mPathEffect;

    public PathEffectPaintView(Context context) {
        super(context);
        init();
    }

    public PathEffectPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathEffectPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.BLACK);

        mPathEffect = new DashPathEffect(new float[]{5, 5}, 10);
        mPaint.setPathEffect(mPathEffect);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mContent = Bitmap.createBitmap(w, h,
                Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mContent);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mContent, 0, 0, null);
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
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        mCanvas.drawPath(mPath, mPaint);
        return true;
    }
}
