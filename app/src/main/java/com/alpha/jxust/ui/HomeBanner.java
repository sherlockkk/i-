package com.alpha.jxust.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.jxust.R;
import com.alpha.jxust.model.TopBannerEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/1/17.
 * @e-mail 1129574214@qq.com
 *
 * 主页广告栏ViewPager布局
 */
public class HomeBanner extends FrameLayout implements View.OnClickListener {
    private static final int DELAY_SHORT = 3000;
    private static final int DELAY_LONG = 5000;
    private Context context;
    private boolean isAutoPlay;
    private int currentItem;
    private Handler handler = new Handler();
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private ViewPager viewPager;
    private LinearLayout ll_dot;
    private List<View> views;
    private List<ImageView> iv_dots;
    private List<TopBannerEntity> topBannerEntities;

    private OnItemClickListener onItemClickListener;

    public HomeBanner(Context context) {
        this(context, null);
    }

    public HomeBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        this.context = context;

        initView();

    }

    private void initView() {
        views = new ArrayList<>();
        iv_dots = new ArrayList<>();
    }

    public void setTopBannerEntities(List<TopBannerEntity> topBannerEntities) {
        this.topBannerEntities = topBannerEntities;
        reset();
    }

    private void reset() {
        views.clear();
        initUI();
    }

    private void initUI() {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_layout, this, true);
        viewPager = (ViewPager) view.findViewById(R.id.vp_banner);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        ll_dot.removeAllViews();

        int len = topBannerEntities.size();
        for (int i = 0; i < len; i++) {
            ImageView iv_dot = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            ll_dot.addView(iv_dot, layoutParams);
            iv_dots.add(iv_dot);

        }

        for (int i = 0; i < len + 1; i++) {
            View banner_view = LayoutInflater.from(context).inflate(R.layout.banner_content_layout, null);
            ImageView imageView_banner_title = (ImageView) banner_view.findViewById(R.id.iv_banner_title);
            TextView textView_banner_title = (TextView) banner_view.findViewById(R.id.tv_banner_title);

            if (i == 0) {
                mImageLoader.displayImage(topBannerEntities.get(len - 1).getImage(), imageView_banner_title, options);
                textView_banner_title.setText(topBannerEntities.get(len - 1).getTitle());
            } else if (i == len + 1) {
                mImageLoader.displayImage(topBannerEntities.get(0).getImage(), imageView_banner_title, options);
                textView_banner_title.setText(topBannerEntities.get(0).getTitle());
            } else {
                mImageLoader.displayImage(topBannerEntities.get(len - 1).getImage(), imageView_banner_title, options);
                textView_banner_title.setText(topBannerEntities.get(len - 1).getTitle());
            }

            view.setOnClickListener(this);
            views.add(view);
        }
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        currentItem = 1;
        viewPager.addOnPageChangeListener(new MyOnPagerChangeListener());
        startAutoPlay();

    }

    private void startAutoPlay() {
        isAutoPlay = true;
        handler.postDelayed(runnable, DELAY_SHORT);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = currentItem % (topBannerEntities.size() + 1) + 1;
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(runnable);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(runnable, DELAY_LONG);
                }
            }else {
                handler.postDelayed(runnable,DELAY_LONG);
            }
        }
    };

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < views.size(); i++) {
                if (i == position - 1) {
                    iv_dots.get(i).setImageResource(R.drawable.dot_focus);
                }
                iv_dots.get(i).setImageResource(R.drawable.dot_blur);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 0:
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(topBannerEntities.size(), false);
                    } else if (viewPager.getCurrentItem() == topBannerEntities.size() + 1) {
                        viewPager.setCurrentItem(1, false);
                    }
                    currentItem = viewPager.getCurrentItem();
                    isAutoPlay = true;
                    break;
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void click(View view, TopBannerEntity entity);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null){
            TopBannerEntity entity = topBannerEntities.get(viewPager.getCurrentItem() - 1);
            onItemClickListener.click(v,entity);
        }
    }
}
