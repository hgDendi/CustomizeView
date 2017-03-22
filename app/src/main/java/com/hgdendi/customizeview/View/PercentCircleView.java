package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dendich on 19/03/2017.
 */

public class PercentCircleView extends View {

    private int mPercentage = 50;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private Path mActivePath;
    private Path mArrowPath;
    private int mHeight;
    private int mWidth;
    private Point mCenterPoint;
    private float[] mArrowPosition;
    private float[] mArrowTan;
    private Matrix mMatrix;


    public PercentCircleView(Context context) {
        super(context);
        init();
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark));
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(50);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mActivePath = new Path();
        mPathMeasure = new PathMeasure();
        mArrowPath = new Path();
        mArrowPath.moveTo(0, 0);
        mArrowPath.lineTo(0, 30);
        mArrowPath.lineTo(100, 0);
        mArrowPath.lineTo(0, -30);
        mArrowPath.lineTo(0, 0);
        mMatrix = new Matrix();
        mArrowTan = new float[2];
        mArrowPosition = new float[2];

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setPercentage(mPercentage + 10);
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w - 2 * (int) (mPaint.getStrokeWidth() + 30 + 0.5);
        mHeight = h - 2 * (int) (mPaint.getStrokeWidth() + 30 + 0.5);
        Path path = new Path();
        mCenterPoint = new Point(w / 2, h / 2);
        path.addCircle(w / 2, h / 2, Math.min(mWidth, mHeight) / 2, Path.Direction.CW);
        mPathMeasure.setPath(path, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mActivePath.reset();
        float distance = mPathMeasure.getLength() * mPercentage / 100;
        mPathMeasure.getSegment(0, distance, mActivePath, true);
        canvas.drawPath(mActivePath, mPaint);

        canvas.drawText(String.valueOf(mPercentage),
                mCenterPoint.x - mPaint.measureText(String.valueOf(mPercentage)) / 2,
                mCenterPoint.y, mPaint);

        mPathMeasure.getPosTan(distance, mArrowPosition, mArrowTan);
        mMatrix.reset();

        canvas.save();
        float degrees = (float) (Math.atan2(mArrowTan[1], mArrowTan[0]) * 180.0 / Math.PI);
        mMatrix.postRotate(degrees);
        mMatrix.postTranslate(mArrowPosition[0], mArrowPosition[1]);
        canvas.setMatrix(mMatrix);
        canvas.drawPath(mArrowPath, mPaint);
        canvas.restore();
    }

    public void setPercentage(int percentage) {
        mPercentage = percentage % 100;
    }

}
