package stepdetector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.cm.cmframeutils.BaseActivity;
import com.cm.cmframeutils.R;

/**
 * ProjectName：cmframeutils
 * PackageName：stepdetector
 * FileName：StepDetectorActivity.java
 * Date：2015/11/26 49
 * Author：大鹏
 * ClassName:StepDetectorActivity
 **/
public class StepDetectorActivity extends BaseActivity {
    private TextView stepdetector_text_num;
    private Context mContext;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int num=StepDetector.CURRENT_SETP;
            switch (msg.what){
                case 1024:
                    stepdetector_text_num.setText(" "+num);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepdetector);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        mContext=StepDetectorActivity.this;
        stepdetector_text_num = (TextView) findViewById(R.id.stepdetector_text_num);
        startService(new Intent(mContext, StepService.class));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (StepService.flag) {
                        handler.sendEmptyMessage(1024);
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClickView(View view) {
        super.onClickView(view);
        switch (view.getId()) {
            case R.id.nav_image_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(mContext, StepService.class));
    }
}
