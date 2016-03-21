package com.material.travel.view;


import com.material.travel.model.LoginResult;

/**
 * Created by caoyamin on 15/9/23.
 */
public interface ILoginView extends BaseViewInterface {

     void onSubmit(LoginResult result);
}
