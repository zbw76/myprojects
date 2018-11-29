package com.zdx.android.viewkit;

import android.media.Image;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 用来描述tabview里面的一个tab界面内容，包含tab标题图标等信息和主内容显示View
 */
public class TabViewItem {
    //tab内容view
    private View mview;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //tab标题内容，显示在tab内
    private String title;

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    //tab标题图标，显示在tab内，配合文字
    private Image icon;

    public TabViewItem(String _title, View _v, Image _icon) {
        this.title = _title;
        mview = _v;
        icon = _icon;
    }

    public View getMview() {
        return mview;
    }

    public void setMview(View mview) {
        this.mview = mview;
    }
}
