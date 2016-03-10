package phonestatus;

import android.content.Context;

/**
 * ProjectName：cmframeutils
 * PackageName：phonestatus
 * FileName：PhoneInfoImpl.java
 * Date：2015/12/9 58
 * Author：大鹏
 * ClassName:PhoneInfoImpl
 **/
public class PhoneInfoImpl implements IphoneInfo {
    private Context mContext;
    public  PhoneInfoImpl(Context mContext){
        super();
        mContext=this.mContext;
    }
    @Override
    public String  getPhoneMoney() {
        return android.os.Build.MODEL;
    }
}
