package com.material.travel.view;

/**
 * Created by caoyamin on 15/9/23.
 */
public interface ISearchView extends BaseViewInterface {
    String getInputText();
    void clearInputText();
    void setInputText(String content);
}
