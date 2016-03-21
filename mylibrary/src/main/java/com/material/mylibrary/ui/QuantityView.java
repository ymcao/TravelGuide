package com.material.mylibrary.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.material.mylibrary.R;


public class QuantityView extends View {

	private Paint mPaint;
	private int addButtonBackground;
	private int addButtonTextColor;
	private String addButtonText;
	private int numberBckground;
	private int numberText=0;
	private int numberTextColor;
	private int removeButtonBackgroud;
	private String removeButtonText;
	private int removeButtonTextColor;
	//private boolean addTouched = false;
	//private boolean removeTouched = false;
	private int WIDTH;
	private int HEIGHT;
	private Rect baseRect;
	private Rect addRect;
	private Rect numberRect;
	private Rect removeRect;
	private int addLine;
	private int numberLine;
	private int removeLine;
	private FontMetricsInt fontMetrics;
	private onViewClickListener mListener;

	public interface onViewClickListener {
		void addButtonListener();

		void addRemoveListener();
	}

	public QuantityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e("YM", "QuantityView");
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setTextSize(36);
		fontMetrics = mPaint.getFontMetricsInt();
		TypedArray tArray = context.obtainStyledAttributes(attrs,
				R.styleable.QuantityView);
		addButtonBackground = tArray.getColor(
				R.styleable.QuantityView_qv_addButtonBackground, 0x000000);
		addButtonText = tArray
				.getString(R.styleable.QuantityView_qv_addButtonText);
		addButtonTextColor = tArray.getColor(
				R.styleable.QuantityView_qv_addButtonTextColor, 0x000000);
		numberBckground = tArray.getColor(
				R.styleable.QuantityView_qv_quantityBackground, 0x000000);
		numberTextColor = tArray.getColor(
				R.styleable.QuantityView_qv_quantityTextColor, 0x000000);
		removeButtonBackgroud = tArray.getColor(
				R.styleable.QuantityView_qv_removeButtonBackground, 0x000000);
		removeButtonTextColor = tArray.getColor(
				R.styleable.QuantityView_qv_removeButtonTextColor, 0x000000);
		removeButtonText = tArray
				.getString(R.styleable.QuantityView_qv_removeButtonText);
		tArray.recycle();

		// TODO Auto-generated constructor stub
	}

	private void initData() {
		addRect = new Rect(0, baseRect.centerY()-50, WIDTH/4, baseRect.centerY()+50);
		addLine = addRect.top
				+ (addRect.bottom - addRect.top - fontMetrics.bottom + fontMetrics.top)
				/ 2 - fontMetrics.top;
		numberRect = new Rect(WIDTH/4, baseRect.centerY()-50, (WIDTH/4)*3, baseRect.centerY()+50);
		numberLine = numberRect.top
				+ (numberRect.bottom - numberRect.top - fontMetrics.bottom + fontMetrics.top)
				/ 2 - fontMetrics.top;
		removeRect = new Rect((WIDTH/4)*3, baseRect.centerY()-50, WIDTH, baseRect.centerY()+50);
		removeLine = removeRect.top
				+ (removeRect.bottom - removeRect.top - fontMetrics.bottom + fontMetrics.top)
				/ 2 - fontMetrics.top;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			if (addRect.contains((int) event.getX(), (int) event.getY())) {
				//addTouched = true;
				numberText++;
				mListener.addButtonListener();
			} else if (removeRect.contains((int) event.getX(),
					(int) event.getY())) {
				numberText--;
				//removeTouched = true;
				mListener.addRemoveListener();
			}else{
				//addTouched = false;
				//removeTouched = false;
			}
			break;
		case MotionEvent.ACTION_CANCEL:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
				invalidate();
			break;
		}
		return true;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		Log.e("YM", "onLayout");
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//setMeasuredDimension(500, 400);
		Log.e("YM", "onMeasure");
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		Log.e("YM", "onSizeChanged");
		WIDTH=getWidth();
		HEIGHT=getHeight();
		baseRect = new Rect(0, 0, WIDTH, HEIGHT);
		initData();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.e("YM", "onDraw");
		//if (!removeTouched&&!addTouched) {
			//mPaint.setColor(0x50000000);
			//canvas.drawRect(baseRect, mPaint);
			/////////////////////////////////
			mPaint.setColor(addButtonBackground);
			canvas.drawRect(addRect, mPaint);
			mPaint.setColor(addButtonTextColor);
			canvas.drawText(addButtonText, addRect.centerX(), addLine, mPaint);
			// ///////////////////////////////////////
			mPaint.setColor(numberBckground);
			canvas.drawRect(numberRect, mPaint);
			// ////////////////////////////////////////
			mPaint.setColor(removeButtonBackgroud);
			canvas.drawRect(removeRect, mPaint);
			mPaint.setColor(removeButtonTextColor);
			canvas.drawText(removeButtonText, removeRect.centerX(), removeLine,
					mPaint);
			//mPaint.setColor(numberTextColor);
			//canvas.drawText("0", numberRect.centerX(), numberLine, mPaint);
		//} else {
			mPaint.setColor(numberTextColor);
			canvas.drawText(String.valueOf(numberText), numberRect.centerX(),
					numberLine, mPaint);
		//}
	}

	public void setOnViewClickListener(onViewClickListener mListener) {
		this.mListener = mListener;
	}

	public synchronized String getNumberText() {
		return String.valueOf(numberText);
	}
}
