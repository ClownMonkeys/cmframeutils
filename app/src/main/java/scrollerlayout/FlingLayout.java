package scrollerlayout;

/**
 * ProjectName：cmframeutils
 * PackageName：customview
 * FileName：FlingLayout.java
 * Date：2015/11/19 59
 * Author：大鹏
 * content:弹性布局
 */

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import logutils.LogUtils;

public class FlingLayout extends FrameLayout {

    public final static int NONE = 0;
    public final static int SCROLLING = 1;
    public final static int FLING = 2;
    private int stateType = NONE;

    protected Pullable mPullView;
    private int mTouchSlop;
    private Scroller mScroller;
    protected float downY, downX;
    private boolean isScrolling = false;
    protected float tepmY;
    private static final int MAX_DURATION = 300;
    private boolean canPullUp = true;
    private boolean canPullDown = true;
    protected OnScrollListener mOnScrollListener;
    protected int maxDistance = 0;

    public FlingLayout(Context context) {
        this(context, null);
    }

    public FlingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context, new DecelerateInterpolator());
    }


    @Override
    public void computeScroll() {
        if (!mScroller.isFinished()) {
            if (mScroller.computeScrollOffset()) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                postInvalidate();
            }
        }
        super.computeScroll();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        int offsetTop = getOffsetTop();
//        LogUtils.e("this x value : "+ x);
//        LogUtils.e("this y value : "+ y);
//        LogUtils.e("this offsetTop value : "+ offsetTop);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录按下的点（x,y）
                tepmY = downY = y;
                downX = x;
                //返回是否已完成滚动。
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    if (offsetTop != 0) {
                        setState(SCROLLING, offsetTop);//
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float distY = y - downY;
                float dy = Math.abs(distY);
                LogUtils.e("this dy value : " + dy);
                //意图分析，避免误操作
                if (isScrolling || (dy > mTouchSlop && dy > Math.abs(x - downX))) {
                    isScrolling = true;
                    int dataY = (int) (y - tepmY);
                    tepmY = y;
                    if (offsetTop == 0) {
                        //开始时 在0,0处
                        //判断是否可以滑动
                        if ((canPullDown() && dataY > 0) || (canPullUp() && dataY < 0)) {
                            if (mPullView != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                                    ((View) mPullView).setOverScrollMode(View.OVER_SCROLL_NEVER);//去除边缘效果
                                }
                            }
                            setState(SCROLLING, 0);//
                            scrollBy(0, -dataY);
                            return true;
                        } else if (mPullView != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                                ((View) mPullView).setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
                            }
                        }
                    } else {
                        //当不在0,0处
                        ev.setAction(MotionEvent.ACTION_CANCEL);//屏蔽原事件
                        LogUtils.e("this offsertop value : " + offsetTop);
                        if ((offsetTop < 0 && offsetTop - dataY >= 0) || (offsetTop > 0 && offsetTop - dataY <= 0)) {
                            //在0,0附近浮动
                            ev.setAction(MotionEvent.ACTION_DOWN);
                            scrollTo(0, 0);
                        } else if ((offsetTop > 0 && dataY < 0) || (offsetTop < 0 && dataY > 0)) {
                            //是否超过最大距离
                            if (maxDistance == 0 || Math.abs(offsetTop) < maxDistance) {
                                scrollBy(0, -dataY / 2);
                            } else if (offsetTop > maxDistance) {
                                scrollTo(0, maxDistance);
                            } else if (offsetTop < -maxDistance) {
                                scrollTo(0, -maxDistance);
                            }
                        } else {
                            scrollBy(0, -dataY);
                        }
                    }
                } else {
                    ev.setLocation(ev.getX(), tepmY);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                fling(offsetTop);
                isScrolling = false;
                break;
        }
        return super.dispatchTouchEvent(ev) || isScrolling;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
        }
        return super.onTouchEvent(event);
    }

    protected boolean canPullUp() {
        if (mPullView != null) {
            return canPullUp && mPullView.isGetBottom();
        }
        return canPullUp;
    }

    protected boolean canPullDown() {
        if (mPullView != null) {
            return canPullDown && mPullView.isGetTop();
        }
        return canPullDown;
    }

    protected void fling(int offsetTop) {
        startScrollTo(offsetTop, 0);
    }


    protected void onScroll(int y) {

    }

    protected void onScrollChange(int state, int y) {

    }

    public int startScrollBy(int startY, int dy) {
        setState(FLING, startY + dy);
        int duration = Math.abs(dy);
        int time = duration > MAX_DURATION ? MAX_DURATION : duration;
        mScroller.startScroll(0, startY, 0, dy, time);
        invalidate();
        return time;
    }

    public int startScrollTo(int startY, int endY) {
        return startScrollBy(startY, endY - startY);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof Pullable && mPullView == null) {
            mPullView = (Pullable) child;
        }
        super.addView(child, index, params);
    }


    public int getOffsetTop() {
        return getScrollY();
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        onScroll(y);

        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(this, y);
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        scrollTo(getScrollX() + x, getOffsetTop() + y);
    }


    private void setState(int state, int y) {
        if (stateType != state || y != getOffsetTop()) {
            onScrollChange(state, y);
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollChange(this, state, y);
            }
        }
        stateType = state;
    }

    public static interface OnScrollListener {
        public void onScroll(FlingLayout flingLayout, int y);

        public void onScrollChange(FlingLayout flingLayout, int state, int y);

    }

    public void setOnScrollListener(OnScrollListener mOnScrollListener) {
        this.mOnScrollListener = mOnScrollListener;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }


    public void setCanPullDown(boolean canPullDown) {
        this.canPullDown = canPullDown;
        if (!canPullDown && getOffsetTop() < 0) {
            scrollTo(getScrollX(), 0);
        }
    }

    public void setCanPullUp(boolean canPullUp) {
        this.canPullUp = canPullUp;
        if (!canPullUp && getOffsetTop() > 0) {
            scrollTo(getScrollX(), 0);
        }
    }
}
