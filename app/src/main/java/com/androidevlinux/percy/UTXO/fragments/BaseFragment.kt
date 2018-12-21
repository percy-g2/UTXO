package com.androidevlinux.percy.UTXO.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

import com.androidevlinux.percy.UTXO.data.network.ApiManager

open class BaseFragment : Fragment() {
    protected var apiManager: ApiManager? = null
    protected var mActivity: Activity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity?
    }

    override fun onDetach() {
        super.onDetach()
        this.mActivity = null
    }

    override fun onStop() {
        super.onStop()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiManager = ApiManager.instance
    }
}
