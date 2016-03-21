package com.material.mylibrary;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * Created by caoyamin on 15/9/8.
 */
public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Log.d("pathview", "fraction:" + String.valueOf(fraction));
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = 200 * fraction * 3;
        float y =  0.5f * 200 * (fraction * 3) * (fraction * 3);
        Point point = new Point(x, y);
        return point;
    }
}
