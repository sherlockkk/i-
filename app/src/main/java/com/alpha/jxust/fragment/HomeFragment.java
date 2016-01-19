package com.alpha.jxust.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alpha.jxust.Constants;
import com.alpha.jxust.R;
import com.alpha.jxust.adapter.NewsListAdapter;
import com.alpha.jxust.base.BaseFragment;
import com.alpha.jxust.model.NewsList;
import com.alpha.jxust.model.TopBannerEntity;
import com.alpha.jxust.tools.HttpUtil;
import com.alpha.jxust.ui.HomeBanner;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @author SongJian
 * @created 2016/1/17.
 * @e-mail 1129574214@qq.com
 */
public class HomeFragment extends BaseFragment {
    private HomeBanner homeBanner;
    private List<TopBannerEntity> entity;
    private TopBannerEntity topEntity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsList newsList;
    private Handler handler = new Handler();
    private NewsListAdapter newsListAdapter;
    private ListView listView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newsListAdapter = new NewsListAdapter(mActivity);
        homeBanner = (HomeBanner) view.findViewById(R.id.home_banner);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        listView = (ListView) view.findViewById(R.id.lv_news_list);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        String[] titles = new String[]{"江西理工与牛津大学达成合作办学","公安部打黑除恶","纸老虎纸老虎纸老虎","郊区王者吊打一区螃蟹！"};
        homeBanner.setImagesUrl(new String[]{"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg", "http://img02.muzhiwan.com/2015/06/11/upload_557903dc0f165.jpg", "http://img04.muzhiwan.com/2015/06/05/upload_5571659957d90.png", "http://img03.muzhiwan.com/2015/06/16/upload_557fd2a8da7a3.jpg"},titles);
        //homeBanner.setImagesRes(new int[]{R.drawable.banner_01,R.drawable.banner_01,R.drawable.banner_01});
        listView.setAdapter(newsListAdapter);
        loadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    private void loadData() {
        if (HttpUtil.isNetworkConnected(mActivity)){
            HttpUtil.get(Constants.NEWS_URL, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    jsonToBean(responseString);
                }
            });
        }
    }

    private void jsonToBean(String response) {
        Gson gson = new Gson();
        newsList = gson.fromJson(response,NewsList.class);

        handler.post(new Runnable() {
            @Override
            public void run() {
                List<NewsList.ItemsEntity.ListEntity>listEntities = newsList.getItems().getList();
//                NewsList.ItemsEntity.ListEntity listEntity = new NewsList.ItemsEntity.ListEntity();
//                for (int i = 0; i < listEntities.size(); i++) {
//                    listEntity.setTitle(listEntities.get(i).getTitle());
//                    listEntity.setPhoto(listEntities.get(i).getPhoto());
//                    listEntity.setClick(listEntities.get(i).getClick());
//                    listEntity.setId(listEntities.get(i).getId());
//                    listEntity.setTime(listEntities.get(i).getTime());
//                    listEntity.setContent(listEntities.get(i).getContent());
//                }
                newsListAdapter.addList(listEntities);
            }
        });
    }


}
