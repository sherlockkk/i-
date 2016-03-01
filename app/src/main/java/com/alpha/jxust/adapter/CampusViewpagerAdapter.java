package com.alpha.jxust.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.alpha.jxust.fragment.CampusFragment;

import java.util.List;

/**
 * @author SongJian
 * @created 2016/2/2.
 * @e-mail 1129574214@qq.com
 *
 * 校园界面ViewPager适配器
 */
public class CampusViewpagerAdapter extends FragmentPagerAdapter {
    private List<Fragment>fragments;
    private List<String>titles;

    public CampusViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CampusViewpagerAdapter(FragmentManager fm, List<Fragment>fragments,List<String >titles){
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.i("CampusViewpagerAdapter", "getPageTitle: "+titles.get(position)+"     "+position);
        return titles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
