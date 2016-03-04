package com.alpha.jxust.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alpha.jxust.Constants;
import com.alpha.jxust.R;
import com.alpha.jxust.activity.MainActivity;
import com.alpha.jxust.activity.NewsDetailActivity;
import com.alpha.jxust.adapter.NewsListAdapter;
import com.alpha.jxust.base.BaseFragment;
import com.alpha.jxust.model.NewsDetail;
import com.alpha.jxust.model.NewsList;
import com.alpha.jxust.model.TopBannerEntity;
import com.alpha.jxust.utils.HttpUtil;
import com.alpha.jxust.ui.HomeBanner;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @author SongJian
 * @created 2016/1/17.
 * @e-mail 1129574214@qq.com
 * <p>
 * 首页Fragment
 */
public class HomeFragment extends BaseFragment {
    private HomeBanner homeBanner;
    private List<TopBannerEntity> entity;
    private TopBannerEntity topEntity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsList newsList;
    private NewsDetail newsDetail;
    private Handler handler = new Handler();
    private NewsListAdapter newsListAdapter;
    private ListView listView;
    private boolean isLoading = false;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = (ListView) view.findViewById(R.id.lv_news);
        View header = inflater.inflate(R.layout.banner, listView, false);
        homeBanner = (HomeBanner) header.findViewById(R.id.banner);
        String[] titles = new String[]{"江西理工与牛津大学达成合作办学", "公安部打黑除恶", "纸老虎纸老虎纸老虎", "郊区王者吊打一区螃蟹！"};
        homeBanner.setImagesUrl(new String[]{"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg", "http://img02.muzhiwan.com/2015/06/11/upload_557903dc0f165.jpg", "http://img04.muzhiwan.com/2015/06/05/upload_5571659957d90.png", "http://img03.muzhiwan.com/2015/06/16/upload_557fd2a8da7a3.jpg"}, titles);
        listView.addHeaderView(header);

        newsListAdapter = new NewsListAdapter(mActivity);
        listView.setAdapter(newsListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, position + "", Snackbar.LENGTH_SHORT).show();
                NewsList.ItemsEntity.ListEntity listEntity = (NewsList.ItemsEntity.ListEntity) parent.getAdapter().getItem(position);
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("entity", listEntity);
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        loadData();

    }

    //加载数据
    private void loadData() {
        isLoading = true;
        if (HttpUtil.isNetworkConnected(mActivity)) {
            HttpUtil.get(Constants.NEWS_URL, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    //有网络就缓存json数据到数据库
                    SQLiteDatabase database = ((MainActivity) mActivity).getCacheDbHelper().getWritableDatabase();
                    database.execSQL("replace into CacheList(date,json) values(" + Constants.BASE_COLUMN + ",'" + responseString + "')");
                    database.close();
                    jsonToBean(responseString);
                }
            });
        } else {

            SQLiteDatabase database = ((MainActivity) mActivity).getCacheDbHelper().getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from CacheList where date = " + Constants.BASE_COLUMN, null);
            if (cursor.moveToFirst()) {
                String json = cursor.getString(cursor.getColumnIndex("json"));
                jsonToBean(json);
            } else {
                isLoading = false;
            }
            cursor.close();
            database.close();
        }
    }

    private void jsonToBean(String response) {
        Gson gson = new Gson();
        newsList = gson.fromJson(response, NewsList.class);
        handler.post(new Runnable() {
            @Override
            public void run() {
                List<NewsList.ItemsEntity.ListEntity> listEntities = newsList.getItems().getList();
                for (int i = 0; i < listEntities.size(); i++) {
                    if (listEntities.get(i).getPhoto() == null) {
                        listEntities.get(i).setType(Constants.NEWS_TYPE_NOPIC);
                    } else {
                        listEntities.get(i).setType(Constants.NEWS_TYPE_PIC);
                    }
                }
                newsListAdapter.addList(listEntities);
                isLoading = false;
            }
        });
    }

}
