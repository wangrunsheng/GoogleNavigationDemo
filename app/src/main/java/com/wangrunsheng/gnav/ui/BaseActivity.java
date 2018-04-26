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

    public static void navigateUpOrBack(Activity currentActivity, Class<? extends Activity> syntheticParentActivity) {

        Intent intent = NavUtils.getParentActivityIntent(currentActivity);

        if (intent == null && syntheticParentActivity != null) {
            try {
                intent = NavUtils.getParentActivityIntent(currentActivity, syntheticParentActivity);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (intent == null) {
            currentActivity.onBackPressed();
        } else {
            if (NavUtils.shouldUpRecreateTask(currentActivity, intent)) {
                TaskStackBuilder builder = TaskStackBuilder.create(currentActivity);
                builder.addNextIntentWithParentStack(intent);
                builder.startActivities();
            } else {
                NavUtils.navigateUpTo(currentActivity, intent);
            }
        }
    }

    // 仅在 SimpleSinglePaneActivity 中调用了
    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("_uri", data);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }

        return arguments;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecentTasksStyler.styleRecentTasksEntry(this);

        // TODO: 2018/4/26 这里对欢迎页面是否需要显示做了一个判断

        // TODO: 2018/4/26 这里对账号同步做了处理

        // TODO: 2018/4/26 这里对用户偏好设置做了监听

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
//        getToolbar();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        final BadgedBottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            mAppNavigationView = new AppNavigationViewAsBottomNavImpl(bottomNav);
            mAppNavigationView.activityReady(this, getSelfNavDrawerItem());

            // TODO: 2018/4/26 updateFeedBadge();
        }

        // TODO: 2018/4/26 trySetupSwipeRefresh(); 手欠，多写了
    }

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
                mToolbar.setNavigationContentDescription(R.string.navdrawer_description_a11y);
                mToolbarTitle = mToolbarTitle.findViewById(R.id.toolbar_title);
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

    protected void setToolbarAsUp(View.OnClickListener clickListener) {
        getToolbar();
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_up);
            mToolbar.setNavigationContentDescription(R.string.close_and_go_back);
            mToolbar.setOnClickListener(clickListener);
        }
    }

    @Override
    protected void onStart() {
        // TODO: 2018/4/26  updateFeedBadge();
        super.onStart();
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

    // 原来是只针对特定页面导航按钮的角标更新
    protected void showFeedBadge() {
        if (mAppNavigationView != null) {
            //mAppNavigationView.showItemBadge(NavigationModel.NavigationItemEnum.FEED);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // 和自定义字体有关
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
