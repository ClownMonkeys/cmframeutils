package com.cm.cmframeutils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Timer;

import about.AboutActivity;
import dataAdapter.DataAdapter;
import logutils.LogUtils;
import nfc.NfcActivity;
import phonestatus.PhoneStatusActivity;
import pulltorefresh.PullToRefreshBase;
import pulltorefresh.PullToRefreshListView;
import refreshview.PullToRefreshView;
import service.CmService;
import setting.SettingActivity;
import snackView.SnackBar;
import stepdetector.StepDetectorActivity;
import utlis.PreferencesUtils;
import utlis.ToastCustom;

public class MainActivity extends Activity {
    private Context mContext;
    /**
     * 双击退出函数
     */
    private Boolean isExit = false;
    private long exitTime = 0;

    private ImageView nav_image_back;
    private PullToRefreshListView main_listview;
    private ArrayList<Integer> mlist;
    private DataAdapter<Integer> dataBase;
    private SnackBar mSanckbar = null;
    private DrawerLayout main_drawerlayout;
    private NavigationView main_navigationview;

    String message = "";
    int messageRes = -1;
    short duration = SnackBar.SHORT_SNACK;
    SnackBar.Style style = SnackBar.Style.DEFAULT;
    SnackBar.Style bgStyle;
    int bgColor = R.color.blue_light;

    private PullToRefreshView mPullToRefreshView;

    private ImageView nav_image_menu;

    Timer mTimerExit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferencesUtils.getBoolean(this, "isFirst")){
            startActivity(new Intent(MainActivity.this,NavigationPageActivity.class));
            finish();
        }else{
            setContentView(R.layout.activity_main);
            ActivityCollector.addActivity(this);
            LogUtils.e("MainActivity的返回栈ID是：" + getTaskId());
            initView();
        }
    }

    public void initView() {
        mContext = MainActivity.this;
        nav_image_back = (ImageView) findViewById(R.id.nav_image_back);

        main_drawerlayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        nav_image_menu = (ImageView) findViewById(R.id.nav_image_menu);
        nav_image_menu.setVisibility(View.VISIBLE);

        main_navigationview = (NavigationView) findViewById(R.id.main_navigationview);
        main_navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            private MenuItem mPreMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (mPreMenuItem != null) {
                    mPreMenuItem.setChecked(false);
                }
                menuItem.setChecked(true);
                main_drawerlayout.closeDrawers();
                mPreMenuItem = menuItem;
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
//                        startService(new Intent(mContext, CmService.class));
//                        ToastUtils.show(mContext, "nav_home");
                        startActivity(new Intent(mContext, PhoneStatusActivity.class));
                        break;
                    case R.id.nav_messages:
//                        stopService(new Intent(mContext, CmService.class));
//                        ToastUtils.show(mContext, "nav_messages");
                        startActivity(new Intent(mContext, StepDetectorActivity.class));
                        break;
                    case R.id.nav_friends:
//                        bindService(new Intent(mContext, CmService.class), serviceConnection, BIND_AUTO_CREATE);
//                        ToastUtils.show(mContext, "nav_friends");
                        startActivity(new Intent(mContext, NfcActivity.class));
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case R.id.nav_setting:
//                        unbindService(serviceConnection);
//                        ToastUtils.show(mContext, "nav_setting");
                        startActivity(new Intent(mContext, SettingActivity.class));
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case R.id.nav_help:
                        startActivity(new Intent(mContext, AboutActivity.class));
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                }
                return true;
            }
        });

        main_listview = (PullToRefreshListView) findViewById(R.id.main_listview);
        mlist = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            mlist.add(i);
        }
        dataBase = new DataAdapter<Integer>(mContext, mlist);
        main_listview.setAdapter(dataBase);
        main_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("点击了" + position);
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

//                mSanckbar = new SnackBar.Builder((Activity) mContext).withOnClickListener(new SnackBar.OnMessageClickListener() {
//                    @Override
//                    public void onMessageClick(Parcelable token) {
//                        ToastUtils.show(mContext, "点击了");
//                    }
//                })
//                        .withMessage("Message : " + position)
//                        .withActionMessage("Action")
//                        .withStyle(style)
//                        .withBackgroundColorId(bgColor)
//                        .withDuration(duration)
//                        .show();
            }
        });

        main_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new GetDataTask().execute();
            }
        });

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_refresshview);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 1000);
            }
        });

//        final CMDialog cmDialog = new CMDialog(mContext, R.style.cmd_dialog_style);
//        cmDialog.setBtnNegativeListener(new NegativeBtnListener() {
//            @Override
//            public void onClick(int position) {
//                ToastUtils.show(mContext, "取消" + position);
//                cmDialog.dissMissDaialog();
//            }
//        });
//        cmDialog.setBtnPositiveListener(new PositiveBtnListener() {
//
//            @Override
//            public void onClickCallBack(String value) {
//                ToastUtils.show(mContext, "确定" + value);
//                cmDialog.dissMissDaialog();
//            }
//        });
//        cmDialog.showDialog();
//        //一定要写在show的后面
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = cmDialog.getWindow().getAttributes();
//        lp.width = (int) (display.getWidth() - 128); //设置宽度
//        cmDialog.getWindow().setAttributes(lp);

    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.nav_image_back:
                ActivityCollector.finishAll();
                break;
            case R.id.nav_image_menu:
                //END即gravity.right 从右向左显示   START即left  从左向右弹出显示
                if (main_drawerlayout.isDrawerVisible(GravityCompat.END)) {
                    main_drawerlayout.closeDrawer(GravityCompat.END);//关闭抽屉
                } else {
                    main_drawerlayout.openDrawer(GravityCompat.END);//打开抽屉
                }
//                main_drawerlayout.openDrawer(Gravity.RIGHT);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (main_drawerlayout.isDrawerVisible(GravityCompat.END)) {
                main_drawerlayout.closeDrawer(GravityCompat.END);//关闭抽屉
            } else {
                exit();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastCustom toastCustom = ToastCustom.createToastConfig();
            toastCustom.ToastShow(mContext, (ViewGroup) findViewById(R.id.toast_layout_root), "再次点击退出程序哟");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            ActivityCollector.finishAll();
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CmService.MyBinder cmservice = (CmService.MyBinder) service;
            cmservice.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private class GetDataTask extends AsyncTask<Void, Void, ArrayList<Integer>> {

        @Override
        protected ArrayList<Integer> doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return mlist;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> result) {
            mlist.add(1);
            dataBase.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            main_listview.onRefreshComplete();
            super.onPostExecute(result);
        }
    }
}
