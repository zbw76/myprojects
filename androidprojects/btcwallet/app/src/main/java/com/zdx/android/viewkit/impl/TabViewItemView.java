package com.zdx.android.viewkit.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;

import com.zdx.android.viewkit.TabView;

import static android.graphics.Color.valueOf;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TabViewItemView extends View {
    private static final int bgcolor = 0xffb3dafd;
    private static final int actbgcolor = 0xffffffff;
    private static final int textcolor = 0xff000000;
    private static final int linecolor = 0xffaaaaaa;

    public TabView.TabTouchLisenter getTouchLisenter() {
        return touchLisenter;
    }

    public void setTouchLisenter(TabView.TabTouchLisenter touchLisenter) {
        this.touchLisenter = touchLisenter;
    }
    //触摸事件监听处理器
    TabView.TabTouchLisenter touchLisenter = null;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    //是否活动tab
    private int active = 0;

    public int getSeek() {
        return seek;
    }

    public void setSeek(int seek) {
        this.seek = seek;
    }

    //第几个标签
    private int seek = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //标题
    private String title = "";

    public TabViewItemView(Context context) {
        super(context);
    }

    /**
     * 绘制tab图形
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        System.out.println("=====================重画了=====");
        super.onDraw(canvas);

        int w = this.getWidth();
        int h = this.getHeight();

        //绘制背景色
        Paint lp = new Paint();
        if(active==1) {
            lp.setColor(actbgcolor);
        } else {
            lp.setColor(bgcolor);
        }
        lp.setStyle(Paint.Style.FILL);
        //lp.setFakeBoldText(true);
        //lp.setTextSize(100);
        canvas.drawRect(0, 0, w, h, lp);

        //绘制图标

        //绘制文字
        Paint bkp = new Paint();
        bkp.setColor(textcolor);
        int fsize = h*3/5;
        bkp.setTextSize(fsize);
        Rect rect = new Rect();
        bkp.getTextBounds(title, 0, title.length(), rect);
        int tw = rect.width();
        int th = rect.height();
        int seekx = (w-tw)/2;
        int seeky = h - (h-th)/2;
        canvas.drawText( title, seekx, seeky, bkp);
    }

    /**
     * 触摸事件处理
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("==============================触摸事件：" + seek);
        super.onTouchEvent(event);
        if(this.touchLisenter!=null) {
            this.touchLisenter.touchTab(seek);
        }
        return true;
    }
}
