package com.wangrunsheng.gnav.schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangrunsheng.gnav.R;
import com.wangrunsheng.gnav.navigation.NavigationModel;
import com.wangrunsheng.gnav.ui.BaseActivity;

public class ScheduleActivity extends BaseActivity {

    @Override
    protected NavigationModel.NavigationItemEnum getSelfNavDrawerItem() {
        return NavigationModel.NavigationItemEnum.SCHEDULE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
    }
}
