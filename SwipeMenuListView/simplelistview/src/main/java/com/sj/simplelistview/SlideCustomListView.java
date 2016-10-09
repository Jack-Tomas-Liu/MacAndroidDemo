package com.sj.simplelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

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

    public SlideCustomListView(Context context) {
        super(context);
    }

    public SlideCustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideCustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                        View view = getChildAt(mLastPostion);
                        if(view!=null){
                            view.scrollTo(0,0);
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
                    if(mDx>0){//左滑
                        getChildAt(mCurrentPostion).scrollTo(100,0);
                    }else {
                        getChildAt(mCurrentPostion).scrollTo(0,0);
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

}

/***
 * 1 首先要拉出来
 * 2 如果一个拉出来了,其余的要关掉。
 **/