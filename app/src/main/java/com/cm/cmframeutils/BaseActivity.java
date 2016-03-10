package com.cm.cmframeutils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import logutils.LogUtils;
import swipebacklayout.SwipeBackActivity;
import swipebacklayout.SwipeBackLayout;
import utlis.ToastUtils;

/**
 * ProjectName：cmframeutils
 * PackageName：com.cm.cmframeutils
 * FileName：BaseActivity.java
 * Date：2015/8/14 15
 * Author：大鹏
 * ClassName:BaseActivity
 **/
public class BaseActivity extends SwipeBackActivity implements View.OnClickListener {
    protected Context mContext;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("onCreate");
        mContext = getApplicationContext();
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //获取当前实例的类名
        LogUtils.e("BaseActivity:" + getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    public void showToastMessage(Context context, String message) {
        ToastUtils.show(context, message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.e("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.e("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy");
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {}


    /**
     * 通过Class跳转界面 *
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面 *
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action跳转界面 *
     */
    protected void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面 *
     */
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void initView() {}

    public void onClickView(View view) {}
}
