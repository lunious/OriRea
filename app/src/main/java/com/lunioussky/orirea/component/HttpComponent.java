package com.lunioussky.orirea.component;

import com.lunioussky.orirea.ui.jandan.JdDetailFragment;
import com.lunioussky.orirea.ui.news.ArticleReadActivity;
import com.lunioussky.orirea.ui.news.ImageBrowseActivity;
import com.lunioussky.orirea.ui.news.NewsFragment;
import com.lunioussky.orirea.ui.video.DetailFragment;
import com.lunioussky.orirea.ui.video.VideoFragment;

import dagger.Component;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/2 .
 */
@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {

    void inject(VideoFragment videoFragment);

    void inject(DetailFragment detailFragment);

    void inject(JdDetailFragment jdDetailFragment);

    void inject(ImageBrowseActivity imageBrowseActivity);

    void inject( com.lunioussky.orirea.ui.news.DetailFragment detailFragment);

    void inject(ArticleReadActivity articleReadActivity);

    void inject(NewsFragment newsFragment);

}
