package com.androidevlinux.percy.UTXO.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.activities.TransactionActivity
import com.androidevlinux.percy.UTXO.data.models.changelly.GetCurrenciesResponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean
import com.androidevlinux.percy.UTXO.data.models.changelly.ParamsBean
import com.androidevlinux.percy.UTXO.utils.Constants
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import com.androidevlinux.percy.UTXO.utils.Utils
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.exchange_amount_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by percy on 15/11/2017.
 */

class ExchangeAmountFragment : BaseFragment(), View.OnClickListener {
    internal var currenciesStringList: List<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.exchange_amount_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnGetExchangeFragment.setOnClickListener(this)
        createTransactionExchangeFragment.setOnClickListener(this)
        currenciesStringList = ArrayList()
        if (Constants.currenciesStringList == null || Constants.currenciesStringList!!.isEmpty()) {
            init()
        } else {
            val currenciesStringListAdapter = ArrayAdapter(mActivity!!,
                    android.R.layout.simple_spinner_item, Constants.currenciesStringList!!)
            currenciesStringListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFromExchangeFragment!!.adapter = currenciesStringListAdapter
            spinnerToExchangeFragment!!.adapter = currenciesStringListAdapter
        }
    }

    private fun minAmount(From: String, To: String, amount: String) {
        val mainBodyBean = MainBodyBean()
        mainBodyBean.id = 1
        mainBodyBean.jsonrpc = "2.0"
        mainBodyBean.method = "getExchangeAmount"
        val params = ParamsBean()
        params.from = From
        params.to = To
        params.amount = amount
        mainBodyBean.params = params
        var sign: String? = null
        try {
            sign = Utils.hmacDigest(Gson().toJson(mainBodyBean), NativeUtils.changellySecretKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        progressBarExchangeFragment.visibility = View.VISIBLE
        apiManager!!.getMinAmount(sign!!, mainBodyBean, object : Callback<GetMinAmountReponseBean> {
            override fun onResponse(call: Call<GetMinAmountReponseBean>, response: Response<GetMinAmountReponseBean>) {
                if (response.body() != null) {
                    if (response.body()!!.error != null) {
                        Toasty.error(mActivity!!, response.body()!!.error!!.message.toString(), Toast.LENGTH_SHORT, true).show()
                    } else {
                        Toasty.success(mActivity!!, response.body()!!.result!!, Toast.LENGTH_SHORT, true).show()
                        txtServerResponseStatusFragment!!.text = response.body()!!.result
                    }
                }
                progressBarExchangeFragment.visibility = View.GONE
            }

            override fun onFailure(call: Call<GetMinAmountReponseBean>, t: Throwable) {
                progressBarExchangeFragment.visibility = View.GONE
            }
        })
    }

    private fun init() {
        val mainBodyBean = MainBodyBean()
        mainBodyBean.id = 1
        mainBodyBean.jsonrpc = "2.0"
        mainBodyBean.method = "getCurrencies"
        val params = ParamsBean()
        mainBodyBean.params = params
        var sign: String? = null
        try {
            sign = Utils.hmacDigest(Gson().toJson(mainBodyBean), NativeUtils.changellySecretKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        progressBarExchangeFragment.visibility = View.VISIBLE
        apiManager!!.getCurrencies(sign!!, mainBodyBean, object : Callback<GetCurrenciesResponseBean> {
            override fun onResponse(call: Call<GetCurrenciesResponseBean>, response: Response<GetCurrenciesResponseBean>) {
                if (response.body() != null) {
                    currenciesStringList = response.body()!!.result
                    Constants.currenciesStringList = currenciesStringList
                    val currenciesStringListAdapter = ArrayAdapter(mActivity!!,
                            android.R.layout.simple_spinner_item, currenciesStringList!!)
                    currenciesStringListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerFromExchangeFragment!!.adapter = currenciesStringListAdapter
                    spinnerToExchangeFragment!!.adapter = currenciesStringListAdapter
                }
                progressBarExchangeFragment.visibility = View.GONE
                if (response.code() == 401) {
                    Toasty.error(mActivity!!, "Unauthorized! Please Check Your Keys", Toast.LENGTH_SHORT, true).show()
                }
            }

            override fun onFailure(call: Call<GetCurrenciesResponseBean>, t: Throwable) {
                Log.i("DownloadFlagSuccess", t.message)
                progressBarExchangeFragment.visibility = View.GONE
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnGetExchangeFragment -> if (Utils.isConnectingToInternet(mActivity!!)) {
                if (spinnerFromExchangeFragment!!.selectedItem != null && spinnerToExchangeFragment!!.selectedItem != null && edtAmountExchangeFragment!!.text!!.toString().isNotEmpty()) {
                    minAmount(spinnerFromExchangeFragment!!.selectedItem.toString(), spinnerToExchangeFragment!!.selectedItem.toString(), edtAmountExchangeFragment!!.text!!.toString())
                } else {
                    Toasty.warning(mActivity!!, "Empty Fields Please Check", Toast.LENGTH_SHORT, true).show()
                }
            } else {
                Toasty.warning(mActivity!!, "No Internet", Toast.LENGTH_SHORT, true).show()
            }
            R.id.createTransactionExchangeFragment -> startActivity(Intent(mActivity, TransactionActivity::class.java))
        }
    }
}

