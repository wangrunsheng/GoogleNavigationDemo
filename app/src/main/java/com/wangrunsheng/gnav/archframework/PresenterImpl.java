package com.wangrunsheng.gnav.archframework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by russell on 2018/4/25.
 */

public class PresenterImpl<M extends Model<Q, UA>, Q extends QueryEnum, UA extends UserActionEnum> implements Presenter, UpdatableView.UserActionListener<UA> {


    private UpdatableView<M, Q, UA>[] mUpdatableViews;

    private M mModel;

    private Q[] mInitialQueriesToLoad;

    private UA[] mValidUserActions;

    public PresenterImpl(M model, UpdatableView<M, Q, UA> view, UA[] validUserActions, Q[] initialQueries) {
        this(model, new UpdatableView[]{view}, validUserActions, initialQueries);
    }

    public PresenterImpl(M model, UpdatableView<M, Q, UA>[] views, UA[] validUserActions, Q[] initialQueries) {
        mModel = model;
        if (views != null) {
            mUpdatableViews = views;
            for (UpdatableView<M, Q, UA> view : mUpdatableViews) {
                view.addListener(this);
            }
        } else {
            // TODO: 2018/4/25 这里是预留打空日志的地方
            // "Creating a PresenterImpl with null view"
        }
        mValidUserActions = validUserActions;
        mInitialQueriesToLoad = initialQueries;
    }

    @Override
    public void loadInitialQueries() {
        if (mInitialQueriesToLoad != null && mInitialQueriesToLoad.length > 0) {
            for (Q query : mInitialQueriesToLoad) {
                mModel.requestData(query, new Model.DataQueryCallback<Q>() {
                    @Override
                    public void onModelUpdated(Model<Q, ?> model, Q query) {
                        if (mUpdatableViews != null) {
                            for (UpdatableView<M, Q, UA> view : mUpdatableViews) {
                                view.displayData(mModel, query);
                            }
                        } else {
                            // TODO: 2018/4/25 又是打日志的地方
                            // "loadInitialQueries(), cannot notify a null view!"
                        }
                    }

                    @Override
                    public void onError(Q query) {
                        if (mUpdatableViews != null) {
                            for (UpdatableView<M, Q, UA> view : mUpdatableViews) {
                                view.displayErrorMessage(query);
                            }
                        } else {
                            // TODO: 2018/4/25
                            // "loadInitialQueries(), cannot notify a null view!"
                        }
                    }
                });
            }
        } else {
            if (mUpdatableViews != null) {
                for (UpdatableView<M, Q, UA> view : mUpdatableViews) {
                    view.displayData(mModel, null);
                }
            } else {
                // TODO: 2018/4/25
                // "loadInitialQueries(), cannot notify a null view!"
            }
        }
    }

    @Override
    public void onUserAction(UA action, @Nullable Bundle args) {
        boolean isValid = false;
        if (mValidUserActions != null && action != null) {
            for (UA validAction : mValidUserActions) {
                if (validAction.getId() == action.getId()) {
                    isValid = true;
                    break;
                }
            }
        }
        if (isValid) {
            mModel.deliverUserAction(action, args, new Model.UserActionCallback<UA>() {
                @Override
                public void onModelUpdated(Model<?, UA> model, UA userAction) {
                    if (mUpdatableViews != null) {
                        for (UpdatableView<M, Q, UA> view : mUpdatableViews) {
                            view.displayUserActionResult(mModel, userAction, true);
                        }
                    } else {
                        // TODO: 2018/4/25
                        // "onUserAction(), cannot notify a null view!"
                    }
                }

                @Override
                public void onError(UA userAction) {
                    if (mUpdatableViews != null) {
                        for (UpdatableView<M, Q, UA> view : mUpdatableViews) {
                            view.displayUserActionResult(null, userAction, false);
                        }
                    } else {
                        // TODO: 2018/4/25
                        // "onUserAction(), cannot notify a null view!"
                    }
                }
            });
        } else {
            if (mUpdatableViews != null) {
                for (UpdatableView<M, Q, UA> view : mUpdatableViews) {
                    view.displayUserActionResult(null, action, false);
                }

                // User action not understood.
                throw new RuntimeException(
                        "Invalid user action " + (action != null ? action.getId() : null)
                                + ". Have you called setValidUserActions on your presenter, with "
                                + "all the UserActionEnum you want to support?");
            } else {
                // TODO: 2018/4/25
                // "onUserAction(), cannot notify a null view!"
            }
        }
    }

    protected M getModel() {
        return mModel;
    }
}
