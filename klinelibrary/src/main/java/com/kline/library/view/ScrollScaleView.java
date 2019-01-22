package com.kline.library.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;


import com.kline.library.bean.CandleData;

import java.util.List;

/**
 * Created by yjq
 * 2018/6/6.
 * 移动缩放
 */

public abstract class ScrollScaleView extends View implements  GestureDetector.OnGestureListener,ScaleGestureDetector.OnScaleGestureListener{
    protected boolean isShowCrossLine = false;
    protected boolean touch = false;
    protected float mScaleX = 1;
    protected boolean isSinglePointer = true;
    float mRound;
    float oldcurrX = 0;
    protected ScaleGestureDetector mScaleDetector;
    protected GestureDetectorCompat mDetector;
    protected List<CandleData> kLists;
    protected int mSelectedIndex = -1;
    public OverScroller mScroller;
    protected float mScrollX = 0;
    //x轴最大的缩放程度
    protected float mScaleXMax = 1.5f;
    //x轴最小的缩放程度
    protected float mScaleXMin = 0.01f;

    protected static final int KLINE_SPEC =  3;
    public ValueAnimator mAnimator;

    public boolean isLongPress;
    public boolean isCancle;



    public ScrollScaleView(Context context) {
        super(context);
        init();
    }

    public ScrollScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    long time;

    void  init(){
        mDetector = new GestureDetectorCompat(getContext(), this);
        mScaleDetector = new ScaleGestureDetector(getContext(), this);
        mScroller = new OverScroller(getContext());

        mAnimator = ValueAnimator.ofInt(0, 100);
        mAnimator.setDuration(5000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                time = System.currentTimeMillis();

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isCancle){
                    isShowCrossLine = false;
                    //Logging.e("ScrollScaleView onAnimationEnd","isShowCrossLine:"+isShowCrossLine);
                    if (dataListener!=null){
                        dataListener.cancle();
                    }
                    invalidate();
                    //Logging.e("ScrollScaleView","time:"+(System.currentTimeMillis()-time));
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isCancle = true;
                //Logging.e("ScrollScaleView","onAnimationCancel:"+(System.currentTimeMillis()-time));

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (!isTouch()) {
                float currX = mScroller.getCurrX();
                mRound = oldcurrX - currX ;
                mScrollX += mRound;
                oldcurrX = currX;
            } else {
                mScroller.forceFinished(true);
            }
            invalidate();
        }
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!isShowCrossLine) {
            mRound = distanceX;
            mScrollX += mRound;
            oldcurrX = mScrollX;
            invalidate();
            return true;
        }
        return false;
    }
    /**
     * 是否在触摸中
     *
     * @return
     */
    public boolean isTouch() {
        return touch;
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        isShowCrossLine = true;

    }



    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!isTouch()&&!isShowCrossLine) {
            if (isSinglePointer){
                mScroller.fling(
                        (int) mScrollX,
                        0,
                        Math.round(velocityX /** mScaleX*/),
                        0,
                        Integer.MIN_VALUE,
                        Integer.MAX_VALUE,
                        0,
                        0);
            }
        }
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        mScaleX *= scaleFactor;
        if (mScaleX < mScaleXMin) {
            mScaleX = mScaleXMin;
        } else if (mScaleX > mScaleXMax) {
            mScaleX = mScaleXMax;
        } else {
            invalidate();
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touch = true;
                break;
            case MotionEvent.ACTION_MOVE:
//                getParent().requestDisallowInterceptTouchEvent(true);
                if (event.getPointerCount() == 1) {
                    //长按之后移动
                    if (isShowCrossLine) {
                        onLongPress(event);
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                isSinglePointer = false;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isSinglePointer = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
//                getParent().requestDisallowInterceptTouchEvent(false);
                //Logging.e("ScrollScaleView","ACTION_UP");
                if (isShowCrossLine){
                    if (!mAnimator.isRunning()){
                        //Logging.e("ScrollScaleView","isRunning:"+(!mAnimator.isRunning()));
                        mAnimator.start();
                    }
                }
                touch = false;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                //Logging.e("ScrollScaleView","ACTION_CANCEL");
                if (isShowCrossLine){
                    if (!mAnimator.isRunning()){
                        mAnimator.start();
                    }
                }
                touch = false;
                invalidate();
                break;
         default:
             break;
        }
        this.mDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        return true;
    }

    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                //如果是左右滑动
                if (Math.abs(deltaX) < Math.abs(deltaY)&& Math.abs(deltaY)>20) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                getParent().requestDisallowInterceptTouchEvent(false);

                break;
            }
            default:
                break;
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 获取位移的最小值
     *
     * @return
     */
    public abstract float getMinScrollX();

    /**
     * 获取位移的最大值
     *
     * @return
     */
    public abstract float getMaxScrollX();

    /**
     * 滑到了最左边
     */
    public abstract void onLeftSide();

    /**
     * 滑到了最右边
     */
    abstract public void onRightSide();

    public int getTextHeight(String s, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(s,0,s.length(), rect);
        return rect.height();
    }
    public interface DataListener{
        void setdata(CandleData param);
        void cancle();

    }
    public  DataListener dataListener;

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //Logging.e("ScrollScaleView","onDetachedFromWindow");
        mAnimator.cancel();

    }
}
