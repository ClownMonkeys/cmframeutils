package about;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cm.cmframeutils.BaseActivity;
import com.cm.cmframeutils.R;

import swipebacklayout.DemoActivity;
import utlis.ToastUtils;

/**
 * ProjectName：cmframeutils
 * PackageName：about
 * FileName：AboutActivity.java
 * Date：2015/10/10 30
 * Author：大鹏
 * ClassName:AboutActivity
 **/
public class AboutActivity extends BaseActivity {
    private ImageView nav_image_menu;
    private TextView nav_text_titel;
    private ImageView nav_image_back;
    private Context mContext;

    private TextView about_text_versioncode;
    private LinearLayout about_linear_checkforupdates,about_linear_imprint,about_linear_toscore ;
    private LinearLayout about_linear_feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        mContext=AboutActivity.this;

        nav_image_menu = (ImageView) findViewById(R.id.nav_image_menu);
        nav_text_titel = (TextView) findViewById(R.id.nav_text_titel);
        nav_image_menu.setVisibility(View.GONE);
        nav_text_titel.setText(R.string.nav_about_title);
        nav_image_back= (ImageView) findViewById(R.id.nav_image_back);
        about_text_versioncode= (TextView) findViewById(R.id.about_text_versioncode);

        about_linear_checkforupdates= (LinearLayout) findViewById(R.id.about_linear_checkforupdates);
        about_linear_imprint= (LinearLayout) findViewById(R.id.about_linear_imprint);
        about_linear_feedback= (LinearLayout) findViewById(R.id.about_linear_feedback);
        about_linear_toscore= (LinearLayout) findViewById(R.id.about_linear_toscore);

        //获取客户端版本信息
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            about_text_versioncode.setText("版本号 " + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void onClickView(View view) {
        super.onClickView(view);
        switch (view.getId()){
            case R.id.nav_image_back:
                finish();
                overridePendingTransition(R.anim.to_left, R.anim.to_right);
                break;
            case R.id.about_linear_checkforupdates:
                startActivity(DemoActivity.class);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                ToastUtils.show(mContext,"about_linear_checkforupdates");
                break;
            case R.id.about_linear_imprint:
                ToastUtils.show(mContext,"about_linear_imprint");
                break;
            case R.id.about_linear_feedback:
                ToastUtils.show(mContext,"about_linear_feedback");
                break;
            case R.id.about_linear_toscore:
                Uri uri = Uri.parse("market://details?id="+getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
