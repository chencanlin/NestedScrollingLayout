package com.ccl.perfectisshit.nestedscrolling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.ccl.perfectisshit.nestedscrolling.fragment.TabFragment;
import com.ccl.perfectisshit.nestedscrolling.widget.ViewPagerIndicator;

/**
 * Created by ccl on 2017/8/30.
 */

public class NestedScrollingActivity extends FragmentActivity {


    public static final int POSITION_QA_FRAGMENT = 0;
    public static final int POSITION_CIRCLE_FRAGMENT = 1;
    public static final int POSITION_INFORMATION_FRAGMENT = 2;

    private SparseArrayCompat<Fragment> mFragments = new SparseArrayCompat<>();

    public int[] tabTitleId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scrolling);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        tabTitleId = new int[]{R.string.tab_fragment_qa, R.string.tab_fragment_circle, R.string.tab_fragment_information};
        mFragments.put(0, TabFragment.newInstance(getString(tabTitleId[POSITION_QA_FRAGMENT])));
        mFragments.put(1, TabFragment.newInstance(getString(tabTitleId[POSITION_CIRCLE_FRAGMENT])));
        mFragments.put(2, TabFragment.newInstance(getString(tabTitleId[POSITION_INFORMATION_FRAGMENT])));
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    private void initView() {
        int imgWidth = 720;
        int imgHeight = 400;
        int screenWidth = getScreenWidth();
        int topViewHeight = screenWidth * imgHeight / imgWidth;
        View topView = findViewById(R.id.id_nested_scrolling_top_view);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth, topViewHeight);
        topView.setLayoutParams(layoutParams);

        ViewPagerIndicator viewPagerIndicator = findViewById(R.id.vpi);
        ViewPager viewPager = findViewById(R.id.vp);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPagerIndicator.setViewPager(viewPager);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<Fragment> mFragments;

        MyViewPagerAdapter(FragmentManager fm, SparseArrayCompat<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (mFragments != null) {
                fragment = mFragments.get(position);
            } else {
                mFragments = new SparseArrayCompat<>();
            }
            if (fragment == null) {
                switch (position) {
                    case 0:
                        fragment = TabFragment.newInstance(getString(tabTitleId[POSITION_QA_FRAGMENT]));
                        break;
                    case 1:
                        fragment = TabFragment.newInstance(getString(tabTitleId[POSITION_CIRCLE_FRAGMENT]));
                        break;
                    case 2:
                        fragment = TabFragment.newInstance(getString(tabTitleId[POSITION_INFORMATION_FRAGMENT]));
                        break;
                }
            }
            mFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(tabTitleId[position]);
        }
    }
}
