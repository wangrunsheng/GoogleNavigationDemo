package com.wangrunsheng.gnav.navigation;

import android.app.Activity;

/**
 * Created by russell on 2018/4/25.
 */

public interface AppNavigationView {

    void activityReady(Activity activity, NavigationModel.NavigationItemEnum self);

    void setUpView();

    void displayNavigationItems(NavigationModel.NavigationItemEnum[] items);

    void itemSelected(NavigationModel.NavigationItemEnum item);

}
