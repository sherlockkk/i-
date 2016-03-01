package com.alpha.jxust.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alpha.jxust.R;
import com.alpha.jxust.adapter.CampusViewpagerAdapter;
import com.alpha.jxust.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/1/18.
 * @e-mail 1129574214@qq.com
 */
public class CampusFragment extends BaseFragment {
    private List<Fragment> fragments;
    private List<String> titles;
    private PagerTabStrip tabStrip;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus, container, false);

        initViewPager(view);

        return view;
    }

    private void initViewPager(View parentView) {
        ViewPager viewPager = (ViewPager) parentView.findViewById(R.id.vp_campus);

        PagerTabStrip tabStrip = (PagerTabStrip) parentView.findViewById(R.id.pts_pagertitle);
       // tabStrip.setTabIndicatorColorResource(R.color.colorPrimaryDark);

        Fragment myApplyFragment = new MyApplyFragment();
        Fragment marketApplyFragment = new MarketApplyFragment();

        fragments = new ArrayList<>();
        fragments.add(myApplyFragment);
        fragments.add(marketApplyFragment);

        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.myapplytitle));
        titles.add(getResources().getString(R.string.marketapplytitle));

//        titles.add("我的应用");
//        titles.add("市场应用");

        CampusViewpagerAdapter adapter = new CampusViewpagerAdapter(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
}
