package com.declan.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private TextView tv_desc;
    private LinearLayout ll_point_container;
    private int[] resourceId;
    private ArrayList<ImageView> list;
    private LinearLayout.LayoutParams layoutParams;
    private String[] contentDesc;
    private int lastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initData();
        initAdapter();

    }
    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);
    }

    /**
     * 初始化数据，包括图片资源、文字资源
     */
    private void initData() {
        //图片资源集合
        resourceId = new int[]{
                R.drawable.a,R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e
        };
        //文字集合
        contentDesc = new String[]{
                "This is image 1",
                "This is image 2",
                "This is image 3",
                "This is image 4",
                "This is image 5"
        };
        ImageView imageView;
        View pointView;
        list = new ArrayList<ImageView>();
        for(int i = 0;i < resourceId.length;i ++){
            imageView = new ImageView(this);
            imageView.setImageResource(resourceId[i]);
            list.add(imageView);

            //给视图添加小白点
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_bg);
            layoutParams = new LinearLayout.LayoutParams(10, 10);
            if(i != 0){
                layoutParams.leftMargin = 15;
            }
            pointView.setEnabled(false);
            ll_point_container.addView(pointView,layoutParams);
        }
    }

    /**
     * 初始化适配器
     * 可以将viewPager近似看成横向的listView
     */
    private void initAdapter() {
        //第一次进入初始化数据
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDesc[0]);
        viewPager.setAdapter(new MyAdapter());
    }


    /**
     * 自定义适配器,继承自PagerAdapter
     */
     class MyAdapter extends PagerAdapter{

         @Override
         public int getCount() {
             return list.size();
         }

         @Override
         public boolean isViewFromObject(View view, Object object) {
             return view == object;
         }

         @Override
         public Object instantiateItem(ViewGroup container, int position) {
             ImageView imageView = list.get(position);
             container.addView(imageView);
             return imageView;
         }

         @Override
         public void destroyItem(ViewGroup container, int position, Object object) {
             container.removeView((View)object);
         }
     }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //新的条目被选中时调用
        tv_desc.setText(contentDesc[position]);
        ll_point_container.getChildAt(position).setEnabled(true);
        ll_point_container.getChildAt(lastPosition).setEnabled(false);
        lastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
