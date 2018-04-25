package com.wangrunsheng.gnav.archframework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by russell on 2018/4/25.
 */

public interface Model<Q extends QueryEnum, UA extends UserActionEnum> {

    Q[] getQueries();

    UA[] getUserActions();

    void deliverUserAction(UA action, @Nullable Bundle args, UserActionCallback<UA> callback);

    void requestData(Q query, DataQueryCallback<Q> callback);

    void cleanUp();

    interface UserActionCallback<UA extends UserActionEnum> {

        void onModelUpdated(Model<?, UA> model, UA userAction);

        void onError(UA userAction);
    }

    interface DataQueryCallback<Q extends QueryEnum> {

        void onModelUpdated(Model<Q, ?> model, Q query);

        void onError(Q query);
    }
}
