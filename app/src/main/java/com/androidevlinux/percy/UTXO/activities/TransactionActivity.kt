package com.androidevlinux.percy.UTXO.activities

import android.os.Bundle
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.fragments.CreateTransactionFragment

class TransactionActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        insertFragment(CreateTransactionFragment())

        setTitle(R.string.create_transaction)

        setupActionBar()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
