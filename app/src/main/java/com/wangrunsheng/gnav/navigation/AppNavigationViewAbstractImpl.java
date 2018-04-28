package com.wangrunsheng.gnav.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.wangrunsheng.gnav.R;

/**
 * Created by russell on 2018/4/25.
 */

public abstract class AppNavigationViewAbstractImpl implements AppNavigationView {

    private static final long BOTTOM_NAV_ANIM_GRACE = 115L;

    protected Activity mActivity;

    private final Handler mHandler = new Handler();

    protected NavigationModel.NavigationItemEnum mSelfItem;

    @Override
    public void activityReady(Activity activity, NavigationModel.NavigationItemEnum self) {
        mActivity = activity;
        mSelfItem = self;

        setUpView();

        // 这里原本是 Presenter 实现的地方
        displayNavigationItems(NavigationConfig.ITEMS);
    }

    @Override
    public abstract void displayNavigationItems(NavigationModel.NavigationItemEnum[] items);

    @Override
    public abstract void setUpView();

    @Override
    public void itemSelected(final NavigationModel.NavigationItemEnum item) {
        if (item.getClassToLaunch() != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mActivity.startActivity(new Intent(mActivity, item.getClassToLaunch()));
                    if (item.finishCurrentActivity()) {
                        mActivity.finish();
                        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            }, BOTTOM_NAV_ANIM_GRACE);
        }
    }

}
