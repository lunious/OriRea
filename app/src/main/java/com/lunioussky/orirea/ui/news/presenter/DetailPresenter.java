package com.lunioussky.orirea.ui.news.presenter;

import android.util.Log;

import com.lunioussky.orirea.bean.NewsDetail;
import com.lunioussky.orirea.net.BaseObserver;
import com.lunioussky.orirea.net.NewsApi;
import com.lunioussky.orirea.net.NewsUtils;
import com.lunioussky.orirea.net.RxSchedulers;
import com.lunioussky.orirea.ui.base.BasePresenter;
import com.lunioussky.orirea.ui.news.contract.DetailContract;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/8 .
 */
public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {
    private static final String TAG = "DetailPresenter";

    NewsApi mNewsApi;

    @Inject
    public DetailPresenter(NewsApi newsApi) {
        this.mNewsApi = newsApi;
    }

    @Override
    public void getData(final String id, final String action, int pullNum) {
        mNewsApi.getNewsDetail(id, action, pullNum)
                .compose(RxSchedulers.<NewsDetail>applySchedulers())
                .filter(new Predicate<NewsDetail>() {
                    @Override
                    public boolean test(@NonNull NewsDetail newsDetail) throws Exception {
                        if (NewsUtils.isBannerNews(newsDetail)) {
                            mView.loadBannerData(newsDetail);
                        }
                        if (NewsUtils.isTopNews(newsDetail)) {
                            mView.loadTopNewsData(newsDetail);
                        }
                        return NewsUtils.isListNews(newsDetail);
                    }
                })
                .map(new Function<NewsDetail, List<NewsDetail.ItemBean>>() {
                    @Override
                    public List<NewsDetail.ItemBean> apply(@NonNull NewsDetail newsDetail) throws Exception {
                        Iterator<NewsDetail.ItemBean> iterator = newsDetail.getItem().iterator();
                        while (iterator.hasNext()) {
                            try {
                                NewsDetail.ItemBean bean = iterator.next();
                                if (bean.getType().equals(NewsUtils.TYPE_DOC)) {if (bean.getStyle().getView() != null) {
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_TITLEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_TITLEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG;
                                        }
                                    }
                                } else if (bean.getType().equals(NewsUtils.TYPE_ADVERT)) {
                                    if (bean.getStyle() != null) {
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_TITLEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG;
                                        } else if (bean.getStyle().getView().equals(NewsUtils.VIEW_SLIDEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG;
                                        }
                                    } else {
                                        //bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG;
                                        iterator.remove();
                                    }
                                } else if (bean.getType().equals(NewsUtils.TYPE_SLIDE)) {
                                    if (bean.getLink().getType().equals("doc")){
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_SLIDEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_TITLEIMG;
                                        }
                                    }else {
                                        bean.itemType = NewsDetail.ItemBean.TYPE_SLIDE;
                                    }
                                } else if (bean.getType().equals(NewsUtils.TYPE_PHVIDEO)) {
                                    bean.itemType = NewsDetail.ItemBean.TYPE_PHVIDEO;
                                } else {
                                    // 凤凰新闻 类型比较多，目前只处理能处理的类型
                                    iterator.remove();
                                }
                            } catch (Exception e) {
                                iterator.remove();
                                e.printStackTrace();
                            }
                        }
                        return newsDetail.getItem();
                    }
                })
                .compose(mView.<List<NewsDetail.ItemBean>>bindToLife())
                .subscribe(new BaseObserver<List<NewsDetail.ItemBean>>() {
                    @Override
                    public void onSucess(List<NewsDetail.ItemBean> itemBeen) {
                        if (!action.equals(NewsApi.ACTION_UP)) {
                            mView.loadData(itemBeen);
                        } else {
                            mView.loadMoreData(itemBeen);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.i(TAG, "onFail: " + e.getMessage().toString());
                        if (!action.equals(NewsApi.ACTION_UP)) {
                            mView.loadData(null);
                        } else {
                            mView.loadMoreData(null);
                        }
                    }
                });
    }
}
