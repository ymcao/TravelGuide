package com.material.travel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.material.mylibrary.ui.DividerItemDecoration;
import com.material.mylibrary.ui.JellyRefreshLayout;
import com.material.travel.MainActivity;
import com.material.travel.R;
import com.material.travel.adapter.MainAdapter;
import com.material.travel.model.TravelInfo;
import com.material.travel.presenter.InitDataPresenter;
import com.material.travel.view.IListLoadView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by caoyamin on 15/10/21.
 */
public class TravelFragment extends BaseFragment implements IListLoadView {

    private static final String ARG_POSITION = "keyword";
    private View rootView;
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;
    private String keyWord;
    private InitDataPresenter dataP;
    private MainAdapter mAdapter;
    private RecyclerView itemLists;
    private LinearLayoutManager layoutManager;
    private JellyRefreshLayout jelly_refresh;
    private FloatingActionButton toTopButton;
    private int type=1;
    private int page =1;

    public static TravelFragment newInstance(String keyWord) {
        TravelFragment f = new TravelFragment();
        Bundle b = new Bundle();
        b.putString(ARG_POSITION, keyWord);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        keyWord = getArguments().getString(ARG_POSITION);
        //
        if(rootView==null)
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        layoutManager = new LinearLayoutManager(getActivity());
        jelly_refresh = (JellyRefreshLayout) rootView.findViewById(R.id.jellyRefresh);
        jelly_refresh.setRefreshListener(new JellyRefreshLayout.JellyRefreshListener() {
            @Override
            public void onRefresh(final JellyRefreshLayout jellyRefreshLayout) {
                type=1;
                page=1;
                lazyLoad();
            }

            @Override
            public void LoadMore(JellyRefreshLayout jellyRefreshLayout) {
                type=2;
                page++;
                lazyLoad();

            }
        });
        itemLists = (RecyclerView) rootView.findViewById(R.id.itemLists);
        itemLists.setLayoutManager(layoutManager);
        itemLists.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST, true));
        itemLists.setItemAnimator(new DefaultItemAnimator());
        itemLists.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    toTopButton.setVisibility(View.VISIBLE);
                }else if(newState==RecyclerView.SCROLL_STATE_DRAGGING){
                    toTopButton.setVisibility(View.GONE);
                }
            }
        });
        toTopButton=(FloatingActionButton)rootView.findViewById(R.id.toTopButton);
        toTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTop();
            }
        });
        dataP = new InitDataPresenter(this);
        isPrepared = true;
        lazyLoad();
        return rootView;
    }

    @Override
    public void setTravelInfo(TravelInfo info) {

        int total=info.data.count;
        int hasLoadedNum=page*10;

        if(info.ret&&info.data.books.size()>0){

           if(type==1) {
               jelly_refresh.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       jelly_refresh.finishRefreshing();
                   }
               },1000);

               mAdapter = new MainAdapter(getActivity().getApplicationContext(), info.data.books);
               itemLists.setAdapter(mAdapter);

           }else{

               jelly_refresh.finishLoadMore();
               mAdapter.add(info.data.books);
           }
            if(hasLoadedNum<total) {
                jelly_refresh.setHashNext(true);
            }else{
                jelly_refresh.setHashNext(false);
            }

            initEvent();
        }
    }

    @Override
    public void onError(String msg) {
        if(type==1) {
            jelly_refresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jelly_refresh.finishRefreshing();
                }
            }, 500);

        }else{
            jelly_refresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jelly_refresh.finishLoadMore();
                }
            }, 5000);

        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        dataP.initMainData(keyWord, page);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void showLoading() {
        ((MainActivity)getActivity()).showDialog();
    }

    @Override
    public void hideLoading() {
        ((MainActivity)getActivity()).dismissDialog();
    }
    private void initEvent() {
        mAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void toTop(){
        String path= Environment.getExternalStorageDirectory().getAbsolutePath();
        File file1=new File(path+"/out.apatch");
        File file2=new File(path+"/travel_1.apk");
        Log.e("YM", "out.apatch:" + getFileMD5(file1));
        Log.e("YM", "travel_1.apk:" + getFileMD5(file1));
    }

    private String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            //Log.e(TAG, "getFileMD5", e);
            return null;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
               // Log.e(TAG, "getFileMD5", e);
            }
        }
        BigInteger bigInt = new BigInteger(digest.digest());
        return bigInt.toString();
    }
}