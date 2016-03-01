package com.alpha.jxust.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.jxust.R;

/**
 * @author SongJian
 * @created 2016/2/3.
 * @e-mail 1129574214@qq.com
 */
public class MyApplyGridAdapter extends BaseAdapter {
    private String[] titles = {"课程表", "成绩查询", "考试查询", "校园一卡通", "图书查询", "跳蚤市场", "社团协会", "同学录", "班级"};
    private int[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    private Context context;
    public MyApplyGridAdapter(Context context) {
    this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;
        if (convertView == null){
            viewHold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_myapply,parent,false);
            viewHold.imageView_imgs = (ImageView) convertView.findViewById(R.id.iv_myapply);
            viewHold.textView_titles = (TextView) convertView.findViewById(R.id.tv_myapply);
            convertView.setTag(viewHold);
        } else {
          viewHold = (ViewHold) convertView.getTag();
        }

        viewHold.imageView_imgs.setImageResource(images[position]);
        viewHold.textView_titles.setText(titles[position]);

        return convertView;
    }

    static class ViewHold{
        ImageView imageView_imgs;
        TextView textView_titles;
    }
}
