package com.material.travel.view;


import com.material.travel.model.TravelInfo;

/**
 * Created by caoyamin on 15/9/23.
 */
public interface IListLoadView extends BaseViewInterface {


     void setTravelInfo(TravelInfo info);
     void onError(String msg);
}
