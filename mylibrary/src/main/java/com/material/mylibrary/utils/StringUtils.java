package com.material.mylibrary.utils;

import java.util.Collection;

/**
 * Created by caoyamin on 15/11/6.
 */
public class StringUtils {
    public static boolean isEmpty(String objStr) {
        if (objStr == null || objStr == "") {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Collection collection) {

        return null == collection || collection.isEmpty();
    }
}
