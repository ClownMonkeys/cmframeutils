package setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cm.cmframeutils.BaseActivity;
import com.cm.cmframeutils.R;

/**
 * ProjectName：cmframeutils
 * PackageName：PACKAGE_NAME
 * FileName：setting.SettingActivity.java
 * Date：2015/11/24 52
 * Author：大鹏
 * ClassName:setting.SettingActivity
 **/
public class SettingActivity extends BaseActivity {
    private ImageView nav_image_back;
    private TextView nav_text_titel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        nav_image_back = (ImageView) findViewById(R.id.nav_image_back);
        nav_text_titel= (TextView) findViewById(R.id.nav_text_titel);
        nav_text_titel.setText(R.string.nav_settings_title);
    }

    @Override
    public void onClickView(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.nav_image_back:
                finish();
                overridePendingTransition(R.anim.to_left, R.anim.to_right);
                break;
        }
    }
}
