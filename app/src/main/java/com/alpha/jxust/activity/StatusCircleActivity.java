package com.alpha.jxust.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alpha.jxust.Constants;
import com.alpha.jxust.R;
import com.alpha.jxust.adapter.RvAdapter;
import com.alpha.jxust.base.BaseActivity;
import com.alpha.jxust.controller.StatusController;
import com.alpha.jxust.model.Status;
import com.alpha.jxust.tools.ToolLog;
import com.alpha.jxust.utils.StatusNetAsyncTask;
import com.alpha.jxust.utils.UserUtil;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVStatusQuery;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.List;

import kale.recycler.ExRecyclerView;
import kale.recycler.OnRecyclerViewScrollListener;

/**
 * @author SongJian
 * @created 2016/3/3.
 * @e-mail 1129574214@qq.com
 */
public class StatusCircleActivity extends BaseActivity {
    private static final int ONE_PAGE_SIZE = 15;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ExRecyclerView exRecyclerView;
    private FloatingActionButton fab_publish;
    private RvAdapter rvAdapter;
     List<Status>statusList  = new ArrayList<>();



    boolean loadMore = true;

    @Override
    protected void initDatas() {


    }


    @Override
    protected void initViews() {
        setContentView(R.layout.activity_status_circle);
        UserUtil.regiserUser(AVUser.getCurrentUser());
        loadData();

    }

    @Override
    protected void findViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_status);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StatusCircleActivity.this, "加载中...", Toast.LENGTH_SHORT).show();
            }
        }, 200);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        exRecyclerView = (ExRecyclerView) findViewById(R.id.rv_status);
        exRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToolLog.i("StatusCircleActivity", "item" + position + "被点击");
            }
        });
        exRecyclerView.addOnScrollListener(new OnRecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {
                ToolLog.i("StatusCircleActivity", "向上滑...");
            }

            @Override
            public void onScrollDown() {
                ToolLog.i("StatusCircleActivity", "向下滑...");
            }

            @Override
            public void onBottom() {
                //Toast.makeText(StatusCircleActivity.this, "已经到底部了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMoved(int i, int i1) {
                ToolLog.i("StatusCircleActivity", "i:" + i + "," + "i1:" + i1);
            }
        });
        exRecyclerView.setAdapter(rvAdapter = new RvAdapter(statusList,this));
        rvAdapter.notifyDataSetChanged();
        fab_publish = (FloatingActionButton) findViewById(R.id.fab_publish);
        fab_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到发布动态
                Intent intent = new Intent(StatusCircleActivity.this, StatusPublishActivity.class);
                startActivity(intent);
            }
        });
    }


    public long getMaxId(int skip) {
        List<Status> currentDatas = rvAdapter.getData();
        long maxId;
        if (skip == 0) {
            maxId = Long.MAX_VALUE;
        } else {

            AVStatus lastStatus = currentDatas.get(currentDatas.size() - 1).getInnerStatus();
            maxId = lastStatus.getMessageId() - 1;
        }
        return maxId;
    }


    private void loadData() {
        if (loadMore) {
            new StatusNetAsyncTask(this) {

                @Override
                protected void doInBack() throws Exception {
                    ToolLog.i("------", "doInBack");
                    statusList = StatusController.getStatusDatas( ONE_PAGE_SIZE);
                }

                @Override
                protected void onPost(Exception e) {
                    swipeRefreshLayout.setRefreshing(false);
                    rvAdapter.updateData(statusList);
                }
            }.execute();
        }

    }
}
