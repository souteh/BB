package com.sofrecom.sofid.utils;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by oibnchahdia on 06/05/2019
 */
public class CustomAlertDialog extends SweetAlertDialog {
    public CustomAlertDialog(Context context) {
        super(context);
    }

    public CustomAlertDialog(Context context, int alertType) {
        super(context, alertType);
    }
}
