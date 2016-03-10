package customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cm.cmframeutils.R;

/**
 * ProjectName：cmframeutils
 * PackageName：customview
 * FileName：TextViewTypeFace.java
 * Date：2015/10/12 06
 * Author：大鹏
 * ClassName:TextViewTypeFace
 **/
public class TextViewTypeFace extends TextView {
    private int rippleColor;

    public TextViewTypeFace(Context context) {
        super(context);
    }

    public TextViewTypeFace(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public TextViewTypeFace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextViewTypeFace(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context,final AttributeSet attrs){
        if (isInEditMode()){
            return;
        }
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewTypeFace);
        rippleColor = typedArray.getColor(R.styleable.TextViewTypeFace_customtextcolor, getResources().getColor(R.color.about_text_color));
        setTextColor(rippleColor);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Light.ttf"));
    }
}
