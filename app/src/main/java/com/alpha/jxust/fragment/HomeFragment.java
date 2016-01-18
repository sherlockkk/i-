package com.alpha.jxust.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alpha.jxust.R;
import com.alpha.jxust.base.BaseFragment;
import com.alpha.jxust.model.TopBannerEntity;
import com.alpha.jxust.ui.HomeBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/1/17.
 * @e-mail 1129574214@qq.com
 */
public class HomeFragment extends BaseFragment {
    private HomeBanner homeBanner;
    private List<TopBannerEntity> entity;
    private TopBannerEntity topEntity;
    private Object data;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeBanner = (HomeBanner) view.findViewById(R.id.home_banner);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        homeBanner.setImagesUrl(new String[]{"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg", "http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg", "http://img02.muzhiwan.com/2015/06/11/upload_557903dc0f165.jpg", "http://img04.muzhiwan.com/2015/06/05/upload_5571659957d90.png", "http://img03.muzhiwan.com/2015/06/16/upload_557fd2a8da7a3.jpg"});
        //homeBanner.setImagesRes(new int[]{R.drawable.banner_01,R.drawable.banner_01,R.drawable.banner_01});
    }


}
