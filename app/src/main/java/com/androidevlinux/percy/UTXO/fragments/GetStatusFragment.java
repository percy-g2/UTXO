package com.androidevlinux.percy.UTXO.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.activities.TransactionActivity;
import com.androidevlinux.percy.UTXO.data.models.changelly.GetMinAmountReponseBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.MainBodyBean;
import com.androidevlinux.percy.UTXO.data.models.changelly.ParamsBean;
import com.androidevlinux.percy.UTXO.utils.Constants;
import com.androidevlinux.percy.UTXO.utils.Utils;
import com.google.gson.Gson;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by percy on 15/11/2017.
 */

public class GetStatusFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.btn_get_amount)
    AppCompatButton btnGetAmount;
    @BindView(R.id.txt_min_amount)
    AppCompatTextView txtMinAmount;
    Unbinder unbinder;
    @BindView(R.id.txt_info)
    AppCompatTextView txtInfo;
    @BindView(R.id.edtTransactionId)
    AppCompatEditText edtTransactionId;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert inflater != null;
        View view = inflater.inflate(R.layout.get_status_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        assert view != null;
        super.onViewCreated(view, savedInstanceState);
    }

    private void MinAmount(String strTransactionId) {
        MainBodyBean mainBodyBean = new MainBodyBean();
        mainBodyBean.setId(1);
        mainBodyBean.setJsonrpc("2.0");
        mainBodyBean.setMethod("getStatus");
        ParamsBean params = new ParamsBean();
        params.setId(strTransactionId);
        mainBodyBean.setParams(params);
        String sign = null;
        try {
            sign = Utils.hmacDigest(new Gson().toJson(mainBodyBean), Constants.secret_key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ProgressDialog progressDialog = new ProgressDialog(mActivity);
        progressDialog.setTitle("Downloading");
        progressDialog.setMessage("Please wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setGravity(Gravity.CENTER);
        progressDialog.show();
        apiManager.getMinAmount(sign, mainBodyBean, new Callback<GetMinAmountReponseBean>() {
            @Override
            public void onResponse(@NonNull Call<GetMinAmountReponseBean> call, @NonNull Response<GetMinAmountReponseBean> response) {
                if (response.body() != null) {
                    if (Objects.requireNonNull(response.body()).getError() != null) {
                        Toasty.error(mActivity, Objects.requireNonNull(response.body()).getError().getMessage(), Toast.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.success(mActivity, Objects.requireNonNull(response.body()).getResult(), Toast.LENGTH_SHORT, true).show();
                        txtMinAmount.setText(Objects.requireNonNull(response.body()).getResult());
                    }
                }
                if (response.code() == 401) {
                    Toasty.error(mActivity, "Unauthorized! Please Check Your Keys", Toast.LENGTH_SHORT, true).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<GetMinAmountReponseBean> call, @NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    @OnClick({R.id.btn_get_amount, R.id.create_transaction})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_amount:
                if (Utils.isConnectingToInternet(mActivity)) {
                    if (!edtTransactionId.getText().toString().isEmpty()) {
                        MinAmount(edtTransactionId.getText().toString());
                    } else {
                        Toasty.warning(mActivity, "Empty Fields Please Check", Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.warning(mActivity, "No Internet", Toast.LENGTH_SHORT, true).show();
                }
                break;
            case R.id.create_transaction:
                startActivity(new Intent(mActivity, TransactionActivity.class));
                break;
        }
    }
}


