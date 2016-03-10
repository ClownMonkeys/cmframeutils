package nfc;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.cm.cmframeutils.BaseActivity;
import com.cm.cmframeutils.R;

import logutils.LogUtils;
import utlis.ToastUtils;

/**
 * ProjectName：cmframeutils
 * PackageName：nfc
 * FileName：NfcActivity.java
 * Date：2015/11/25 19
 * Author：大鹏
 * ClassName:NfcActivity
 **/
@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class NfcActivity extends BaseActivity {
    NfcAdapter nfcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        // 获取默认的NFC控制器
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        }
        if (nfcAdapter == null) {
            ToastUtils.show(this, "此设备不支持NFC");
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            ToastUtils.show(this, "请在系统设置中先启用NFC功能！");
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //得到是否检测到ACTION_TECH_DISCOVERED触发
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            //处理该intent
            LogUtils.e("NfcAdapter.ACTION_TECH_DISCOVERED :" + getIntent().getAction());
            processIntent(getIntent());
        }
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())){
            LogUtils.e("NfcAdapter.ACTION_NDEF_DISCOVERED :" + getIntent().getAction());

        }
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())){
            LogUtils.e("NfcAdapter.ACTION_TAG_DISCOVERED :"+getIntent().getAction());

        }
        if (NfcAdapter.ACTION_ADAPTER_STATE_CHANGED.equals(getIntent().getAction())){
            LogUtils.e("NfcAdapter.ACTION_ADAPTER_STATE_CHANGED :" +getIntent().getAction());
        }
    }

    //字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        LogUtils.e("Discovered tag with intent: " + action);
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

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private void processIntent(Intent intent) {
        //取出封装在intent中的TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        for (String tech : tagFromIntent.getTechList()) {
            LogUtils.e(tech);
        }
        boolean auth = false;
        //读取TAG
        MifareClassic mfc = MifareClassic.get(tagFromIntent);
        try {
            String metaInfo = "";
            //Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();//获取TAG的类型
            int sectorCount = mfc.getSectorCount();//获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
                    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";
            for (int j = 0; j < sectorCount; j++) {
                //Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j,
                        MifareClassic.KEY_DEFAULT);
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ":验证成功\n";
                    // 读取扇区中的块
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo += "Block " + bIndex + " : "
                                + bytesToHexString(data) + "\n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ":验证失败\n";
                }
            }
            LogUtils.e("数据信息 ："+metaInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
