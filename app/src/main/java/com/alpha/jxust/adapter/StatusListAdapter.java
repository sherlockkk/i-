package com.alpha.jxust.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alpha.jxust.Constants;
import com.alpha.jxust.R;
import com.alpha.jxust.Controller.StatusController;
import com.alpha.jxust.base.BaseListAdapter;
import com.alpha.jxust.model.Status;
import com.alpha.jxust.ui.ViewHolder;
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

/**
 * @author SongJian
 * @created 2016/3/3.
 * @e-mail 1129574214@qq.com
 */
public class StatusListAdapter extends BaseListAdapter<Status> {
    public StatusListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //动态列表界面布局
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.status_item, null, false);
        }
        ImageView avatarView = ViewHolder.findViewById(convertView, R.id.iv_avatarView);
        TextView nameView = ViewHolder.findViewById(convertView, R.id.tv_nameView);
        TextView timeView = ViewHolder.findViewById(convertView, R.id.tv_timeView);
        TextView statusTextView = ViewHolder.findViewById(convertView, R.id.tv_statusText);
        ImageView statusImageView = ViewHolder.findViewById(convertView, R.id.iv_statusImage);
        TextView likeView = ViewHolder.findViewById(convertView, R.id.tv_likeView);
        TextView commentView = ViewHolder.findViewById(convertView, R.id.tv_commentView);

        Status status = datas.get(position);
        AVStatus innerStatus = status.getInnerStatus();
        AVUser user = innerStatus.getSource();

        StatusUtil.displayAvatar(user, avatarView);
        nameView.setText(user.getUsername());

        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "头像被点击", Toast.LENGTH_SHORT).show();
            }
        });

        if (TextUtils.isEmpty(innerStatus.getMessage())) {
            statusTextView.setVisibility(View.GONE);
        } else {
            statusTextView.setText(innerStatus.getMessage());
            statusTextView.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(innerStatus.getImageUrl()) == false) {
            statusImageView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(innerStatus.getImageUrl(), statusImageView, StatusUtil.normalImageOptions);

            statusImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "图片被点击", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            statusImageView.setVisibility(View.GONE);
        }

        final AVObject detail = status.getDetail();
        final List<String> likes;
        if (detail.get(Constants.LIKES) != null) {
            likes = (List<String>) detail.get("likes");
        } else {
            likes = new ArrayList<String>();
        }
        int n = likes.size();
        if (n > 0) {
            likeView.setText("点赞 " + n);
        } else {
            likeView.setText("点赞");
        }

        final String userId = user.getObjectId();
        final boolean contains = likes.contains(userId);
        likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点赞被点击", Toast.LENGTH_SHORT).show();
                SaveCallback saveCallback = new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (StatusUtil.filterException(mContext, e)) {
                            notifyDataSetChanged();
                        }
                    }
                };
                if (contains) {
                    likes.remove(userId);
                    StatusController.saveStatusLikes(detail, likes, saveCallback);
                } else {
                    likes.add(userId);
                    StatusController.saveStatusLikes(detail, likes, saveCallback);
                }

            }
        });

        timeView.setText(millisecs2DateString(innerStatus.getCreatedAt().getTime()));
        return convertView;
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
