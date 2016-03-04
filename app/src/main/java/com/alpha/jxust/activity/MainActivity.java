package com.alpha.jxust.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import com.alpha.jxust.R;
import com.alpha.jxust.db.CacheDbHelper;
import com.alpha.jxust.fragment.CampusFragment;
import com.alpha.jxust.fragment.HomeFragment;
import com.alpha.jxust.fragment.DiscoverFragment;
import com.alpha.jxust.fragment.MineFragment;
import com.alpha.jxust.ui.HomeBanner;

import java.util.Arrays;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private HomeBanner mHomeBanner;

    private RadioGroup radioGroup;
    int curCursor;
    private Fragment homeFragment = new HomeFragment();
    private Fragment campusFragment = new CampusFragment();
    private Fragment messageFragment = new DiscoverFragment();
    private Fragment mimeFragment = new MineFragment();
    private List<Fragment> fragmentList = Arrays.asList(homeFragment, campusFragment, messageFragment, mimeFragment);

    private FragmentManager fragmentManager;
    private CacheDbHelper cacheDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cacheDbHelper = new CacheDbHelper(this,1);
        fragmentManager = getSupportFragmentManager();
        findViews();
        initViews();
        initFootBar();
    }

    //复写onSaveInstanceState方法，不保存当前Fragment状态，以解决Fragment恢复现场重叠的bug
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.menu_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initFootBar() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        curCursor = 0;
                        break;
                    case R.id.foot_bar_campus:
                        curCursor = 1;
                        break;
                    case R.id.foot_bar_message:
                        curCursor = 2;
                        break;
                    case R.id.foot_bar_mime:
                        curCursor = 3;
                        break;
                }
                addFragmentToStack(curCursor);
            }

        });

        addFragmentToStack(0);
    }

    private void addFragmentToStack(int cursor) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(cursor);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_content, fragment);
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment f = fragmentList.get(i);
            if (i == cursor && f.isAdded()) {
                fragmentTransaction.show(f);
            } else if (f != null && f.isAdded() && f.isVisible()) {
                fragmentTransaction.hide(f);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public CacheDbHelper getCacheDbHelper(){
        return cacheDbHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fragmentList.get(curCursor);
        if (fragment != null){
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
