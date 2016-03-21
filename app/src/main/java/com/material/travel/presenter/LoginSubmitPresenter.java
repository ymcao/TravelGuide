package com.material.travel.presenter;

import android.util.Log;

import com.material.travel.model.LoginResult;
import com.material.travel.model.UserModel;
import com.material.travel.view.ILoginView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by caoyamin on 15/9/23.
 */
public class LoginSubmitPresenter extends BasePresenter<ILoginView> {

    private ILoginView iLoginView;
    public LoginSubmitPresenter(ILoginView iLoginView){

        super("http://192.168.0.126:8080");
        this.iLoginView=iLoginView;
    }

    public void submitLogin(UserModel model){

        iLoginView.showLoading();
        Observable<LoginResult> observable = service.loginSubmit(model);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iLoginView.hideLoading();
                    }

                    @Override
                    public void onNext(LoginResult result) {
                        Log.e("YM",result.msg);
                        iLoginView.hideLoading();

                    }
                });
    }
}
