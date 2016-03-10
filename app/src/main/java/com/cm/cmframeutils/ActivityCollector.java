package com.cm.cmframeutils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName：cmframeutils
 * PackageName：com.cm.cmframeutils
 * FileName：ActivityCollector.java
 * Date：2015/8/28 01
 * Author：大鹏
 * ClassName:ActivityCollector
 **/
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    //添加一个活动
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //清除一个活动
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //销毁所有活动
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
