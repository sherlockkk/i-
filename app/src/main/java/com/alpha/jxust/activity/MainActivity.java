package com.alpha.jxust.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.alpha.jxust.R;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 *
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.app_name,R.string.menu_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        mSwipeRefreshLayout.setProgressViewOffset(false,0,100);
        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
               // mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    mSwipeRefreshLayout.setRefreshing(false);

                    //swipeRefreshLayout.setEnabled(false);
                    break;
                default:
                    break;
            }
        }

    };


    private void findViews() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sr);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
