package com.alpha.jxust.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alpha.jxust.R;
import com.alpha.jxust.base.BaseActivity;
import com.alpha.jxust.tools.ToolLog;

/**
 * @author SongJian
 * @created 2016/2/10.
 * @e-mail 1129574214@qq.com
 */
public class LoginActivity extends BaseActivity {
    private static String TAG = "LoginActivity";
    private TextView textView;


    @Override
    protected void setListeners() {
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(RegisterActivity.class,null,false);
//            }
//        });

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void findViews() {
        textView = (TextView) findViewById(R.id.tv_register);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolLog.i(TAG,">>>>>>>>>>立即注册");
                startActivity(RegisterActivity.class,null,false);
            }
        });
    }
}
