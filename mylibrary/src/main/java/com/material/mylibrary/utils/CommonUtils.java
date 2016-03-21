package com.material.mylibrary.utils;

import java.util.List;

/**
 * Created by caoyamin on 15/9/16.
 */
public class CommonUtils {

    public static boolean isEmpty(List lists){

        if(lists!=null&&lists.size()>0)
            return false;
        return true;
    }
    public static boolean isEmpty(String content){

        if(content!=null&&content!="")
            return false;
        return true;
    }

}
