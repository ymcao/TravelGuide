package com.material.mylibrary.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.material.mylibrary.ui.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyamin on 15/11/2.
 */
public class CategoryView extends LinearLayout {
    private OnViewSelectListener selectListener;
    private List<TextView> items = new ArrayList<TextView>();
    private List<View> lines = new ArrayList<View>();
    private Context mContext;
    private static int WIDTH = 0;

    public CategoryView(Context context) {
        super(context);
        mContext = context;
        setOrientation(VERTICAL);
    }

    public CategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(VERTICAL);
    }

    public void setAdater(CategoryAdapter adpater) {
        for (int i = 0; i < adpater.getAdapter().length; i++) {
            Text t = new Text(mContext);
            t.setText(adpater.getAdapter()[i]);
            t.setOnClickListener(new OnSelectListener(i));
            items.add(i, t);
            addView(t);
            Line v = new Line(mContext);
            lines.add(i, v);
            addView(v);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = getWidth();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected  void  setSelect(int position){
        for(int i=0;i<items.size();i++){
            if(i==position){
                ((Text)items.get(i)).setSelected(true);
                continue;
            }else{
                ((Text)items.get(i)).setSelected(false);
            }
        }
    }

    public class OnSelectListener implements OnClickListener {
        private int position;
        public OnSelectListener(int positon){
            this.position=positon;
        }
        @Override
        public void onClick(View v) {
              selectListener.onSelect(position);
             setSelect(position);
        }
    }

    public static class Line extends View {

        public Line(Context context) {
            super(context);
            init();
        }

        public Line(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(WIDTH, 2);
        }

        private void init() {
            setBackgroundColor(Color.GRAY);
        }
    }

    public static class Text extends TextView{

        public Text(Context context) {
            super(context);
            init();
        }

        public Text(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }
        private void init() {
            //setBackgroundResource(R.drawable.bg);
            setTextColor(Color.BLACK);
            setGravity(Gravity.CENTER_VERTICAL);
            setHeight(80);
            setWidth(WIDTH);
            setTextSize(15);
        }
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(WIDTH, 80);
        }
    }

    public void setSelectListener(OnViewSelectListener selectListener){
        this.selectListener=selectListener;
    }

    public interface  OnViewSelectListener{
         void onSelect(int position);
    }

}
