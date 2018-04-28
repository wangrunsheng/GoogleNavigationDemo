package com.wangrunsheng.gnav.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.wangrunsheng.gnav.R;

import java.lang.reflect.Field;

/**
 * Created by russell on 2018/4/25.
 */

public class BadgedBottomNavigationView extends BottomNavigationView {

    private static final int NO_BADGE_POSITION = -1;

    private int badgePosition = NO_BADGE_POSITION;
    private Paint badgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius;
    private @Nullable OnNavigationItemSelectedListener listener;

    private ViewTreeObserver.OnDrawListener drawListener = new ViewTreeObserver.OnDrawListener() {
        @Override
        public void onDraw() {
            if (badgePosition > NO_BADGE_POSITION) {
                postInvalidateOnAnimation();
            }
        }
    };

    public BadgedBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgedBottomNavigationView);
        badgePaint.setColor(a.getColor(R.styleable.BadgedBottomNavigationView_badgeColor, Color.TRANSPARENT));
        radius = a.getDimension(R.styleable.BadgedBottomNavigationView_badgeRadius, 0f);
        a.recycle();

        super.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (badgePosition > NO_BADGE_POSITION) {
                    getViewTreeObserver().addOnDrawListener(drawListener);
                }
                if (listener != null) {
                    return listener.onNavigationItemSelected(item);
                }
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (badgePosition > NO_BADGE_POSITION) {
            ViewGroup menuView = (ViewGroup) getChildAt(0);
            if (menuView == null) {
                return;
            }
            ViewGroup menuItem = (ViewGroup) menuView.getChildAt(badgePosition);
            if (menuItem == null) {
                return;
            }
            View icon = menuItem.getChildAt(0);
            if (icon == null) {
                return;
            }

            float cx = menuView.getLeft() + menuItem.getRight() - icon.getLeft();
            canvas.drawCircle(cx, icon.getTop(), radius, badgePaint);
        }
    }

    @Override
    public void setOnNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        getViewTreeObserver().removeOnDrawListener(drawListener);
        super.onDetachedFromWindow();
    }

    @SuppressLint("RestrictedApi")
    public void disableShiftMode() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) this.getChildAt(0);

        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
