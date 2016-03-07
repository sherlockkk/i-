package com.alpha.jxust.adapterItem;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.jxust.Constants;
import com.alpha.jxust.R;
import com.alpha.jxust.adapter.RvAdapter;
import com.alpha.jxust.controller.StatusController;
import com.alpha.jxust.model.Status;
import com.alpha.jxust.tools.ToolLog;
import com.alpha.jxust.utils.StatusUtil;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.AdapterModel;
import kale.adapter.ViewHolder;

/**
 * @author SongJian
 * @created 2016/3/4.
 * @e-mail 1129574214@qq.com
 */
public class StatusAdapterItem implements AdapterItem<Status> {
    private Context context;
    private RvAdapter adapter;
    private ImageView iv_avatarView;
    private TextView tv_nameView;
    private TextView tv_timeView;
    private TextView tv_statusText;
    private ImageView iv_statusImage;
    private TextView tv_commentView;
    private TextView tv_likeView;

    public StatusAdapterItem(Context context, RvAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.status_item;
    }

    @Override
    public void initViews(ViewHolder viewHolder, Status status, int i) {
        iv_avatarView = viewHolder.getView(R.id.iv_avatarView);
        tv_nameView = viewHolder.getView(R.id.tv_nameView);
        tv_timeView = viewHolder.getView(R.id.tv_timeView);
        tv_statusText = viewHolder.getView(R.id.tv_statusText);
        iv_statusImage = viewHolder.getView(R.id.iv_statusImage);
        tv_commentView = viewHolder.getView(R.id.tv_commentView);
        tv_likeView = viewHolder.getView(R.id.tv_likeView);
        setView(status,i);
    }

    private void setView(Status sta, int i) {
        AVStatus innerStatus = sta.getInnerStatus();
        AVUser source = innerStatus.getSource();
        StatusUtil.displayAvatar(source,iv_avatarView);
        tv_nameView.setText(source.getUsername());
        iv_avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolLog.i("StatusAdapterItem","头像被点击");
            }
        });

        if (TextUtils.isEmpty(innerStatus.getMessage())){
            tv_statusText.setVisibility(View.GONE);
        }else {
            tv_statusText.setText(innerStatus.getMessage());
            tv_statusText.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(innerStatus.getImageUrl())==false){
            iv_statusImage.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(innerStatus.getImageUrl(),iv_statusImage,StatusUtil.normalImageOptions);
            iv_statusImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToolLog.i("StatusAdapterItem", "图片被点击");
                }
            });
        }else {
            iv_statusImage.setVisibility(View.GONE);
        }
        //从model中获取点赞、评论等数据
        final AVObject detail = sta.getDetail();
        final List<String> likes;//存储点赞者UserId的集合
        if (detail.get(Constants.LIKES)!=null){
            likes = (List<String>) detail.get(Constants.LIKES);
        }else {
            likes = new ArrayList<String>();
        }
        //获取点赞数量
        int n = likes.size();
        if (n>0){
            tv_likeView.setText("点赞 "+n);
        }else {
            tv_likeView.setText("点赞");
        }
        //点赞
        final AVUser user = AVUser.getCurrentUser();
        final String userId = user.getObjectId();
        final boolean contains = likes.contains(userId);
        tv_likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCallback saveCallback = new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (StatusUtil.filterException(context,e)){
                            adapter.notifyDataSetChanged();
                        }
                    }
                };
                if (contains){
                    likes.remove(userId);
                    StatusController.saveStatusLikes(detail,likes,saveCallback);
                }else {
                    likes.add(userId);
                    StatusController.saveStatusLikes(detail,likes,saveCallback);
                }
            }
        });

        tv_timeView.setText(millisecs2DateString(innerStatus.getCreatedAt().getTime()));
        //点击评论
        tv_commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolLog.i("----", "评论被点击");
            }
        });
    }


    public static PrettyTime prettyTime = new PrettyTime();

    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }

    public static String millisecs2DateString(long timestamp) {
        long gap = System.currentTimeMillis() - timestamp;
        if (gap < 1000 * 60 * 60 * 24) {
            String s = prettyTime.format(new Date(timestamp));
            return s.replace(" ", "");
        } else {
            return getDate(new Date(timestamp));
        }
    }

}
