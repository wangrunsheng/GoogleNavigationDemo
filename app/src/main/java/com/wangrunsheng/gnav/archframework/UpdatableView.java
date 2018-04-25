package com.wangrunsheng.gnav.archframework;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by russell on 2018/4/25.
 */

public interface UpdatableView<M extends Model<Q, UA>, Q extends QueryEnum, UA extends UserActionEnum> {

    void displayData(M model, Q query);

    void displayErrorMessage(Q query);

    void displayUserActionResult(M model, UA userAction, boolean success);

    Uri getDataUri(Q query);

    Context getContext();

    void addListener();

    interface UserActionListener<UA extends UserActionEnum> {

        void onUserAction(UA action, @Nullable Bundle args);
    }

}
