package com.wangrunsheng.gnav.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wangrunsheng.gnav.R;
import com.wangrunsheng.gnav.feed.FeedActivity;
import com.wangrunsheng.gnav.home.MainActivity;
import com.wangrunsheng.gnav.info.InfoActivity;
import com.wangrunsheng.gnav.map.MapActivity;
import com.wangrunsheng.gnav.schedule.ScheduleActivity;

/**
 * Created by russell on 2018/4/25.
 */

public class NavigationModel  {

    public enum NavigationItemEnum {

        HOME(R.id.home_nav_item, R.string.navdrawer_item_home, R.drawable.ic_nav_home, MainActivity.class, true),
        SCHEDULE(R.id.schedule_nav_item, R.string.navdrawer_item_schedule, R.drawable.ic_nav_schedule, ScheduleActivity.class, true),
        FEED(R.id.feed_nav_item, R.string.navdrawer_item_feed, R.drawable.ic_nav_feed, FeedActivity.class, true),
        MAP(R.id.map_nav_item, R.string.navdrawer_item_map, R.drawable.ic_nav_map, MapActivity.class, true),
        INFO(R.id.info_nav_item, R.string.navdrawer_item_info, R.drawable.ic_nav_info, InfoActivity.class, true),
        INVALID(12, 0, 0, null);

        private int id;

        private int titleResource;

        private int iconResource;

        private Class classToLaunch;

        private boolean finishCurrentActivity;

        NavigationItemEnum(int id, int titleResource, int iconResource, Class classToLaunch) {
            this(id, titleResource, iconResource, classToLaunch, false);
        }

        NavigationItemEnum(int id, int titleResource, int iconResource, Class classToLaunch, boolean finishCurrentActivity) {
            this.id = id;
            this.titleResource = titleResource;
            this.iconResource = iconResource;
            this.classToLaunch = classToLaunch;
            this.finishCurrentActivity = finishCurrentActivity;
        }

        public static NavigationItemEnum getById(int id) {
            for (NavigationItemEnum value : NavigationItemEnum.values()) {
                if (value.getId() == id) {
                    return value;
                }
            }
            return INVALID;
        }

        public int getId() {
            return id;
        }

        public int getTitleResource() {
            return titleResource;
        }

        public int getIconResource() {
            return iconResource;
        }

        public Class getClassToLaunch() {
            return classToLaunch;
        }

        public boolean finishCurrentActivity() {
            return finishCurrentActivity;
        }
    }
}
