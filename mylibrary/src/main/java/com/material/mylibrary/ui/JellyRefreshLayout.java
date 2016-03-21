package com.material.mylibrary.ui;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.material.mylibrary.R;


public class JellyRefreshLayout extends PullToRefreshLayout {

    JellyRefreshListener mJellyRefreshListener;
    private String mLoadingPullText = "松开下拉刷新...";
    private String mLoadingText = "正在努力加载中...";

    private int mLoadingTextColor;

    private int mJellyColor;

    public JellyRefreshLayout(Context context) {
        super(context);
        setupHeader();
        setupFooter();
    }

    public JellyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);
        setupHeader();
        setupFooter();
    }

    public JellyRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(attrs);
        setupHeader();
        setupFooter();
    }

    public JellyRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributes(attrs);
        setupHeader();
        setupFooter();
    }

    private void setAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.JellyRefreshLayout);
        try {
            Resources resources = getResources();
            mLoadingText = a.getString(R.styleable.JellyRefreshLayout_android_text);
            mLoadingTextColor = a.getColor(R.styleable.JellyRefreshLayout_android_textColor,
                    resources.getColor(android.R.color.white));
            mJellyColor = a.getColor(R.styleable.JellyRefreshLayout_jellyColor,
                    resources.getColor(android.R.color.holo_blue_bright));
        } finally {
            a.recycle();
        }
    }

    public void setRefreshListener(JellyRefreshListener jellyRefreshListener) {
        this.mJellyRefreshListener = jellyRefreshListener;
    }

    private void setupHeader() {
        if (isInEditMode()) {
            return;
        }

        @SuppressLint("InflateParams")
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.view_pull_header, null);
        final JellyView jellyView = (JellyView) headerView.findViewById(R.id.jelly);
        final TextView textLoading = (TextView) headerView.findViewById(R.id.text_loading);
        jellyView.setJellyColor(mJellyColor);
        textLoading.setText(mLoadingText);
        textLoading.setTextColor(mLoadingTextColor);
        final float headerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        setHeaderHeight(headerHeight);
        final float pullHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
        setPullHeight(pullHeight);
        setHeaderView(headerView);
        setPullToRefreshListener(
                new PullToRefreshListener() {
                    @Override
                    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                        if (mJellyRefreshListener != null) {
                            mJellyRefreshListener.onRefresh(JellyRefreshLayout.this);
                        }
                        textLoading.setText(mLoadingText);
                        jellyView.setMinimumHeight((int) (headerHeight));
                        ValueAnimator animator = ValueAnimator.ofInt(jellyView.getJellyHeight(), 0);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                jellyView.setJellyHeight((int) animation.getAnimatedValue());
                                jellyView.invalidate();
                            }
                        });
                        animator.setInterpolator(new OvershootInterpolator(3));
                        animator.setDuration(200);
                        animator.start();
                        pullToRefreshLayout.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {

                                        textLoading.setVisibility(View.VISIBLE);
                                    }
                                }, 120
                        );
                    }
                }
        );
        setPullingListener(new PullToRefreshLayout.PullToRefreshPullingListener() {
            @Override
            public void onPulling(PullToRefreshLayout pullToRefreshLayout, float fraction) {
                textLoading.setVisibility(View.VISIBLE);
                textLoading.setText(mLoadingPullText);
                jellyView.setMinimumHeight((int) (headerHeight * MathUtils.constrains(0, 1, fraction)));
                jellyView.setJellyHeight((int) (pullHeight * Math.max(0, fraction - 1)));
                jellyView.invalidate();
            }

            @Override
            public void onReleasing(PullToRefreshLayout pullToRefreshLayout, float fraction) {
                if (!pullToRefreshLayout.isRefreshing()) {
                    textLoading.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupFooter() {
        if (isInEditMode()) {
            return;
        }
        View footerView = LayoutInflater.from(getContext()).inflate(R.layout.view_pull_footer, null);
        final TextView  textLoading = (TextView) footerView.findViewById(R.id.text_loading);
        //ProgressBarCircular progressBarCircular = (ProgressBarCircular) footerView.findViewById(R.id.progress);
        //progressBarCircular.setBackgroundColor(getResources().getColor(R.color.light));
        setFooterView(footerView);
        final float headerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        setFooterHeight(headerHeight);

        setPullToLoadmoreListener(new PullToLoadmoreListener() {
            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (mJellyRefreshListener != null) {
                    mJellyRefreshListener.LoadMore(JellyRefreshLayout.this);
                }
            }
        });
        setPullingLoadmoreListener(new PullToLoadmorePullingListener() {

            @Override
            public void onPulling(PullToRefreshLayout pullToRefreshLayout, float fraction) {
                //if (finishIsSlideup()) {
                textLoading.setHeight((int) (headerHeight * MathUtils.constrains(0, 1, fraction)));
                // ObjectAnimator.ofFloat(iconLoading, "rotation", 0F, 360F).setDuration(3000).start();
                //}
            }

            @Override
            public void onReleasing(PullToRefreshLayout pullToRefreshLayout, float fraction) {

            }
        });
    }

    @Override
    public void setPullHeight(float pullHeight) {
        super.setPullHeight(pullHeight);
    }

    @Override
    public void setHeaderHeight(float headerHeight) {
        super.setHeaderHeight(headerHeight);
    }

    @Override
    public boolean isRefreshing() {
        return super.isRefreshing();
    }

    public boolean isLoadMore() {
        return super.isLoadMore();
    }

    @Override
    public void setHashNext(boolean hasNext) {
        super.setHashNext(hasNext);
    }

    @Override
    public void finishRefreshing() {
        super.finishRefreshing();
    }

    @Override
    public void finishLoadMore() {
        super.finishLoadMore();
    }

    public interface JellyRefreshListener {

        void onRefresh(JellyRefreshLayout jellyRefreshLayout);

        void LoadMore(JellyRefreshLayout jellyRefreshLayout);
    }
}
