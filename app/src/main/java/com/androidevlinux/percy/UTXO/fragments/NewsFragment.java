package com.androidevlinux.percy.UTXO.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.adapters.NewsAdapter;
import com.androidevlinux.percy.UTXO.data.models.newsapi.Article;
import com.androidevlinux.percy.UTXO.data.models.newsapi.NewsBean;
import com.androidevlinux.percy.UTXO.utils.NativeUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    Unbinder unbinder;
    @BindView(R.id.news_recycler_view)
    RecyclerView newsRecyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    CompositeDisposable disposables;
    Observable<NewsBean> newsBeanObservable;
    ArrayList<Article> articleArrayList;
    NewsAdapter newsAdapter;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert inflater != null;
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        assert view != null;
        super.onViewCreated(view, savedInstanceState);
        articleArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        newsRecyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(articleArrayList, mActivity, mSwipeRefreshLayout);
        newsRecyclerView.setAdapter(newsAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));
        new refreshTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void getNews() {
        newsBeanObservable = apiManager.getNewsData(NativeUtils.getNewsApiKey());
        disposables = new CompositeDisposable();
        disposables.add(newsBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NewsBean>() {

                    @Override
                    public void onNext(NewsBean value) {
                        articleArrayList = (ArrayList<Article>) value.getArticles();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        newsAdapter = new NewsAdapter(articleArrayList, mActivity, mSwipeRefreshLayout);
                        newsRecyclerView.setAdapter(newsAdapter);
                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposables != null) {
            disposables.dispose();
        }
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        disposables.dispose();
        new refreshTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @SuppressLint("StaticFieldLeak")
    private class refreshTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            articleArrayList.clear();
        }

        @Override
        protected String doInBackground(String... value) {
            getNews();
            return null;
        }

        @Override
        protected void onPostExecute(String value) {
            newsAdapter.notifyDataSetChanged();
        }
    }
}
