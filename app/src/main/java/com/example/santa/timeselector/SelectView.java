package com.example.santa.timeselector;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by santa on 16/7/20.
 */
public class SelectView extends ScrollView {
    private String DEBUG = "SelectView";
    private LayoutParams mLayoutParams;
    private int tagPadding = 20;
    private LinkedList<String> mData;

    private int totalHeight = 0;
    private int childHeight = 0;
    private int childCount = 0;
    private int tagTextSize = 20;
    private int tagTextColor = Color.BLACK;
    private int dividerPadding = 20;
    private LinearLayout mLinearLayout;


    private VelocityTracker mVelocityTracker;
    private int mActivePointerId = -1;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    public SelectView(Context context) {
        this(context, null);
    }

    public SelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);


        mLinearLayout = new LinearLayout(context);
        LayoutParams layoutParams= new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mLinearLayout.setBackgroundColor(Color.RED);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setLayoutParams(layoutParams);
        addView(mLinearLayout);
        setData(null);
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



    public void setData(ArrayList<String> list) {

        mData = new LinkedList<>();

//        final int childCount = calculateChidCount();

        for (int i = 0 ; i<50; i++) {
            addTextTab(0, i+"月");
        }
    }

    private void addTextTab(final int position, String title) {

        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        addTab(position, tab);
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tab.setPadding(0, tagPadding, 0, tagPadding);
        mLinearLayout.addView(tab, mLayoutParams);
    }



}
