package com.alpha.jxust.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.alpha.jxust.R;
import com.alpha.jxust.Controller.StatusController;
import com.alpha.jxust.adapter.StatusListAdapter;
import com.alpha.jxust.base.BaseActivity;
import com.alpha.jxust.base.BaseListView;
import com.alpha.jxust.model.Status;
import com.alpha.jxust.tools.ToolLog;
import com.alpha.jxust.utils.UserUtil;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVStatusQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/3/3.
 * @e-mail 1129574214@qq.com
 */
public class StatusCircleActivity extends BaseActivity {
    private BaseListView<Status> statusList;
    private FloatingActionButton fab_publish;

    @Override
    protected void initDatas() {

        statusList.init(new BaseListView.DataInterface<Status>() {
            @Override
            public List<Status> getDatas(int skip, int limit, List<Status> currentDatas) throws Exception {
                long maxId = getMaxId(skip, currentDatas);
                if (maxId == 0) {
                    return new ArrayList<Status>();
                } else {
                    return StatusController.getStatusDatas(maxId,limit);
                }
            }

            @Override
            public void onItemSelected(Status item) {
                //单条动态被选中时的操作
            }

            @Override
            public void onItemLongPressed(Status item) {
                super.onItemLongPressed(item);
                //单条动态被长按时的操作
            }
        }, new StatusListAdapter(this));
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_status_circle);
        UserUtil.regiserUser(AVUser.getCurrentUser());
    }

    @Override
    protected void findViews() {
        statusList = (BaseListView<Status>) findViewById(R.id.status_List);
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


    public static long getMaxId(int skip, List<Status> currentDatas) {
        long maxId;
        if (skip == 0) {
            maxId = Long.MAX_VALUE;
        } else {
            AVStatus lastStatus = currentDatas.get(currentDatas.size() - 1).getInnerStatus();
            maxId = lastStatus.getMessageId() - 1;
        }
        return maxId;
    }
}
