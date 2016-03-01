package com.alpha.jxust.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.jxust.Constants;
import com.alpha.jxust.R;
import com.alpha.jxust.model.NewsList;
import com.alpha.jxust.tools.ToolLog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/1/19.
 * @e-mail 1129574214@qq.com
 */
public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsList.ItemsEntity.ListEntity> listEntityList;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Handler handler;


    public NewsListAdapter(Context context) {
        this.context = context;
        listEntityList = new ArrayList<>();
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
    }

    public void addList(List<NewsList.ItemsEntity.ListEntity> items) {
        this.listEntityList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return listEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return listEntityList.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.news_listview_layout1, parent, false);
            //viewHolder.tv_topic = (TextView) convertView.findViewById(R.id.tv_topic);
            viewHolder.iv_time = (ImageView) convertView.findViewById(R.id.iv_time);
            viewHolder.iv_clicked = (ImageView) convertView.findViewById(R.id.iv_clicked);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
            viewHolder.tv_release_time = (TextView) convertView.findViewById(R.id.tv_news_list_releaseTime);
            viewHolder.tv_title_clicked = (TextView) convertView.findViewById(R.id.tv_news_list_clicked);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsList.ItemsEntity.ListEntity entity = listEntityList.get(position);
        if (entity.getType() == Constants.NEWS_TYPE_NOPIC) {
            // ((FrameLayout) viewHolder.tv_topic.getParent()).setBackgroundColor(Color.TRANSPARENT);
            viewHolder.tv_title.setVisibility(View.VISIBLE);
            viewHolder.iv_title.setVisibility(View.GONE);
            //viewHolder.tv_topic.setVisibility(View.VISIBLE);
        } else {
            //viewHolder.tv_topic.setVisibility(View.GONE);
            viewHolder.tv_title.setVisibility(View.VISIBLE);
            viewHolder.iv_title.setVisibility(View.VISIBLE);
            if (entity.getTime().toString().substring(2,4).equals("15")){
                imageLoader.displayImage(Constants.BASE_PICURL+"2015/"+entity.getPhoto(),viewHolder.iv_title,options);
            }else if (entity.getTime().toString().substring(2,4).equals("16")){
                imageLoader.displayImage(Constants.BASE_PICURL+"2016/"+entity.getPhoto(),viewHolder.iv_title,options);
            }
           // imageLoader.displayImage("drawable://" + R.drawable.banner_01, viewHolder.iv_title, options);
        }
        imageLoader.displayImage("drawable://" + R.drawable.iconfont_shizhong, viewHolder.iv_time, options);
        imageLoader.displayImage("drawable://" + R.drawable.iconfont_liulan, viewHolder.iv_clicked, options);
        viewHolder.tv_title.setText(entity.getTitle());
        viewHolder.tv_release_time.setText(entity.getTime());
        viewHolder.tv_title_clicked.setText(entity.getClick() + "");

        return convertView;



    }

    public static class ViewHolder {
        TextView tv_topic;
        TextView tv_title;
        ImageView iv_title;
        ImageView iv_time;
        ImageView iv_clicked;
        TextView tv_release_time;
        TextView tv_title_clicked;
    }



}



