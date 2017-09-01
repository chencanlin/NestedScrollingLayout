package com.ccl.perfectisshit.nestedscrolling.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccl.perfectisshit.nestedscrolling.R;

/**
 * Created by ccl on 2017/8/30.
 */

public class ViewPagerIndicator extends LinearLayout {

    private int mScreenWidth;
    private ViewPager mViewPager;
    private static final int[] colors = {0xFFFF7F27, 0xFF3E07D2, 0xFFB61C51};
    private SparseArrayCompat<LinearLayout> tabItem = new SparseArrayCompat<>();

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        mScreenWidth = getScreenWidth();
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (mViewPager != null) {
            int count = mViewPager.getAdapter().getCount();
            if (count != 0) {
                generateTitleView(count);
            }
        }
        mViewPager.addOnPageChangeListener(new OnMyPageChangeListener());
    }

    private class OnMyPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < tabItem.size(); i++) {
                LinearLayout linearLayout = tabItem.get(i);
                if(linearLayout != null && linearLayout.getChildCount() == 2){
                    View secondChild = linearLayout.getChildAt(1);
                    secondChild.setVisibility(i == position ? View.VISIBLE : View.GONE);
                    View firstChild = linearLayout.getChildAt(0);
                    firstChild.setSelected(i == position);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void generateTitleView(int count) {
        int tabWidth = mScreenWidth/count;
        for (int i = 0; i < count; i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            LayoutParams layoutParams = new LayoutParams(tabWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.setId(i);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
//            int argb = Color.argb(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
//            linearLayout.setBackgroundColor(argb);
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(view.getId());
                }
            });
            TextView textView = new TextView(getContext());
            textView.setText(mViewPager.getAdapter().getPageTitle(i));
            textView.setTextSize(13);
            textView.setGravity(Gravity.CENTER);
            ColorStateList colorStateList = getContext().getResources().getColorStateList(R.color.selector_tab);
            textView.setTextColor(colorStateList);
            textView.setSelected(i==0);
            LayoutParams textViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (dpToPixel(37)));
            textView.setLayoutParams(textViewParams);
            TextView bottomLine = new TextView(getContext());
            LayoutParams bottomLineParams = new LayoutParams((int) (mScreenWidth / count - dpToPixel(20)), (int) dpToPixel(2));
            bottomLineParams.gravity = Gravity.CENTER_HORIZONTAL;
            bottomLine.setBackgroundColor(0xffFF7F27);
            bottomLine.setLayoutParams(bottomLineParams);
            bottomLine.setVisibility(i == 0 ? View.VISIBLE:View.GONE);
            linearLayout.addView(textView);
            linearLayout.addView(bottomLine);
            addView(linearLayout);
            tabItem.put(i, linearLayout);
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSpecMine = MeasureSpec.makeMeasureSpec(mScreenWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpecMine = MeasureSpec.makeMeasureSpec((int) dpToPixel(39), MeasureSpec.EXACTLY);
        measureChildren(widthMeasureSpecMine, heightMeasureSpecMine);
        setMeasuredDimension(mScreenWidth, (int) dpToPixel(39));
    }

    public int getScreenWidth() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public float dpToPixel(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
