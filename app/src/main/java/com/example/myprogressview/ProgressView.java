package com.example.myprogressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by guoqiang on 2017/5/5.
 */

public class ProgressView extends View {
    private static final int[] SECTION_COLORS = {Color.RED, Color.YELLOW, Color.GREEN};
    private float maxCount;
    private float currentCount;
    private Paint mPaint;
    private int mWidth, mHeight;

    public ProgressView(Context context, AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ProgressView(Context context) {
        super(context);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int round = mHeight / 2;
        mPaint.setColor(Color.rgb(71, 76, 80));
        RectF rectBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBg, round, round, mPaint);
        mPaint.setColor(Color.GRAY);
        RectF rectBlackBg = new RectF(2, 2, mWidth - 2, mHeight - 2);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);
        float section = currentCount / maxCount;
        RectF rectProgressBg = new RectF(3, (mHeight - 3) * (1.0f - section), mWidth - 3, mHeight - 3);
        if (section <= 1.0f / 3.0f) {
            if (section != 0.0f) {
                mPaint.setColor(SECTION_COLORS[2]);
            } else {
                mPaint.setColor(Color.TRANSPARENT);
            }
        } else {
            int count = (section <= 1.0f / 3.0f * 2) ? 2 : 3;
            int[] colors = new int[count];
            float[] positions = new float[count];
            if (count == 2) {
                positions[0] = 0.0f;
                positions[1] = 1.0f - positions[0];
                //因为是竖着的进度条，所以需要从原数组的第二个位置开始
                System.arraycopy(SECTION_COLORS, 1, colors, 0, count);

            } else {
                positions[0] = 0.0f;
                positions[1] = (maxCount / 3) / currentCount;
                positions[2] = 1.0f - positions[0] * 2;
                System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
            }
            positions[positions.length - 1] = 1.0f;

            LinearGradient shader = new LinearGradient(3, (mHeight - 3) * (1.0f - section), mWidth - 3, mHeight - 3, colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(shader);
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }


    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }


    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }


}
