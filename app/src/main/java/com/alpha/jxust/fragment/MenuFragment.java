package com.alpha.jxust.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alpha.jxust.R;
import com.alpha.jxust.activity.LoginActivity;
import com.alpha.jxust.adapter.MenuAdpater;
import com.alpha.jxust.base.BaseFragment;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 *
 * 侧滑菜单Fragment
 */
public class MenuFragment extends BaseFragment{
    private MenuAdpater menuAdpater;
    private ListView listView;
    private TextView textView;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu,container,false);

        listView = (ListView) view.findViewById(R.id.lv_item);
        textView = (TextView) view.findViewById(R.id.tv_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });

        menuAdpater = new MenuAdpater(mActivity);
        listView.setAdapter(menuAdpater);

        return view;
    }

    public void onLogin(){
        Intent intent = new Intent(mActivity, LoginActivity.class);
        startActivity(intent);
    }
}
