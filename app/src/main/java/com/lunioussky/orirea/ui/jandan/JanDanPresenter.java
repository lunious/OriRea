package com.lunioussky.orirea.ui.jandan;

import android.util.Log;

import com.lunioussky.orirea.bean.FreshNewsBean;
import com.lunioussky.orirea.bean.JdDetailBean;
import com.lunioussky.orirea.net.BaseObserver;
import com.lunioussky.orirea.net.JanDanApi;
import com.lunioussky.orirea.net.RxSchedulers;
import com.lunioussky.orirea.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class JanDanPresenter extends BasePresenter<JanDanContract.View> implements JanDanContract.Presenter {
    private static final String TAG = "JanDanPresenter";
    JanDanApi mJanDanApi;

    @Inject
    public JanDanPresenter(JanDanApi janDanApi) {
        this.mJanDanApi = janDanApi;
    }

    @Override
    public void getData(String type, int page) {
        if (type.equals(JanDanApi.TYPE_FRESH)) {
            getFreshNews(page);
        } else {
            getDetailData(type, page);
        }
    }

    @Override
    public void getFreshNews(final int page) {
        mJanDanApi.getFreshNews(page)
                .compose(RxSchedulers.<FreshNewsBean>applySchedulers())
                .compose(mView.<FreshNewsBean>bindToLife())
                .subscribe(new BaseObserver<FreshNewsBean>() {
                    @Override
                    public void onSucess(FreshNewsBean freshNewsBean) {
                        if (page > 1) {
                            mView.loadMoreFreshNews(freshNewsBean);
                        } else {
                            mView.loadFreshNews(freshNewsBean);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }

    @Override
    public void getDetailData(final String type, final int page) {
        mJanDanApi.getJdDetails(type, page)
                .compose(RxSchedulers.<JdDetailBean>applySchedulers())
                .compose(mView.<JdDetailBean>bindToLife())
                .map(new Function<JdDetailBean, JdDetailBean>() {
                    @Override
                    public JdDetailBean apply(@NonNull JdDetailBean jdDetailBean) throws Exception {
                        for (JdDetailBean.CommentsBean bean : jdDetailBean.getComments()) {
                            if (bean.getPics() != null) {
                                if (bean.getPics().size() > 1) {
                                    bean.itemType = JdDetailBean.CommentsBean.TYPE_MULTIPLE;
                                } else {
                                    bean.itemType = JdDetailBean.CommentsBean.TYPE_SINGLE;
                                }
                            }
                        }
                        return jdDetailBean;
                    }
                })
                .subscribe(new BaseObserver<JdDetailBean>() {
                    @Override
                    public void onSucess(JdDetailBean jdDetailBean) {
                        if (page > 1) {
                            mView.loadMoreDetailData(type, jdDetailBean);
                        } else {
                            mView.loadDetailData(type, jdDetailBean);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        if (page > 1) {
                            mView.loadMoreDetailData(type, null);
                        } else {
                            mView.loadDetailData(type, null);
                        }
                        Log.i(TAG, "onFail: " + e.getMessage());
                    }
                });
    }
}
