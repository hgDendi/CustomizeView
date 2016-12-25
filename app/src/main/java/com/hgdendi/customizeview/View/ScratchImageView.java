package com.hgdendi.customizeview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by dendich on 25/12/2016.
 */

public class ScratchImageView extends ImageView {

    private Paint mPaint;

    public ScratchImageView(Context context) {
        super(context);
    }

    public ScratchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScratchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
