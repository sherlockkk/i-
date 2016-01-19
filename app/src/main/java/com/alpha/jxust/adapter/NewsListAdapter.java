package com.alpha.jxust.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    public NewsListAdapter(Context context) {
        this.context = context;
        listEntityList = new ArrayList<>();
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
    }

    public void addList(List<NewsList.ItemsEntity.ListEntity> items) {
        this.listEntityList = items;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ToolLog.i("NewsAdapter",listEntityList.get(position).getPhoto());
        if (listEntityList.get(position).getPhoto() == null) {

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.news_listview_layout1, parent, false);
                viewHolder.textView_title = (TextView) convertView.findViewById(R.id.tv_news_title);
                viewHolder.textView_news_description = (TextView) convertView.findViewById(R.id.tv_news_description);
                viewHolder.textView__news_releaseTime = (TextView) convertView.findViewById(R.id.tv_news_list_releaseTime);
                viewHolder.textView_news_clicked = (TextView) convertView.findViewById(R.id.tv_news_list_clicked);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView_title.setText(listEntityList.get(position).getTitle());
            viewHolder.textView_news_description.setText(listEntityList.get(position).getContent());
            viewHolder.textView__news_releaseTime.setText(listEntityList.get(position).getTime());
            //viewHolder.textView_news_clicked.setText(listEntityList.get(position).getClick());
            ToolLog.i("co1",convertView.toString());
            return convertView;
        } else {
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.news_listview_layout2, parent, false);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_news_list_pic);
                viewHolder.textView_title = (TextView) convertView.findViewById(R.id.tv_news_title);
                viewHolder.textView_news_description = (TextView) convertView.findViewById(R.id.tv_news_description);
                viewHolder.textView__news_releaseTime = (TextView) convertView.findViewById(R.id.tv_news_list_releaseTime);
                viewHolder.textView_news_clicked = (TextView) convertView.findViewById(R.id.tv_news_list_clicked);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView_title.setText(listEntityList.get(position).getTitle());
            viewHolder.textView_news_description.setText(listEntityList.get(position).getContent());
            viewHolder.textView__news_releaseTime.setText(listEntityList.get(position).getTime());
            viewHolder.textView_news_clicked.setText(listEntityList.get(position).getClick());
            //imageLoader.displayImage(listEntityList.get(position).getPhoto());
            viewHolder.imageView.setImageResource(R.drawable.banner_01);
            ToolLog.i("co2",convertView.toString());
            return convertView;
        }
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView textView_title;
        TextView textView_news_description;
        TextView textView__news_releaseTime;
        TextView textView_news_clicked;
    }
}
