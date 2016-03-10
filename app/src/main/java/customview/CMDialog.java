package customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cm.cmframeutils.R;

import listener.NegativeBtnListener;
import listener.PositiveBtnListener;

/**
 * ProjectName：cmframeutils
 * PackageName：customview
 * FileName：CMDialog.java
 * Date：2015/8/14 13
 * Author：大鹏
 * ClassName:CMDialog
 **/
public class CMDialog extends Dialog {
    private TextView cmdTextTitle;
    private TextView cmdTextContent;
    private Button cmdBtnnegative;
    private Button cmdBtnpositive;
    private Context mContext;

    public NegativeBtnListener viewListenerInterface;
    public PositiveBtnListener positiveListenerl;

    public CMDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CMDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    protected CMDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cmdialog);
        cmdTextTitle = (TextView) findViewById(R.id.cmddialog_text_title);
        cmdTextContent = (TextView) findViewById(R.id.cmddialog_text_content);
        cmdBtnnegative = (Button) findViewById(R.id.cmddialog_btn_negative);
        cmdBtnpositive = (Button) findViewById(R.id.cmddialog_btn_positive);
        cmdBtnnegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewListenerInterface != null)
                    viewListenerInterface.onClick(v.getId());
            }
        });
        cmdBtnpositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positiveListenerl != null)
                    positiveListenerl.onClickCallBack("123");
            }
        });
    }

    public void showDialog() {
        CMDialog.this.show();
    }

    public void dissMissDaialog(){
        CMDialog.this.dismiss();
    }

    public String getTextValue() {
        return cmdTextTitle.getText().toString();
    }

    public void setTextValue(String value) {
        cmdTextTitle.setText(value);
    }

    public void setTextFontsType() {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-Light.ttf");
        cmdTextTitle.setTypeface(typeface);
        cmdTextContent.setTypeface(typeface);
    }

    public void setBtnNegativeListener(NegativeBtnListener negativeListener) {
        this.viewListenerInterface = negativeListener;
    }

    public void setBtnPositiveListener(PositiveBtnListener positiveListenerl) {
        this.positiveListenerl = positiveListenerl;
    }
}
