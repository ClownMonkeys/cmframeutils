package utlis;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cm.cmframeutils.R;

/**
 * ProjectName：cmframeutils
 * PackageName：utlis
 * FileName：ToastCustom.java
 * Date：2015/10/16 35
 * Author：大鹏
 * ClassName:ToastCustom
 **/
public class ToastCustom {

    private static ToastCustom toastCustom;
    private Toast toast;

    private ToastCustom() {
    }

    public static ToastCustom createToastConfig() {
        if (toastCustom == null) {
            toastCustom = new ToastCustom();
        }
        return toastCustom;
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param root
     * @param tvString
     */

    public void ToastShow(Context context, ViewGroup root, String tvString) {
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_toast, root);
        TextView text = (TextView) layout.findViewById(R.id.toast_text_message);
        text.setText(tvString);
        text.setTextColor(Color.parseColor("#ffffff"));
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
