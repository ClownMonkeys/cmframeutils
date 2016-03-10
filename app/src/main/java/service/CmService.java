package service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.cm.cmframeutils.MainActivity;
import com.cm.cmframeutils.R;

import java.util.Timer;
import java.util.TimerTask;

import logutils.LogUtils;

/**
 * ProjectName：cmframeutils
 * PackageName：service
 * FileName：CmService.java
 * Date：2015/10/12 14
 * Author：大鹏
 * ClassName:CmService
 **/
public class CmService extends Service {
    public MyBinder mBinder = new MyBinder();
    Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new Notification(R.drawable.ic_launcher,
                "有通知到来", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容",
                pendingIntent);
        startForeground(1, notification);
        LogUtils.e("onCreate() executed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        LogUtils.e("onDestroy");
    }

    /**
     * 运行在后台
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("onCreate");
                timer = new Timer(true);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        LogUtils.e("a.a");
                    }
                }, 1000, 1000 * 60);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e("onUnbind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public void startDownload() {
            LogUtils.e("startDownload() executed");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 执行具体的下载任务
                }
            }).start();
        }
    }
}
