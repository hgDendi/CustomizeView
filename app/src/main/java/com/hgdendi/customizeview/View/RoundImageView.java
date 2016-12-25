package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by dendich on 25/12/2016.
 */

public class RoundImageView extends ImageView {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private Region mClickRegion;

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mClickRegion = new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = Math.min(w, h) / 2;

        Path clickPath = new Path();
        clickPath.addCircle(mRadius, mRadius, mRadius, Path.Direction.CW);
        mClickRegion.setPath(clickPath, new Region(0, 0, w, h));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isReadyToDraw()) {
            return;
        }
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }

    private boolean isReadyToDraw() {
        final Drawable drawable = getDrawable();
        if (drawable == null) {
            return false;
        }
        Bitmap bp = drawableToBitamp(drawable);
        BitmapShader bitmapShader = new BitmapShader(bp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = getScaleMatrix(bp);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        return mClickRegion.contains(x, y) && super.onTouchEvent(event);
    }

    @NonNull
    private Matrix getScaleMatrix(Bitmap bp) {
        float scale = Math.max(mWidth * 1.0f / bp.getWidth(), mHeight
                * 1.0f / bp.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        return matrix;
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
