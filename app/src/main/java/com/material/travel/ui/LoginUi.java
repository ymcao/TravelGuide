package com.material.travel.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.material.mylibrary.utils.DensityUtils;
import com.material.travel.R;

/**
 * Created by caoyamin on 15/12/9.
 */
public class LoginUi extends RelativeLayout {

    private Scroller mScroller;
    /** 屏幕高度  */
    private int mScreenHeight = 0;
    /** 屏幕宽度  */
    private int mScreenWidth = 0;
    /** 点击时候Y的坐标*/
    private int downY = 0;
    /** 拖动时候Y的坐标*/
    private int moveY = 0;
    /** 拖动时候Y的方向距离*/
    private int scrollY = 0;
    /** 松开时候Y的坐标*/
    private int upY = 0;
    /** 是否在移动*/
    private Boolean isMoving = false;
    /** 布局的高度*/
    private int viewHeight = 0;
    /** 是否打开*/
    public boolean isShow = false;
    /** 是否可以拖动*/
    public boolean mEnabled = true;
    /** 点击外面是否关闭该界面*/
    public boolean mOutsideTouchable = true;
    /** 上升动画时间 */
    private int mDuration = 800;
    public LoginUi(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoginUi(Context context) {
        super(context);
        init(context);
    }
    void init(Context context){
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setFocusable(true);
        mScroller=new Scroller(context);
        mScreenWidth= DensityUtils.getWindowWidth(context);
        mScreenHeight=DensityUtils.getWindowHeight(context);
        // 背景设置成透明
        this.setBackgroundColor(Color.argb(0, 0, 0, 0));
        final View view = LayoutInflater.from(context).inflate(R.layout.view_login,null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,DensityUtils.dp2px(context,200.0f));
        addView(view, params);
        view.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                viewHeight = view.getHeight();
            }
        });
        LoginUi.this.scrollTo(0, mScreenHeight);
        ImageView btn_close = (ImageView)view.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!mEnabled){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                Log.d("", "downY = " + downY);
                //如果完全显示的时候，让布局得到触摸监听，如果不显示，触摸事件不拦截，向下传递
                if(isShow){
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) event.getY();
                scrollY = moveY - downY;
                //向下滑动
                if (scrollY > 0) {
                    if(isShow){
                        scrollTo(0, -Math.abs(scrollY));
                    }
                }else{
                    if(mScreenHeight - this.getTop() <= viewHeight && !isShow){
                        scrollTo(0, Math.abs(viewHeight - scrollY));
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                upY = (int) event.getY();
                if(isShow){
                    if( this.getScrollY() <= -(viewHeight /2)){
                        startMoveAnim(this.getScrollY(),-(viewHeight - this.getScrollY()), mDuration);
                        isShow = false;
                        Log.d("isShow", "false");
                    } else {
                        startMoveAnim(this.getScrollY(), -this.getScrollY(), mDuration);
                        isShow = true;
                        Log.d("isShow", "true");
                    }
                }
                Log.d("this.getScrollY()", ""+this.getScrollY());
                changed();
                break;
            case MotionEvent.ACTION_OUTSIDE:
                Log.d("", "ACTION_OUTSIDE");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
    /**
     * 拖动动画
     * @param startY
     * @param dy  移动到某点的Y坐标距离
     * @param duration 时间
     */
    public void startMoveAnim(int startY, int dy, int duration) {
        isMoving = true;
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 更新界面
            postInvalidate();
            isMoving = true;
        } else {
            isMoving = false;
        }
        super.computeScroll();
    }

    /** 开打界面 */
    public void show(){
        if(!isShow && !isMoving){
            LoginUi.this.startMoveAnim(-viewHeight,   viewHeight, mDuration);
            isShow = true;
            Log.d("isShow", "true");
            changed();
        }
    }

    /** 关闭界面 */
    public void dismiss(){
        if(isShow && !isMoving){
            LoginUi.this.startMoveAnim(0, -viewHeight, mDuration);
            isShow = false;
            Log.d("isShow", "false");
            changed();
        }
    }
    /** 是否打开 */
    public boolean isShow(){
        return isShow;
    }

    /** 获取是否可以拖动*/
    public boolean isSlidingEnabled() {
        return mEnabled;
    }
    /**
     * 显示状态发生改变时候执行回调借口
     */
    public void changed(){
        if(statusListener != null){
            if(isShow){
                statusListener.onShow();
            }else{
                statusListener.onDismiss();
            }
        }
    }
    /** 设置是否可以拖动*/
    public void setSlidingEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    /**
     * 设置监听接口，实现遮罩层效果
     */
    public void setOnStatusListener(onStatusListener listener){
        this.statusListener = listener;
    }

    public void setOutsideTouchable(boolean touchable) {
        mOutsideTouchable = touchable;
    }
    /** 监听接口*/
    public onStatusListener statusListener;

    /**
     * 监听接口，来在主界面监听界面变化状态
     */
    public interface onStatusListener{
        /**  开打状态  */
        public void onShow();
        /**  关闭状态  */
        public void onDismiss();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
    }
}
