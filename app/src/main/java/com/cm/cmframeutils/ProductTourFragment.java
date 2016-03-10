package com.cm.cmframeutils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ProjectName：cmframeutils
 * PackageName：com.cm.cmframeutils
 * FileName：ProductTourFragment.java
 * Date：2015/8/31 34
 * Author：大鹏
 * ClassName:ProductTourFragment
 **/
public class ProductTourFragment extends Fragment {
    final static String LAYOUT_ID = "layoutid";

    public static ProductTourFragment newInstance(int layoutId) {
        ProductTourFragment pane = new ProductTourFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        return rootView;
    }
}
