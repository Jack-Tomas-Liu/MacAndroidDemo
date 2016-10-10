package com.sj.simplelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by liuxinxian on 16/10/8.
 */

public class SlideCustomListView extends ListView {
    private int mLastPostion=-1;
    private int mCurrentPostion = -1;
    private View mSlideView;
    private float mDownX;
    private float mDownY;
    private float mMoveX;
    private float mMoveY;
    private float mDx;

    //view
    private View mLastView,mCurrentView;

    //Scroller
    Scroller mScroller;

    public SlideCustomListView(Context context) {
        super(context);
        initScroller(context);
    }

    public SlideCustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroller(context);
    }

    public SlideCustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroller(context);
    }

    private void initScroller(Context context){
        mScroller = new Scroller(context,new AccelerateInterpolator());//加速插值器
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                mCurrentPostion = pointToPosition((int)mDownX,(int)mDownY);
                //把上一个item扯回去
                if(mCurrentPostion!=-1){
                    if(mCurrentPostion!=mLastPostion){
                        mLastView = getChildAt(mLastPostion);
                        if(mLastView!=null){
                            mLastView.scrollTo(0,0);
                        }
                    }
                }
                mLastPostion = mCurrentPostion;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = ev.getX();
                mMoveY = ev.getY();
                mCurrentPostion = pointToPosition((int)mMoveX,(int)mMoveY);
                mDx = mDownX - mMoveX;
                if(mCurrentPostion==mLastPostion){
//                    if(mDx>0){//左滑
//                        getChildAt(mCurrentPostion).scrollTo(100,0);
//                    }else {//右滑
//                        getChildAt(mCurrentPostion).scrollTo(0,0);
//                    }
                    mCurrentView = getChildAt(mCurrentPostion);
                }
//                if(!mScroller.computeScrollOffset()){//Call this when you want to know the new location.  If it returns true, the animation is not yet finished.
//                    mScroller.startScroll(0,0,150,0,500);
//                    System.out.println("滑动结束，执行");
//                }else {
//                    System.out.println("滑动动画没有结束");
//                }
                mScroller.startScroll(0,0,150,0);
                invalidate();
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * override view computeScroll method,invalidate method will caused this method
     */

    @Override
    public void computeScroll() {
        super.computeScroll();
        System.out.println("computeScroll()");
        if(mScroller.computeScrollOffset()){
            if(mCurrentView==null){
                return;
            }
            mCurrentView.scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            //mScroller.startScroll(0,0,-150,0,500);
            postInvalidate();
        }
    }
}

/***
 * 1 首先要拉出来
 * 2 如果一个拉出来了,其余的要关掉。
 * 3 缓慢的拉出来
 **/