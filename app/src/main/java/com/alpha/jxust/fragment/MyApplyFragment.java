package com.alpha.jxust.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alpha.jxust.R;
import com.alpha.jxust.adapter.MyApplyGridAdapter;
import com.alpha.jxust.base.BaseFragment;
import com.alpha.jxust.ui.HomeBanner;

/**
 * @author SongJian
 * @created 2016/2/2.
 * @e-mail 1129574214@qq.com
 */
public class MyApplyFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    private GridView gridView;
    private MyApplyGridAdapter myApplyGridAdapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview_myapply_layout,container,false);
        gridView = (GridView) view.findViewById(R.id.gv_myapply);
        myApplyGridAdapter = new MyApplyGridAdapter(mActivity);
        gridView.setAdapter(myApplyGridAdapter);
        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Snackbar.make(view,position+"",Snackbar.LENGTH_SHORT).show();
    }
}
