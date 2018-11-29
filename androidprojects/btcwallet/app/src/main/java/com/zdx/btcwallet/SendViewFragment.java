package com.zdx.btcwallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SendViewFragment extends Fragment {
    LinearLayout mView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView==null) {
            mView = new LinearLayout(container.getContext());
            mView.setBackgroundColor(Color.GREEN);
        }
        return mView;
    }
}
