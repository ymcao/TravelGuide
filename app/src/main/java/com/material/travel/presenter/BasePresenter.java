package com.material.travel.presenter;


import com.material.travel.APIService;
import com.material.travel.Http.RetrofitUtils;
import com.material.travel.view.BaseViewInterface;

/**
 * Created by caoyamin on 15/9/23.
 */
public class BasePresenter<T extends BaseViewInterface> {
    public APIService service;
    public BasePresenter(String baseUrl){
        service= RetrofitUtils.getSingleIntance(baseUrl).create(APIService.class);

    }

}
