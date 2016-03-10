package customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.cm.cmframeutils.R;

/**
 * ProjectName：cmframeutils
 * PackageName：customview
 * FileName：CustomRelativeLayout.java
 * Date：2015/9/2 55
 * Author：大鹏
 * ClassName:CustomRelativeLayout
 **/
public class CustomRelativeLayout extends RelativeLayout {
    Paint mPaint;
    public CustomRelativeLayout(Context context) {
        super(context);
//        initPaint();
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initPaint() {
        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(getResources().getColor(R.color.primary_dark));
        //给Paint加上抗锯齿标志
        mPaint.setAntiAlias(true);
        /**
         * 设置填充样式
         *Paint.Style.FILL:填充内部
         *Paint.Style.FILL_AND_STROKE:填充内部和描边
         *Paint.Style.STROKE：仅描边
         */
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置画笔宽度
        mPaint.setStrokeWidth(30);
        /**
         *设置阴影
         *float radius:
         * float dx:x轴坐标
         * float dy:y轴坐标
         * int shadowColor:阴影颜色
         */
        mPaint.setShadowLayer(10, 15, 15, Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(getResources().getColor(R.color.primary));
        //给Paint加上抗锯齿标志
        mPaint.setAntiAlias(true);
        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        mPaint.setDither(true);
        /**
         * 设置填充样式
         *Paint.Style.FILL:填充内部
         *Paint.Style.FILL_AND_STROKE:填充内部和描边
         *Paint.Style.STROKE：仅描边
         */
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置画笔宽度
        mPaint.setStrokeWidth(30);
        /**
         *设置阴影
         *float radius:阴影的半径
         * float dx:x轴坐标
         * float dy:y轴坐标
         * int shadowColor:阴影颜色
         */
        mPaint.setShadowLayer(150, 0, 0, getResources().getColor(R.color.black));

        canvas.drawColor(getResources().getColor(R.color.transparent));
        //绘制图片
//        Resources res = getResources();
//        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
//        canvas.drawBitmap(bmp,40,40,mPaint);

        //将位置移动画纸的坐标点:150,150
//        canvas.translate(canvas.getWidth() / 2, 200);
        /**
         *float cx:x轴坐标，圆的中心的x坐标
         *float cy:y轴坐标，圆的中心的y坐标
         *float radius:圆的半径
         *@NonNull Paint paint:画笔
         */
//        canvas.drawCircle(0,0, 150, mPaint);

        //绘制直线
//        canvas.drawLine(0,200,200,0,mPaint);

//        canvas.drawRect(, 170, 0, 130, mPaint);

        /**
         * drawRect(float left, float top, float right, float bottom, @NonNull Paint paint)
         * 矩形的高 height = bottom  - right
         * 矩形的宽 width  = right – left
         * 第一个点：A（0，75） X,Y
         * B（0，170） X,Y
         * C(1080,75)X,Y
         * D(1080,170)X,Y
         */

        canvas.drawRect(0, 10, 1080, 170, mPaint);// 长方形
    }
}
