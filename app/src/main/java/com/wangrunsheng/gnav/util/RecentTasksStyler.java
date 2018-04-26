package com.wangrunsheng.gnav.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;

import com.wangrunsheng.gnav.R;

/**
 * Created by russell on 2018/4/26.
 */

public class RecentTasksStyler {

    private static Bitmap sIcon = null;

    private RecentTasksStyler() { }

    public static void styleRecentTasksEntry(Activity activity) {
        final String label = activity.getString(activity.getApplicationInfo().labelRes);
        final int colorPrimary = UIUtils.getThemeColor(activity, R.attr.colorPrimary, R.color.theme_primary);
        if (sIcon == null) {
            sIcon = UIUtils.drawableToBitmap(activity, R.drawable.ic_recents_logo);
        }
        activity.setTaskDescription(new ActivityManager.TaskDescription(label, sIcon, colorPrimary));
    }
}
