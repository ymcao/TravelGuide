package com.material.mylibrary.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by caoyamin on 15/9/15.
 */
public class CustomView_1 extends View {

    private float radius=0;
    // private float scaleRadius=0;
    private float currentRadius=0;
    private Paint paint;
    private Paint bgPaint;
    //private String text="";
    private float sweepAngle=0;
    private RectF rect;
    private RectF rectDraw;
    private boolean touched=false;
    private float Y;
    private float X;
    private ValueAnimator mScaleAnimator = new ValueAnimator();
    private ObjectAnimator animator;
    public CustomView_1(Context context) {
        super(context);
    }

    public CustomView_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        ////////////////////////////////ß
        bgPaint = new Paint();
        bgPaint.setColor(Color.DKGRAY);
        bgPaint.setStrokeWidth(1);
        bgPaint.setAntiAlias(true);
        this.bgPaint.setColor(Color.BLUE);
        this.bgPaint.setTextSize(20);
        this.bgPaint.setTextAlign(Paint.Align.CENTER);

        bgPaint.setStyle(Paint.Style.STROKE);


    }
    public void setPhase(float phase)
    {
        //sweepAngle=phase;
        //text=(int)(sweepAngle/360*100)+"";
        Y=phase;
        X=phase;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius=getMeasuredWidth()/8;
        currentRadius=radius-5.f;
        rect=new RectF(0,0,getWidth(),getHeight());
        rectDraw=new RectF(rect.centerX()-radius,rect.centerY()-radius,rect.centerX()+radius,rect.centerY()+radius);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (int) (rectDraw.top + (rectDraw.bottom - rectDraw.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top);
        //canvas.drawCircle(50,cy,radius,paint)

        if(touched) {
            currentRadius+=X;
            canvas.drawCircle(rectDraw.centerX(), rectDraw.centerY() + Y, radius/2, paint);
            canvas.drawCircle(rectDraw.centerX(), rectDraw.centerY() - Y, radius/2, paint);
            canvas.drawCircle(rectDraw.centerX()+X, rectDraw.centerY() , radius/2, paint);
            canvas.drawCircle(rectDraw.centerX()-X, rectDraw.centerY() , radius/2, paint);
            //canvas.drawCircle(rectDraw.centerX(), rectDraw.centerY() ,currentRadius, bgPaint);
        }else{
            currentRadius-=X;
            canvas.drawCircle(rectDraw.centerX(), rectDraw.centerY() - Y, radius/2, paint);
            canvas.drawCircle(rectDraw.centerX(), rectDraw.centerY() + Y, radius/2, paint);
            canvas.drawCircle(rectDraw.centerX()-X, rectDraw.centerY() , radius/2, paint);
            canvas.drawCircle(rectDraw.centerX()+X, rectDraw.centerY() , radius/2, paint);
            //canvas.drawCircle(rectDraw.centerX(), rectDraw.centerY() , currentRadius, bgPaint);
        }
        canvas.drawCircle(rectDraw.centerX(),rectDraw.centerY(), radius, paint);
       // canvas.drawCircle(rectDraw.centerX(),rectDraw.centerY(), radius+scaleRadius, paint);
        //canvas.drawArc(rectDraw, 0, sweepAngle, true, paint);
        //canvas.drawText(text + "%", rect.centerX(), baseline, bgPaint);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /*mScaleAnimator.setFloatValues(0, getMeasuredWidth() / 5);
        mScaleAnimator.setDuration(1000);
        mScaleAnimator.setRepeatCount(4);
        mScaleAnimator.setInterpolator(new DecelerateInterpolator());
        mScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleRadius = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mScaleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                scaleRadius = 0;
                invalidate();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });*/
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(rect.contains((int)event.getX(),(int)event.getY())){
                    touched=!touched;
                }
                break;
            case MotionEvent.ACTION_CANCEL:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if(touched/*&&!mScaleAnimator.isRunning()*/){
                    //mScaleAnimator.start();
                     animator = ObjectAnimator.ofFloat(CustomView_1.this, "phase", 0.0f, 150.0f);
                    animator.setDuration(3000);
                    animator.setInterpolator(new BounceInterpolator());
                    animator.start();
                }else{
                    animator = ObjectAnimator.ofFloat(CustomView_1.this, "phase", 150.0f, 0.0f);
                    animator.setDuration(1000);
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.start();
                }
                break;
        }
        return true;
    }
}
