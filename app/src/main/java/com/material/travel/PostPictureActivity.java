package com.material.travel;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.material.travel.adapter.PicsAdapter;
import com.material.travel.okimage.TestImages;
import com.material.travel.presenter.PostPicPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caoyamin on 15/10/22.
 */
public class PostPictureActivity extends BaseActivity /*implements IPostBackView*/ {
    //private Button postBtn;
    private PostPicPresenter picPresenter;
    Map<String ,Object> map=new HashMap<String,Object>();
    RecyclerView picLists;
    PicsAdapter adapter;
    private GridLayoutManager layoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(0XFFFFFF);
        titleView.setText("图片列表");
        picLists=(RecyclerView)findViewById(R.id.picLists);
        layoutManager = new GridLayoutManager(PostPictureActivity.this,3);
        picLists.setLayoutManager(layoutManager);
        adapter=new PicsAdapter(PostPictureActivity.this, TestImages.imageThumbUrls);
        picLists.setAdapter(adapter);
        //picPresenter=new PostPicPresenter(this);

    }
      /* String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/liudehua.jpg";
        Map<String, RequestBody> map = new HashMap<>();
        File imgFile=new File(path);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "XXOO");
        map.put("name",name);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
        map.put("photo\"; filename=\""+imgFile.getName()+"", fileBody);
        postBtn.setOnClickListener(
                v -> goTo());
      */
}
