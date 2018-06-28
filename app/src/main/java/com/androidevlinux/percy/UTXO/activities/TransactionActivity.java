package com.androidevlinux.percy.UTXO.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.fragments.CreateTransactionFragment;

public class TransactionActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        insertFragment(new CreateTransactionFragment());

        setTitle(R.string.create_transaction);

        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar (toolbar).
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
