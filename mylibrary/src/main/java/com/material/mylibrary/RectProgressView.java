package com.material.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by caoyamin on 15/9/8.
 */
public class RectProgressView extends View {
    /**
     * mInitColor 进度条初始颜色
     */
    private int mInitColor;

    /**
     * mProgressColor 进度条进度颜色
     */
    private int mProgressColor;

    /**
     * mBorderWidth 边框宽度
     */
    private float mBorderWidth;

    /**
     * mBorderColor 边框颜色
     */
    private int mBorderColor;

    /**
     * mTextColor 文本颜色
     */
    private int mTextColor;

    /**
     * mTextSize 文本大小
     */
    private float mTextSize;
    /**
     * mText 文本内容
     */
    private String mText;

    /**
     * mProgress 进度
     */
    private int mProgress;

    /**
     * mMax 最大值
     */
    private int mMax;

    /**
     * mRadius 圆角大小
     */
    private float mRadius;
    private Paint mPaint;


    public RectProgressView(Context context) {
        super(context);
    }

    public RectProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initArray(context,attrs);
    }
    private void initArray(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.RectProgressView);
        this.mInitColor = array.getColor(
                R.styleable.RectProgressView_initColor, getContext()
                        .getResources()
                        .getColor(R.color.default_rect_initColor));
        this.mProgressColor = array.getColor(
                R.styleable.RectProgressView_progressColor,
                getContext().getResources().getColor(
                        R.color.default_rect_progressColor));
        this.mBorderColor = array.getColor(
                R.styleable.RectProgressView_borderColor,
                getContext().getResources().getColor(
                        R.color.default_rect_borderColor));
        this.mBorderWidth = array.getDimension(
                R.styleable.RectProgressView_borderWidth_1,
                getContext().getResources().getDimension(
                        R.dimen.default_rect_borderWidth));
        this.mTextColor = array.getColor(
                R.styleable.RectProgressView_rtextColor, getContext()
                        .getResources()
                        .getColor(R.color.default_rect_textColor));
        this.mTextSize = array.getDimension(
                R.styleable.RectProgressView_rtextSize,
                getContext().getResources().getDimension(
                        R.dimen.default_rect_textSize));
        this.mText = array.getString(R.styleable.RectProgressView_text);
        this.mProgress = array.getInteger(
                R.styleable.RectProgressView_progress,
                getContext().getResources().getInteger(
                        R.integer.default_rect_progress));
        this.mMax = array.getInteger(
                R.styleable.RectProgressView_rmax,
                getContext().getResources().getInteger(
                        R.integer.default_rect_max));
        this.mRadius = array.getDimension(
                R.styleable.RectProgressView_radius,
                getContext().getResources().getDimension(
                        R.dimen.default_rect_radius));
        array.recycle();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        initPaint();
        int width = getWidth();
        int height = getHeight();
        //绘制边框矩形
        if (this.mBorderWidth > 0.0f) {
            this.mPaint.setColor(this.mBorderColor);
            RectF borderRectf = new RectF(0, 0, width, height);
            canvas.drawRoundRect(borderRectf, this.mRadius, this.mRadius,
                    this.mPaint);
        }

        //绘制初始矩形
        this.mPaint.setColor(this.mInitColor);
        RectF initRectf = new RectF(this.mBorderWidth, this.mBorderWidth, width
                - this.mBorderWidth, height - this.mBorderWidth);
        canvas.drawRoundRect(initRectf, this.mRadius, this.mRadius, this.mPaint);

        //绘制进度矩形
        float current = (float) this.mProgress / (float) this.mMax;
        if (current > 0.0f) {
            this.mPaint.setColor(this.mProgressColor);
            RectF progressRectf = new RectF(this.mBorderWidth,
                    this.mBorderWidth, (width - this.mBorderWidth) * current,
                    height - this.mBorderWidth);
            if (this.mProgress == this.mMax) {
                canvas.drawRoundRect(progressRectf, this.mRadius, this.mRadius,
                        this.mPaint);
            } else {
                float[] radii = { this.mRadius, this.mRadius, 0, 0, 0, 0,
                        this.mRadius, this.mRadius };
                Path path = new Path();
                path.addRoundRect(progressRectf, radii, Path.Direction.CW);
                canvas.drawPath(path, this.mPaint);
            }
        }

        //绘制文本
        //if (this.mText!=null&&this.mText!="") {
        this.mPaint.setColor(this.mTextColor);
        this.mPaint.setTextSize(this.mTextSize);
        //Paint.FontMetricsInt fontMetrics = this.mPaint.getFontMetricsInt();
        //float baseline = initRectf.top
               // + (initRectf.bottom - initRectf.top - fontMetrics.bottom + fontMetrics.top)
               // / 2 - fontMetrics.top;
        // canvas.drawText(this.mText, 10, baseline, this.mPaint);
        canvas.drawText((int)(current*100)+"%",width/2,(getBottom()-getTop())/2, this.mPaint);
        //}

    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public int getmInitColor() {
        return mInitColor;
    }

    public void setmInitColor(int mInitColor) {
        this.mInitColor = mInitColor;
    }

    public int getmProgressColor() {
        return mProgressColor;
    }

    public void setmProgressColor(int mProgressColor) {
        this.mProgressColor = mProgressColor;
    }

    public float getmBorderWidth() {
        return mBorderWidth;
    }

    public void setmBorderWidth(float mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }

    public int getmBorderColor() {
        return mBorderColor;
    }

    public void setmBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public synchronized int getmProgress() {
        return mProgress;
    }

    public synchronized void setmProgress(int mProgress) {
        if (mProgress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (mProgress > this.mMax) {
            mProgress = this.mMax;
        }
        if (mProgress <= this.mMax) {
            this.mProgress = mProgress;
            postInvalidate();
        }
    }

    public synchronized int getmMax() {
        return mMax;
    }

    public synchronized void setmMax(int mMax) {
        if (mMax < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.mMax = mMax;
    }

    public float getmRadius() {
        return mRadius;
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }
}
