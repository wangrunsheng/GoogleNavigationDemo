package com.wangrunsheng.gnav.navigation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

import com.wangrunsheng.gnav.ui.widget.BadgedBottomNavigationView;

/**
 * Created by russell on 2018/4/26.
 */

public class AppNavigationViewAsBottomNavImpl extends AppNavigationViewAbstractImpl implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final BadgedBottomNavigationView mNavigationView;

    public AppNavigationViewAsBottomNavImpl(final BadgedBottomNavigationView navigationView) {
        mNavigationView = navigationView;
    }

    @Override
    public void displayNavigationItems(NavigationModel.NavigationItemEnum[] items) {
        final Menu menu = mNavigationView.getMenu();
        for (NavigationModel.NavigationItemEnum item : items) {
            final MenuItem menuItem = menu.findItem(item.getId());
            if (menuItem != null) {
                menuItem.setVisible(true);
                menuItem.setIcon(item.getIconResource());
                menuItem.setTitle(item.getTitleResource());
                if (item == mSelfItem) {
                    menuItem.setChecked(true);
                }
            } else {
                // TODO: 2018/4/26  打印日志
                // "Menu Item for navigation item with title " +(item.getTitleResource() != 0? mActivity.getResources().getString(item.getTitleResource()): "") + "not found"
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final NavigationModel.NavigationItemEnum navItem = NavigationModel.NavigationItemEnum.getById(item.getItemId());
        if (navItem != null && navItem != mSelfItem) {
            itemSelected(navItem);
            // TODO: 2018/4/26 这个Google做了一个分析，发送了一个事件
            return true;
        }
        return false;
    }

    @Override
    public void setUpView() {
        mNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void showNavigation() {

    }

    @Override
    public void showItemBadge(NavigationModel.NavigationItemEnum item) {
        mNavigationView.showBadge(item.ordinal());
    }

    @Override
    public void clearItemBadge(NavigationModel.NavigationItemEnum item) {
        mNavigationView.clearBadge();
    }
}
