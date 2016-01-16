package com.alpha.jxust.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 */
public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        initViews();
        initDatas();
        setListeners();
    }

    protected abstract void setListeners();

    protected abstract void initDatas();

    protected abstract void initViews();

    protected abstract void findViews();
}
