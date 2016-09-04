package com.example.santa.timeselector;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by santa on 16/7/19.
 */
public class JustListView extends ListView {
    private String DEBUG = "JustListView";
    private VelocityTracker mVelocityTracker;
    private int mActivePointerId = -1;
    private int mMinimumVelocity;
    private int mMaximumVelocity;


    public JustListView(Context context) {
        this(context, null);
    }

    public JustListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JustListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public JustListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }



    private void obtainVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("JustListView", "action  = "+ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                Log.d(DEBUG ,"releaseVelocityTracker");
                break;
            case MotionEvent.ACTION_DOWN:
//                mScroller.abortAnimation();
//                mLastPosition = ev.getX();
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                Log.d(DEBUG ,"initOrResetVelocityTracker");

                break;
            case MotionEvent.ACTION_MOVE:
                obtainVelocityTracker(ev);
                Log.d(DEBUG ,"obtainVelocityTracker");

                break;

        }
        return super.onInterceptTouchEvent(ev);
    }





    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("JustListView", "action onTouchEvent = "+ev.getAction());

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, mActivePointerId);

                Log.d(DEBUG, "2---initialVelocity is = " + initialVelocity + "mMinimumVelocity = "+mMinimumVelocity);
                mActivePointerId = -1;
                releaseVelocityTracker();
                if ((Math.abs(initialVelocity) > 0)) {
                    Log.d(DEBUG, "big---initialVelocity is big ");
                    fling(initialVelocity);
                    return true;
                }

//                releaseVelocityTracker();

                break;
            case MotionEvent.ACTION_DOWN:
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                Log.d(DEBUG ,"initOrResetVelocityTracker");

                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);

                break;
            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (activePointerIndex == -1) {
                    Log.e(DEBUG, "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                    break;
                }
                obtainVelocityTracker(ev);
                Log.d(DEBUG ,"obtainVelocityTracker");
                break;

        }
//        switch ()
        return super.onTouchEvent(ev);
    }



    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        Log.d("JustListView", "velocityY = "+velocityY);
        smoothScrollToPosition(getFirstVisiblePosition() + velocityY/100);
    }



}
