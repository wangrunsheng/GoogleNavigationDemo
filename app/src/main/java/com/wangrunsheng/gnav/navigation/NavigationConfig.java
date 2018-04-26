package com.wangrunsheng.gnav.navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by russell on 2018/4/25.
 */

public class NavigationConfig {

    public final static NavigationModel.NavigationItemEnum[] ITEMS = new NavigationModel.NavigationItemEnum[] {
            NavigationModel.NavigationItemEnum.HOME,
            NavigationModel.NavigationItemEnum.SCHEDULE,
            NavigationModel.NavigationItemEnum.FEED,
            NavigationModel.NavigationItemEnum.MAP,
            NavigationModel.NavigationItemEnum.INFO,
    };

    // 该方法把BuildConfig中无效化的导航页面去掉了
    public static NavigationModel.NavigationItemEnum[] filterOutItemsDisabledInBuildConfig(NavigationModel.NavigationItemEnum[] items) {
        List<NavigationModel.NavigationItemEnum> enabledItems = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            boolean includeItem = true;
            switch (items[i]) {

            }

            if (includeItem) {
                enabledItems.add(items[i]);
            }
        }
        return enabledItems.toArray(new NavigationModel.NavigationItemEnum[enabledItems.size()]);
    }
}
