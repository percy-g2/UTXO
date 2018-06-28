package com.androidevlinux.percy.UTXO.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.androidevlinux.percy.UTXO.data.network.ApiManager;

public class BaseFragment extends Fragment {
    protected ApiManager apiManager;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = ApiManager.getInstance();
    }
}
