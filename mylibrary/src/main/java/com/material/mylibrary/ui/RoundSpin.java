package com.material.mylibrary.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.material.mylibrary.R;


/**
 * Created by caoyamin on 15/7/23.
 */
public class RoundSpin extends View {

    private Paint paint;
    private int roundColor;//
    private int spinRoundColor;
    private int textColor;
    private float textSize;
    private int progress;
    private float roundWidth;
    private int max;

    public RoundSpin(Context context) {
        this(context, null);

    }

    public RoundSpin(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public RoundSpin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundPin);
        roundColor = mTypedArray.getColor(R.styleable.RoundPin_roundColor, Color.RED);
        spinRoundColor = mTypedArray.getColor(R.styleable.RoundPin_roundProgressColor, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.RoundPin_roundTextColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundPin_roundTextSize, 24);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundPin_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.RoundPin_roundMax, 100);
        //textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        //style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth()/2; //获取圆心的x坐标
        int radius = (int) (centre - roundWidth/2); //圆环的半径
        //设置进度是实心还是空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(spinRoundColor);  //设置进度的颜色
        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);  //用于定义的圆弧的形状和大小的界限

        paint.setStyle(Paint.Style.STROKE);
        if(progress !=0)
            canvas.drawArc(oval, 0, 360 * progress / max, true, paint);  //根据进度画圆弧

        /**
         * 画中间的圆环
         */
        paint.setColor(roundColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.FILL); //设置空心
        //paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环
        /**
         * 画进度百分比
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        int percent = (int)(((float)progress / (float)max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间

        if( percent != 0){
            canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize/2, paint); //画出进度百分比
        }
    }

    public  synchronized  void setProgress(int progress){
        if(progress<0){
            return;
        }
        if(progress > max){
            progress = max;
        }
        if(progress <= max){
            this.progress = progress;
            postInvalidate();
        }
    }
    public synchronized  int getProgress(){
        return progress;
    }
    public synchronized  void setMax(int max){
        this.max=max;
    }
    public synchronized  int getMax(){
        return max;
    }
}
