package com.androidevlinux.percy.UTXO.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidevlinux.percy.UTXO.R;

/**
 * Created by percy on 15/11/2017.
 */

public class CustomProgressDialog {
    static Dialog progDialog = null;
    static ProgressBar progBar = null;
    static TextView lblMessage = null;

    private static void initializeCustomProgressDialog(Context activityContext,
                                                       String strTemp) {
        if (progDialog == null) {
            progDialog = new Dialog(activityContext, R.style.progress_dialog);
            progDialog.setContentView(R.layout.progress_dialog);
            progBar = progDialog.findViewById(R.id.progBar);
            lblMessage = progDialog.findViewById(R.id.lblMessage);
        }
        lblMessage.setText(strTemp);
    }

    public static Dialog showCustomProgressDialog(Context activityContext,
                                                  String strTemp) {
        try {
            initializeCustomProgressDialog(activityContext, strTemp);
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ignored) {
        }
        return progDialog;
    }

    public static void dismissCustomProgressDialog(Dialog mProgDialog) {
        if (mProgDialog != null) {
            mProgDialog.dismiss();
        }
        progDialog = null;
        progBar = null;
        lblMessage = null;
    }

}

