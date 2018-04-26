package com.wangrunsheng.gnav.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.wangrunsheng.gnav.R;
import com.wangrunsheng.gnav.archframework.PresenterImpl;
import com.wangrunsheng.gnav.archframework.UpdatableView;

/**
 * Created by russell on 2018/4/25.
 */

public abstract class AppNavigationViewAbstractImpl implements UpdatableView<NavigationModel, NavigationModel.NavigationQueryEnum, NavigationModel.NavigationUserActionEnum>, AppNavigationView {

    private static final long BOTTOM_NAV_ANIM_GRACE = 115L;
    private UserActionListener<NavigationModel.NavigationUserActionEnum> mUserActionLister;

    protected Activity mActivity;

    private final Handler mHandler = new Handler();

    protected NavigationModel.NavigationItemEnum mSelfItem;

    @Override
    public void displayData(NavigationModel model, NavigationModel.NavigationQueryEnum query) {
        switch (query) {
            case LOAD_ITEMS:
                displayNavigationItems(model.getmItems());
                break;
        }
    }

    @Override
    public void displayErrorMessage(NavigationModel.NavigationQueryEnum query) {
        switch (query) {
            case LOAD_ITEMS:

                break;
        }
    }

    @Override
    public void activityReady(Activity activity, NavigationModel.NavigationItemEnum self) {
        mActivity = activity;
        mSelfItem = self;

        setUpView();

        NavigationModel model = new NavigationModel();
        PresenterImpl<NavigationModel, NavigationModel.NavigationQueryEnum, NavigationModel.NavigationUserActionEnum> presenter = new PresenterImpl<>(model, this, NavigationModel.NavigationUserActionEnum.values(), NavigationModel.NavigationQueryEnum.values());
        presenter.loadInitialQueries();
        addListener(presenter);
    }

    @Override
    public void updateNavigationItems() {
        mUserActionLister.onUserAction(NavigationModel.NavigationUserActionEnum.RELOAD_ITEMS, null);
    }

    @Override
    public abstract void displayNavigationItems(NavigationModel.NavigationItemEnum[] items);

    @Override
    public abstract void setUpView();

    @Override
    public abstract void showNavigation();

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

    @Override
    public void displayUserActionResult(NavigationModel model, NavigationModel.NavigationUserActionEnum userAction, boolean success) {
        switch (userAction) {
            case RELOAD_ITEMS:
                displayNavigationItems(model.getmItems());
                break;
        }
    }

    @Override
    public Uri getDataUri(NavigationModel.NavigationQueryEnum query) {
        return null;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public void addListener(UserActionListener<NavigationModel.NavigationUserActionEnum> listener) {
        mUserActionLister = listener;
    }
}
