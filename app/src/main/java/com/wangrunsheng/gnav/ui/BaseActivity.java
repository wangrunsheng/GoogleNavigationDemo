package com.wangrunsheng.gnav.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
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
import com.wangrunsheng.gnav.util.RecentTasksStyler;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by russell on 2018/4/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private AppNavigationView mAppNavigationView;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecentTasksStyler.styleRecentTasksEntry(this);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

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

    protected int getNavigationTitleId() {
        return 0;
    }

    protected void setFullscreenLayout() {
        View decor = getWindow().getDecorView();
        int flags = decor.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decor.setSystemUiVisibility(flags);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        // 和自定义字体有关
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
