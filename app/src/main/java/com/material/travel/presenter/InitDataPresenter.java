package com.material.travel.presenter;

import com.material.travel.model.TravelInfo;
import com.material.travel.view.IListLoadView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by caoyamin on 15/9/23.
 */
public class InitDataPresenter extends BasePresenter<IListLoadView> {

    private IListLoadView iListLoadView;

    public InitDataPresenter(IListLoadView dataView){
        super("http://apis.baidu.com");
        this.iListLoadView=dataView;
    }

    public void initMainData(String query,int page){
        iListLoadView.showLoading();

        Observable<TravelInfo> observable = service.queryTravelLists("ad41d354345de3726f26d79fd97c9de0", query, page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TravelInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iListLoadView.hideLoading();
                        iListLoadView.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(TravelInfo travelInfo) {
                        //Log.e("YM","travelInfo:"+travelInfo);
                        iListLoadView.hideLoading();
                        iListLoadView.setTravelInfo(travelInfo);
                    }
                });
    }
}
