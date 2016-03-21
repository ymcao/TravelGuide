package com.material.travel.presenter;

import com.material.travel.model.UploadResult;
import com.material.travel.view.IPostBackView;
import com.squareup.okhttp.RequestBody;
import java.util.Map;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by caoyamin on 15/9/23.
 */
public class PostPicPresenter extends BasePresenter<IPostBackView> {

    private IPostBackView iPostBackView;
    public PostPicPresenter(IPostBackView iPostBackView){

        super("http://192.168.0.126:8080");
        this.iPostBackView=iPostBackView;
    }

    public void postPicture(Map<String,RequestBody> maps){

        iPostBackView.showLoading();
        Observable<UploadResult> observable = service.postIdCard(maps);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UploadResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iPostBackView.hideLoading();
                    }

                    @Override
                    public void onNext(UploadResult result) {
                      
                        iPostBackView.hideLoading();
                        iPostBackView.setPicInfo(result);
                    }
                });
    }
}
