package com.zdx.android.viewkit;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zdx.android.viewkit.impl.TabViewItemView;
import com.zdx.android.viewkit.impl.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * tabview，描述一个tab切换的控件
 */
public class TabView extends LinearLayout {
    //ViewPager mViewPager;
    List<TabViewItem> tabList;
    List<TabViewItemView> tabViewList = new ArrayList<TabViewItemView>();
    //切换页面容器
    ViewPager mViewPager;

    /**
     * 初始化一个tabview控件，参数tabs用来描述tab列表内容。
     * @param context
     * @param tabs
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TabView(Context context, List<TabViewItem> tabs) {
        super(context);
        tabList = tabs;
        //排列布局，采用两层linearlayout排列
        //第一层，上下排列，上面ViewPager,下面切换tab的linearlayout
        LinearLayout ll1 = new LinearLayout(context);
        ll1.setOrientation(LinearLayout.VERTICAL);
        //ll1.setDividerPadding(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //int th = ll1.getHeight();
        //System.out.println("==================total height:" + th);
        LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 100.0f);

        //添加viewPager滑动切换页面
        List<View> vlist = new ArrayList<View>();
        for(TabViewItem item : tabs) {
            vlist.add(item.getMview());
        }
        ViewPageAdapter vpa= new ViewPageAdapter(vlist);
        mViewPager = new ViewPager(context);
        mViewPager.setAdapter(vpa);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());

        ll1.addView(mViewPager, llp1);

        //添加下面的三个tab，每个是一个LinearLayout
        LinearLayout ll2 = new LinearLayout(context);
        ll2.setBackgroundColor(Color.WHITE);
        ll2.setOrientation(LinearLayout.VERTICAL);

        //首先在下面这个里面方式一个垂直layout，用来放置一条线，和三个按钮layout
        LinearLayout line = new LinearLayout(context);
        line.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams linep = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        ll2.addView(line, linep);

        //方式tab容器
        LinearLayout tabview = new LinearLayout(context);
        tabview.setOrientation(LinearLayout.HORIZONTAL);
        //tabview.setBackgroundColor(Color.RED);
        //添加tab显示

        TabTouchLisenter tl = new TabTouchLisenter();
        tl.setRoot(this);
        int i=1;
        for(TabViewItem item : tabs) {
            TabViewItemView tabitemv = new TabViewItemView(context);
            if(i==1) {
                tabitemv.setActive(1);
            } else {
                tabitemv.setActive(0);
            }
            tabitemv.setSeek(i);
            tabitemv.setTouchLisenter(tl);
            tabitemv.setTitle(item.getTitle());
            LinearLayout.LayoutParams tab1p = new LinearLayout.LayoutParams(120, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            tabview.addView(tabitemv, tab1p);
            tabViewList.add(tabitemv);

            i++;
        }

        LinearLayout.LayoutParams tabcontentp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getTabPXHeight(context)-1);
        ll2.addView(tabview, tabcontentp);

        LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getTabPXHeight(context), 0f);
        ll1.addView(ll2, llp2);

        this.addView(ll1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 按照英寸高度转为像素高度
     * @return
     */
    public int getTabPXHeight(Context context) {
        int px = 40;
        System.out.println("=======================px1:" + px);
        float density = context.getResources().getDisplayMetrics().density;
        px = (int) (px * density + 0.5f);
        System.out.println("=======================px2:" + px);
        return px;
    }

    /**
     * tab的触摸事件，用来完成切换
     */
    public class TabTouchLisenter {
        public TabView getRoot() {
            return root;
        }

        public void setRoot(TabView root) {
            this.root = root;
        }

        TabView root = null;
        /**
         * 触摸了第i个tab
         * @param i
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void touchTab(int i) {
            System.out.println("=======================切换tab请求，切换到：" + i);
            for(TabViewItemView tv : tabViewList) {
                int act = 0;
                if(tv.getSeek()==i) {
                    act = 1;
                } else {
                    act = 0;
                }
                if(tv.getActive()!=act) {
                    //需要切换的时候才处理切换重画
                    tv.setActive(act);
                    //重画
                    tv.invalidate();
                }
            }
            //设置滑动页面当前页面
            mViewPager.setCurrentItem(i-1);
        }
    }

    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //            Log.d(TAG,"onPageScrooled");
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onPageSelected(int position) {
            int i = position+1;
            for(TabViewItemView tv : tabViewList) {
                int act = 0;
                if(tv.getSeek()==i) {
                    act = 1;
                } else {
                    act = 0;
                }
                if(tv.getActive()!=act) {
                    //需要切换的时候才处理切换重画
                    tv.setActive(act);
                    //重画
                    tv.invalidate();
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
