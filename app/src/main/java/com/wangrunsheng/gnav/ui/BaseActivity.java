package com.wangrunsheng.gnav.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wangrunsheng.gnav.R;
import com.wangrunsheng.gnav.navigation.AppNavigationView;
import com.wangrunsheng.gnav.navigation.AppNavigationViewAsBottomNavImpl;
import com.wangrunsheng.gnav.navigation.NavigationModel;
import com.wangrunsheng.gnav.ui.widget.BadgedBottomNavigationView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by russell on 2018/4/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    // 1. 在 Activity 中唯一要做的就是创建导航
    private AppNavigationView mAppNavigationView;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    // 2. 每个子类重写此方法，将需要导航的 Activity 加入到豪华套餐
    protected NavigationModel.NavigationItemEnum getSelfNavDrawerItem() {
        return NavigationModel.NavigationItemEnum.INVALID;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getToolbar();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final BadgedBottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.disableShiftMode();
        // 3. 对导航进行了具体的实现
        if (bottomNav != null) {
            mAppNavigationView = new AppNavigationViewAsBottomNavImpl(bottomNav);
            mAppNavigationView.activityReady(this, getSelfNavDrawerItem());
        }

    }

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
                mToolbar.setNavigationContentDescription(R.string.navdrawer_description_a11y);
                mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
                if (mToolbarTitle != null) {
                    int titleId = getNavigationTitleId();
                    if (titleId != 0) {
                        mToolbarTitle.setText(titleId);
                    }
                }

                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        return mToolbar;
    }

    // 如果需要修改导航标签可以重写此方法
    protected int getNavigationTitleId() {
        return 0;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // 和自定义字体有关
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
