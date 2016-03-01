package com.alpha.jxust.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        findViews();
        initDatas();
        setListeners();
    }

    protected abstract void setListeners();

    protected abstract void initDatas();

    protected abstract void initViews();

    protected abstract void findViews();

    public void startActivity(Class<? extends Activity> target,Bundle bundle,boolean finish){
        Intent intent = new Intent();
        intent.setClass(this,target);
        if (bundle!=null){
            intent.putExtra(getPackageName(),bundle);
        }
        startActivity(intent);
        if (finish){
            finish();
        }
    }

    public Bundle getBundle(){
        if (getIntent()!=null&&getIntent().hasExtra(getPackageName())){
            return getIntent().getBundleExtra(getPackageName());
        }else{
            return null;
        }
    }
}
