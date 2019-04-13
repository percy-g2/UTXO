package com.androidevlinux.percy.UTXO.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.adapters.NewsAdapter
import com.androidevlinux.percy.UTXO.data.models.newsapi.Article
import com.androidevlinux.percy.UTXO.data.models.newsapi.NewsBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.*
import java.util.*

class NewsFragment : BaseFragment(), androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {
    private var disposables: CompositeDisposable? = null
    private var newsBeanObservable: Observable<NewsBean>? = null
    private var articleArrayList: ArrayList<Article>? = null
    private var newsAdapter: NewsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleArrayList = ArrayList()
        val linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        news_recycler_view!!.layoutManager = linearLayoutManager
        newsAdapter = NewsAdapter(articleArrayList!!, mActivity!!)
        news_recycler_view!!.adapter = newsAdapter
        swipe_container!!.setOnRefreshListener(this)
        swipe_container!!.setColorSchemeColors(ContextCompat.getColor(mActivity!!, android.R.color.holo_green_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_red_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_blue_dark),
                ContextCompat.getColor(mActivity!!, android.R.color.holo_orange_dark))
        getNews()
    }

    override fun onResume() {
        super.onResume()
        mActivity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun getNews() {
        if (!swipe_container!!.isRefreshing) {
            swipe_container!!.isRefreshing = true
        }
        articleArrayList!!.clear()
        newsBeanObservable = apiManager!!.getNewsData(NativeUtils.newsApiKey)
        disposables = CompositeDisposable()
        disposables!!.add(newsBeanObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<NewsBean>() {

                    override fun onNext(value: NewsBean) {
                        //check for duplicate articles from list
                        for (article in value.articles!!) {
                            var exists = false
                            for (existingArticle in articleArrayList!!) {
                                if (article.title!! == existingArticle.title) {
                                    exists = true
                                }
                            }
                            if (!exists) {
                                articleArrayList!!.add(article)
                                newsAdapter!!.notifyDataSetChanged()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        if (swipe_container.isRefreshing) {
                            swipe_container.isRefreshing = false

                        }
                        newsAdapter!!.notifyDataSetChanged()
                    }
                }))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (disposables != null) {
            disposables!!.dispose()
        }
    }

    override fun onRefresh() {
        disposables!!.dispose()
        getNews()
    }
}
