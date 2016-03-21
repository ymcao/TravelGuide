package com.material.travel.view;

import android.content.Context;

/**
 * Created by caoyamin on 15/9/23.
 */
public interface BaseViewInterface {
    /**
     * Get a {@link android.content.Context}.
     */
    Context getContext();
    void showLoading();
    void hideLoading();
}
