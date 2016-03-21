package com.material.travel.presenter;

import com.material.travel.model.Results;
import com.material.travel.view.ISearchView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by caoyamin on 15/9/23.
 */
public class SearchPresenter extends BasePresenter<ISearchView> {

    private ISearchView searchView;


    public SearchPresenter(ISearchView searchView){
        super("");
        this.searchView=searchView;
    }

    public void Submit(){
        searchView.showLoading();

        rx.Observable<Results> observable = service.queryIdCard("ad41d354345de3726f26d79fd97c9de0", searchView.getInputText().toString());
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Results>() {
                    @Override
                    public void onCompleted() {
                       searchView.hideLoading();
                    }
                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Results results) {
                        searchView.setInputText(results.retData.address);
                        searchView.hideLoading();
                    }
                });
    }
    public void clearInput(){
        searchView.clearInputText();
    }
}
