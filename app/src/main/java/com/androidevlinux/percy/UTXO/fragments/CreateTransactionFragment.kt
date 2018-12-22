package com.androidevlinux.percy.UTXO.fragments

import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.data.models.changelly.GetCurrenciesResponseBean
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean
import com.androidevlinux.percy.UTXO.data.models.changelly.ParamsBean
import com.androidevlinux.percy.UTXO.data.models.changelly.TransactionBean
import com.androidevlinux.percy.UTXO.utils.Constants
import com.androidevlinux.percy.UTXO.utils.Utils
import com.google.gson.Gson
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.create_transaction_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by percy on 15/11/2017.
 */

class CreateTransactionFragment : BaseFragment() {

    internal var currenciesStringList: List<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_transaction_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currenciesStringList = ArrayList()
        btnGetTransactionFragment.setOnClickListener {
            if (Utils.isConnectingToInternet(mActivity!!)) {
                if (spinnerFromTransactionFragment!!.selectedItem != null && spinnerToTransactionFragment!!.selectedItem != null && !edtAmountTransactionFragment!!.text!!.toString().isEmpty() && !edtUserPayOutAddressTransactionFragment!!.text!!.toString().isEmpty()) {
                    createTransaction(spinnerFromTransactionFragment!!.selectedItem.toString(), spinnerToTransactionFragment!!.selectedItem.toString(), edtAmountTransactionFragment!!.text!!.toString(), edtUserPayOutAddressTransactionFragment!!.text!!.toString())
                } else {
                    Toasty.warning(mActivity!!, "Empty Fields Please Check", Toast.LENGTH_SHORT, true).show()
                }
            } else {
                Toasty.warning(mActivity!!, "No Internet", Toast.LENGTH_SHORT, true).show()
            }
        }

        if (Constants.currenciesStringList == null || Constants.currenciesStringList!!.isEmpty()) {
            init()
        } else {
            val currenciesStringListAdapter = ArrayAdapter(mActivity!!,
                    R.layout.spinner_item, Constants.currenciesStringList!!)
            currenciesStringListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFromTransactionFragment!!.adapter = currenciesStringListAdapter
            spinnerToTransactionFragment!!.adapter = currenciesStringListAdapter
        }
        registerForContextMenu(txtServerResponsePayInAddressTransactionFragment!!)
    }

    private fun createTransaction(From: String, To: String, amount: String, address: String) {
        val mainBodyBean = MainBodyBean()
        mainBodyBean.id = 1
        mainBodyBean.jsonrpc = "2.0"
        mainBodyBean.method = "createTransaction"
        val params = ParamsBean()
        params.from = From
        params.to = To
        params.amount = amount
        params.address = address
        mainBodyBean.params = params
        var sign: String? = null
        try {
            sign = Utils.hmacDigest(Gson().toJson(mainBodyBean), Constants.secret_key)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val progressDialog = ProgressDialog(mActivity)
        progressDialog.setTitle("Downloading")
        progressDialog.setMessage("Please wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.isIndeterminate = true
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.window!!.setGravity(Gravity.CENTER)
        progressDialog.show()
        apiManager!!.createTransaction(sign!!, mainBodyBean, object : Callback<TransactionBean> {
            override fun onResponse(call: Call<TransactionBean>, response: Response<TransactionBean>) {
                if (response.body() != null) {
                    if (response.body()!!.error != null) {
                        Toasty.error(mActivity!!, response.body()!!.error!!.message.toString(), Toast.LENGTH_SHORT, true).show()
                    } else {
                        txtServerResponseTransactionIdTransactionFragment!!.text = response.body()!!.result!!.id
                        txtServerResponsePayInAddressTransactionFragment!!.text = response.body()!!.result!!.payinAddress
                    }
                    // Log.i("Transaction", response.body().toString());
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<TransactionBean>, t: Throwable) {
                progressDialog.dismiss()
            }
        })
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val clipboard = mActivity!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Pay In Address", txtServerResponsePayInAddressTransactionFragment!!.text.toString())
        clipboard.primaryClip = clip
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
            sign = Utils.hmacDigest(Gson().toJson(mainBodyBean), Constants.secret_key)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val progressDialog = ProgressDialog(mActivity)
        progressDialog.setTitle("Downloading")
        progressDialog.setMessage("Please wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.isIndeterminate = true
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.window!!.setGravity(Gravity.CENTER)
        progressDialog.show()
        apiManager!!.getCurrencies(sign!!, mainBodyBean, object : Callback<GetCurrenciesResponseBean> {
            override fun onResponse(call: Call<GetCurrenciesResponseBean>, response: Response<GetCurrenciesResponseBean>) {
                if (response.body() != null) {
                    currenciesStringList = response.body()!!.result
                    Constants.currenciesStringList = currenciesStringList
                    val currenciesStringListAdapter = ArrayAdapter(mActivity!!,
                            R.layout.spinner_item, currenciesStringList!!)
                    currenciesStringListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerFromTransactionFragment!!.adapter = currenciesStringListAdapter
                    spinnerToTransactionFragment!!.adapter = currenciesStringListAdapter
                }
                progressDialog.dismiss()
                if (response.code() == 401) {
                    Toasty.error(mActivity!!, "Unauthorized! Please Check Your Keys", Toast.LENGTH_SHORT, true).show()
                }
            }

            override fun onFailure(call: Call<GetCurrenciesResponseBean>, t: Throwable) {
                progressDialog.dismiss()
            }
        })
    }
}



