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
import android.view.View;

import com.alpha.jxust.R;
import com.alpha.jxust.fragment.HomeFragment;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 *
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
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
    }




    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
