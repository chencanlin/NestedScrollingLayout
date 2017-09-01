package com.ccl.perfectisshit.nestedscrolling.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ccl.perfectisshit.nestedscrolling.R;


/**
 * Created by ccl on 2017/8/30.
 */

public class NestedScrollingLinearLayout extends LinearLayout implements NestedScrollingParent {

    private View mTopView;
    private int mTopViewMeasureHeight;
    private ValueAnimator mValueAnimator;


    public NestedScrollingLinearLayout(Context context) {
        this(context, null);
    }

    public NestedScrollingLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.id_nested_scrolling_top_view);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewMeasureHeight = mTopView.getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + getChildAt(0).getMeasuredHeight() + getChildAt(1).getMeasuredHeight());
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
    }

    @Override
    public void onStopNestedScroll(View child) {
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        int scrollY = getScrollY();
        int consumedY = dy;
        if (dy > 0) {
            if (scrollY + consumedY >= mTopViewMeasureHeight) {
                consumedY = mTopViewMeasureHeight - scrollY;
            }
        } else {
            if (scrollY + consumedY <= 0) {
                consumedY = -scrollY;
            }
        }

        scrollBy(0, consumedY);
        consumed[1] = consumedY;

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (velocityY > 0) {
            if (velocityY >= 200 && getScrollY() >= mTopViewMeasureHeight / 2 && getScrollY() + velocityY >= 2 * mTopViewMeasureHeight / 3) {
                animateScroll(velocityY);
            }
        } else {
            if (velocityY <= -200 && getScrollY() <= mTopViewMeasureHeight / 2 && getScrollY() + velocityY <= mTopViewMeasureHeight / 3) {
                animateScroll(velocityY);
            }
        }
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    private void animateScroll(float velocityY) {
        if (velocityY > 0) {
            mValueAnimator = ValueAnimator.ofFloat(getScrollY(), mTopViewMeasureHeight);
        } else {
            mValueAnimator = ValueAnimator.ofFloat(getScrollY(), 0);
        }
        mValueAnimator.setDuration(computeDuration(velocityY));
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                scrollTo(0, Math.round(animatedValue));
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                recyclerAnimate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mValueAnimator.start();
    }

    private void recyclerAnimate() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.removeAllListeners();
            mValueAnimator = null;
        }
    }

    private int computeDuration(float velocityY) {
        float distance;
        if (velocityY > 0) {
            distance = mTopViewMeasureHeight - getScrollY();
        } else {
            distance = getScrollY();
        }
        return Math.round(500 * (distance * 3 / mTopViewMeasureHeight));
    }
}
