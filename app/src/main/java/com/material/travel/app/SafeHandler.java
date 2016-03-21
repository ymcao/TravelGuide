package com.material.travel.app;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by caoyamin on 15/11/26.
 */
public class SafeHandler extends Handler  {
        private final WeakReference<Activity> mActivity;
        public SafeHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if(mActivity.get() == null) {
                return;
            }
        }
}
