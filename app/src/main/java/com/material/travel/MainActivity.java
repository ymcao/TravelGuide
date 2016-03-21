package com.material.travel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.material.mylibrary.ui.SlidingTabLayout;
import com.material.travel.adapter.ViewPagerAdapter;

public class MainActivity extends BaseActivity {
    private LinearLayout mainLayout;
    //private  InputMethodManager inputMethodManager;
    private SearchView searchView;
    //private CategoryView categoryView;
    //private CategoryAdapter mAdater;
    private String mQuery=null;
    ViewPager pager;
    private String titles[] = new String[]{"香港", "澳门", "台湾", "日本"
            , "新加坡", "泰国", "马来西亚", "马尔代夫","英国"};
    SlidingTabLayout slidingTabLayout;
    private Button testButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout=(LinearLayout)findViewById(R.id.mainLayout);
        //categoryView=(CategoryView)findViewById(R.id.categoryView);
        //mAdater=new CategoryAdapter(titles);
        /*categoryView.setAdater(mAdater);
        categoryView.setSelectListener(new CategoryView.OnViewSelectListener() {
            @Override
            public void onSelect(int position) {
                Toast.makeText(MainActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });*/
        //
       //inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(0XFFFFFF);
        pager = (ViewPager) findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles));
        //pager.setCurrentItem(0);
        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
        testButton=(Button)findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Button Click",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                hideSoftInput();
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideSoftInput();
                //mQuery=query;
                Intent i=new Intent(MainActivity.this, PostPictureActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "onQueryTextSubmit", Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        /*MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Toast.makeText(MainActivity.this, "onExpand", Toast.LENGTH_LONG).show();

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
               // Toast.makeText(MainActivity.this, "Collapse", Toast.LENGTH_LONG).show();
                return true;
            }
        });*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


    /*private void hideSoftInput() {
        if (inputMethodManager != null) {
            View v = MainActivity.this.getCurrentFocus();
            if (v == null) {
                return;
            }

            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            searchView.clearFocus();
        }
    }*/
    //getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
}