package com.alpha.jxust.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alpha.jxust.R;
import com.alpha.jxust.activity.StatusCircleActivity;
import com.alpha.jxust.base.BaseFragment;
import com.alpha.jxust.ui.ViewHolder;

/**
 * @author SongJian
 * @created 2016/1/18.
 * @e-mail 1129574214@qq.com
 */
public class DiscoverFragment extends BaseFragment implements View.OnClickListener{
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        LinearLayout circleView = ViewHolder.findViewById(view,R.id.ll_circle);
        circleView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_circle:
                //跳转到校友圈
                Intent intent = new Intent(mActivity, StatusCircleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
