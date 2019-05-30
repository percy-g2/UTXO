package com.androidevlinux.percy.UTXO.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.activities.TransactionActivity
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean
import com.androidevlinux.percy.UTXO.data.models.changelly.ParamsBean
import com.androidevlinux.percy.UTXO.utils.NativeUtils
import com.androidevlinux.percy.UTXO.utils.Utils
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.get_status_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by percy on 15/11/2017.
 */

class GetStatusFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.get_status_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTransactionStatusFragment.setOnClickListener(this)
        btnGetStatusFragment.setOnClickListener(this)
    }

    private fun minAmount(strTransactionId: String) {
        val mainBodyBean = MainBodyBean()
        mainBodyBean.id = 1
        mainBodyBean.jsonrpc = "2.0"
        mainBodyBean.method = "getStatus"
        val params = ParamsBean()
        params.id = strTransactionId
        mainBodyBean.params = params
        var sign: String? = null
        try {
            sign = Utils.hmacDigest(Gson().toJson(mainBodyBean), NativeUtils.changellySecretKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        progressBarStatusFragment.visibility = View.VISIBLE
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
                if (response.code() == 401) {
                    Toasty.error(mActivity!!, "Unauthorized! Please Check Your Keys", Toast.LENGTH_SHORT, true).show()
                }
                progressBarStatusFragment.visibility = View.GONE
            }

            override fun onFailure(call: Call<GetMinAmountReponseBean>, t: Throwable) {
                progressBarStatusFragment.visibility = View.GONE
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnGetStatusFragment -> if (Utils.isConnectingToInternet(mActivity!!)) {
                if (edtTransactionIdStatusFragment!!.text!!.toString().isNotEmpty()) {
                    minAmount(edtTransactionIdStatusFragment!!.text!!.toString())
                } else {
                    Toasty.warning(mActivity!!, "Empty Fields Please Check", Toast.LENGTH_SHORT, true).show()
                }
            } else {
                Toasty.warning(mActivity!!, "No Internet", Toast.LENGTH_SHORT, true).show()
            }
            R.id.createTransactionStatusFragment -> startActivity(Intent(mActivity, TransactionActivity::class.java))
        }
    }
}


