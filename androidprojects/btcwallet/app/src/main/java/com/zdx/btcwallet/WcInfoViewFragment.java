package com.zdx.btcwallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 钱包信息显示view例子
 */
public class WcInfoViewFragment extends Fragment {
    LinearLayout mView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView==null) {
            mView = new LinearLayout(container.getContext());
            mView.setBackgroundColor(Color.RED);
        }
        return mView;
    }
}
