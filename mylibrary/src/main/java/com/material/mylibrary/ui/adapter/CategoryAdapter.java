package com.material.mylibrary.ui.adapter;

/**
 * Created by caoyamin on 15/11/2.
 */
public class CategoryAdapter {

    private String[] cateArray=null;

    public CategoryAdapter(String[] cateArray){
        this.cateArray=cateArray;
    }
    public String[] getAdapter(){
        return cateArray;
    }

}
