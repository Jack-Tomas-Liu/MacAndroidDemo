package com.sj.simplelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by liuxinxian on 16/10/8.
 */

public class SlideListView extends ListView {
    private Scroller scroller;

    private int downX;
    private int downY;
    private int moveX;
    private int moveY;
    private int downPosition;
    private int upPosition;
    private boolean deleOpen = false;

    private View slideListItem;
    private int lastPos = -1;

    /**
     * 构造器
     */
    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideListView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                downX = (int) ev.getX();
                downY = (int) ev.getY();
                // 获取按下的条目索引
                downPosition = pointToPosition(downX, downY);
                slideListItem = getChildAt(downPosition);
                if (slideListItem != null) {
                    slideListItem.scrollTo(0, 0);
                }

                // 如果按下的不是当前的item
                if (lastPos != -1) {
                    if (lastPos != downPosition) {
                        View childAt = getChildAt(lastPos);
                        childAt.scrollTo(0, 0);
                    }
                }
                lastPos = downPosition;
                break;

            case MotionEvent.ACTION_MOVE:

                moveX = (int) ev.getX();
                moveY = (int) ev.getY();

                int upPosition = pointToPosition(moveX, moveY);

                if (downPosition == upPosition) {
                    // 是同一个条目
                    if ((downX - moveX) > 50) {
                        if (slideListItem != null) {
                            slideListItem.scrollTo(100, 0);
                        }

                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


}
