package com.alpha.jxust.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.jxust.Constants;
import com.alpha.jxust.R;
import com.alpha.jxust.model.NewsDetail;
import com.alpha.jxust.model.NewsList;
import com.alpha.jxust.utils.HttpUtil;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cz.msebera.android.httpclient.Header;

/**
 * @author SongJian
 * @created 2016/1/21.
 * @e-mail 1129574214@qq.com
 */
public class NewsDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NewsList.ItemsEntity.ListEntity listEntity;
    private NewsDetail newsDetail;
    private NewsDetail.ArticleEntity newsArticle;
    private TextView textView_newsDetail;
    private ImageView iv_toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        findViews();
        setToolbar();
        initData();


    }

    //初始化数据
    private void initData() {
        listEntity = (NewsList.ItemsEntity.ListEntity) getIntent().getSerializableExtra("entity");
        if (HttpUtil.isNetworkConnected(this)) {
            HttpUtil.get(Constants.NEWS_DETAIL + listEntity.getId(), new TextHttpResponseHandler() {
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

    //toolbar的相关设置
    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //json数据解析映射到bean
    private void jsonToBean(String responseString) {
        Gson gson = new Gson();
        newsDetail = gson.fromJson(responseString, NewsDetail.class);
        final ImageLoader imageloader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        if (listEntity.getPhoto() != null) {
            if (listEntity.getTime().substring(2, 4).equals("15")) {
                imageloader.displayImage(Constants.BASE_PICURL + "2015/" + listEntity.getPhoto(), iv_toolBar, options);
            } else if (listEntity.getTime().substring(2, 4).equals("16")) {
                imageloader.displayImage(Constants.BASE_PICURL + "2016/" + listEntity.getPhoto(), iv_toolBar, options);
            }

        } else {
            imageloader.displayImage("drawable://" + R.drawable.banner_01, iv_toolBar, options);
        }
        textView_newsDetail.setText(newsDetail.getArticle().getContent());
        collapsingToolbarLayout.setTitle(newsDetail.getArticle().getTitle());
    }


    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        iv_toolBar = (ImageView) findViewById(R.id.image);
        textView_newsDetail = (TextView) findViewById(R.id.tv_newsDetail);
    }
}
