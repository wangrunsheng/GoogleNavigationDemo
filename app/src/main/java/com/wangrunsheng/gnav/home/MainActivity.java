package com.wangrunsheng.gnav.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangrunsheng.gnav.R;
import com.wangrunsheng.gnav.navigation.NavigationModel;
import com.wangrunsheng.gnav.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected NavigationModel.NavigationItemEnum getSelfNavDrawerItem() {
        return NavigationModel.NavigationItemEnum.HOME;
    }

    @Override
    protected int getNavigationTitleId() {
        return R.string.home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
