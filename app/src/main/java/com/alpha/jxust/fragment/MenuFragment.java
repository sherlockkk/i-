package com.alpha.jxust.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alpha.jxust.R;
import com.alpha.jxust.base.BaseFragment;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 *
 * 侧滑菜单Fragment
 */
public class MenuFragment extends BaseFragment{
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu,container,false);
        return view;
    }
}
