package com.material.mylibrary.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;


class PullToRefreshLayout extends FrameLayout {

    private float mTouchStartY;

    private float mCurrentY;

    private View mChildView;

    private static DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(10);
    private float mPullHeight;
    private float mHeaderHeight;
    private float mFooterHeight;
    private boolean isRefreshing;
    private boolean isLoadMore;
    private boolean hasNext;
    private boolean DOWN=false;
    private boolean UP=false;


    private PullToRefreshListener mPullToRefreshListener;
    private PullToRefreshPullingListener mPullToRefreshPullingListener;
    private PullToLoadmoreListener mPullToLoadmoreListener;
    private PullToLoadmorePullingListener mPullToLoadmorePullingListener;
    private FrameLayout mHeader;
    private FrameLayout mFooter;

    public PullToRefreshLayout(Context context) {
        super(context);
        init();
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }

        if (getChildCount() > 1) {
            throw new RuntimeException("You can only attach one child");
        }


        mPullHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                150,
                getContext().getResources().getDisplayMetrics());

        mHeaderHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100,
                getContext().getResources().getDisplayMetrics());

        mFooterHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100,
                getContext().getResources().getDisplayMetrics());

        this.post(() -> {
            mChildView = getChildAt(0);
            addHeaderContainer();
            addFooterContainer();
        });

    }

    public void setHeaderView(View headerView) {
        post(() -> mHeader.addView(headerView));
    }

    public void setFooterView(View footerView) {

        post(() -> mFooter.addView(footerView));

    }

    public void setPullHeight(float pullHeight) {
        this.mPullHeight = pullHeight;
    }

    public void setHeaderHeight(float headerHeight) {
        this.mHeaderHeight = headerHeight;
    }

    public void setFooterHeight(float footrHeight) {
        this.mFooterHeight = footrHeight;
    }

    public void setHashNext(boolean hasNext){
        this.hasNext=hasNext;
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    private void addHeaderContainer() {
        FrameLayout headerContainer = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.TOP;
        headerContainer.setLayoutParams(layoutParams);
        mHeader = headerContainer;
        addViewInternal(mHeader);
        setUpChildViewAnimator(1);
    }

    private void addFooterContainer() {
        FrameLayout footerContainer = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.BOTTOM;
        footerContainer.setLayoutParams(layoutParams);
        mFooter = footerContainer;
        addViewInternal(mFooter);
        setUpChildViewAnimator(2);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setUpChildViewAnimator(int methodType) {
        if (mChildView == null) {
            return;
        }
        mChildView.animate().setInterpolator(new DecelerateInterpolator());

        mChildView.animate().setUpdateListener(animation -> {
                    int height = (int) mChildView.getTranslationY();
                    if(DOWN) {
                        mHeader.getLayoutParams().height = height;
                        mHeader.requestLayout();
                        if (mPullToRefreshPullingListener != null) {
                            mPullToRefreshPullingListener.onReleasing(this, height / mHeaderHeight);
                        }
                    }else if(UP) {

                        mFooter.getLayoutParams().height = -height;
                        mFooter.requestLayout();
                        if (mPullToLoadmorePullingListener != null) {
                            mPullToLoadmorePullingListener.onReleasing(this,-height / mFooterHeight);
                        }
                    }
                }
        );
    }

    private void addViewInternal(@NonNull View child) {
        super.addView(child);
    }

    @Override
    public void addView(@NonNull View child) {
        if (getChildCount() >= 1) {
            throw new RuntimeException("You can only attach one child");
        }
        mChildView = child;
        super.addView(child);
    }

    public boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof RecyclerView) {
                final RecyclerView recylerView = (RecyclerView) mChildView;
                int firstVisibleItem = ((LinearLayoutManager) recylerView.getLayoutManager()).findFirstVisibleItemPosition();
                return recylerView.getLayoutManager().getItemCount() > 0
                        && (firstVisibleItem > 0 || recylerView.getChildAt(0)
                        .getTop() < recylerView.getPaddingTop());
            } else if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mChildView, -1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, -1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (isRefreshing || isLoadMore) {
            return true;
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartY = e.getY();
                mCurrentY = mTouchStartY;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = e.getY();
                float dy = currentY - mTouchStartY;

                if (dy > 0 && !canChildScrollUp()) {
                    DOWN=true;
                    UP=false;
                    return DOWN;
                } else if (dy < 0 && isSlideToBottom()&& hasNext) {
                    DOWN=false;
                    UP=true;
                    return UP;
                }
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent e) {
        if (isRefreshing || isLoadMore) {
            return super.onTouchEvent(e);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = e.getY();

                if (mChildView != null) {

                    if(DOWN){
                        float dy = MathUtils.constrains(
                                0,
                                mPullHeight * 2,
                                mCurrentY - mTouchStartY);
                        float offsetY = decelerateInterpolator.getInterpolation(dy / mPullHeight / 2) * dy / 2;
                        mChildView.setTranslationY(
                                offsetY
                        );
                        if(mFooter!=null){
                           mFooter.setVisibility(View.GONE);
                        }
                        if(mHeader!=null) {
                            mHeader.getLayoutParams().height = (int) offsetY;
                            mHeader.requestLayout();
                        }
                        if (mPullToRefreshPullingListener != null) {
                            mPullToRefreshPullingListener.onPulling(this, offsetY / mHeaderHeight);
                        }
                    }else{
                        float dy = MathUtils.constrains(
                                0,
                                mFooterHeight,
                                -(mCurrentY - mTouchStartY)/2);
                        float offsetY = decelerateInterpolator.getInterpolation(dy / mFooterHeight / 2) * dy / 2;
                        mChildView.setTranslationY(
                                -offsetY
                        );
                        if(mFooter!=null){
                            mFooter.setVisibility(View.VISIBLE);
                        }
                        if(mFooter!=null) {
                            ////////////////////////////////////
                            mFooter.getLayoutParams().height = (int)(offsetY);
                            mFooter.requestLayout();
                        }
                        if (mPullToLoadmorePullingListener != null) {
                            mPullToLoadmorePullingListener.onPulling(this, offsetY / mFooterHeight);
                        }
                    }

                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mChildView != null) {
                    if (mChildView.getTranslationY() >= mHeaderHeight) {
                        mChildView.animate().translationY(mHeaderHeight).start();
                        isRefreshing = true;
                        if (mPullToRefreshListener != null) {
                            mPullToRefreshListener.onRefresh(this);
                        }
                    } else if (isSlideToBottom()) {
                        mChildView.animate().translationY(-mFooterHeight).start();
                        isLoadMore = true;
                        if (mPullToLoadmoreListener != null) {
                            mPullToLoadmoreListener.onLoadMore(this);
                        }
                    } else {
                        mChildView.animate().translationY(0).start();
                    }

                }
                return true;
            default:
                return super.onTouchEvent(e);
        }
    }

    /*下拉刷新数据*/
    public void setPullToRefreshListener(PullToRefreshListener pullToRefreshListener) {
        this.mPullToRefreshListener = pullToRefreshListener;
    }

    public void setPullingListener(PullToRefreshPullingListener pullingListener) {
        this.mPullToRefreshPullingListener = pullingListener;
    }

    ////
    public void setPullToLoadmoreListener(PullToLoadmoreListener pullToLoadmoreListener) {
        this.mPullToLoadmoreListener = pullToLoadmoreListener;
    }

    public void setPullingLoadmoreListener(PullToLoadmorePullingListener pullingLoadmoreListener) {
        this.mPullToLoadmorePullingListener = pullingLoadmoreListener;
    }

    public void finishRefreshing() {
        if (mChildView != null) {
            mChildView.animate().translationY(0).start();
        }
        isRefreshing = false;
    }

    public void finishLoadMore() {
        if (mChildView != null) {
            mChildView.animate().translationY(0).start();
        }
        isLoadMore = false;
    }
    interface PullToRefreshListener {

        void onRefresh(PullToRefreshLayout pullToRefreshLayout);
    }

    interface PullToLoadmoreListener {

        void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
    }

    interface PullToRefreshPullingListener {

        void onPulling(PullToRefreshLayout pullToRefreshLayout, float fraction);

        void onReleasing(PullToRefreshLayout pullToRefreshLayout, float fraction);

    }

    interface PullToLoadmorePullingListener {

        void onPulling(PullToRefreshLayout pullToRefreshLayout, float fraction);

        void onReleasing(PullToRefreshLayout pullToRefreshLayout, float fraction);

    }

    boolean isSlideToBottom() {

        if (mChildView instanceof RecyclerView) {
            final RecyclerView recylerView = (RecyclerView) mChildView;
            int lastVisibleItem = ((LinearLayoutManager) recylerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            int totalItemCount = recylerView.getLayoutManager().getItemCount();
            // 判断是否滚动到底部，并且是向右滚动
            if (lastVisibleItem == (totalItemCount - 1)) {
                return true;
            }
        } else if (mChildView instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) mChildView;
            if (absListView.getLastVisiblePosition() == (absListView.getAdapter().getCount() - 1)) {
                return true;
            }
        }
        return false;
    }
}
