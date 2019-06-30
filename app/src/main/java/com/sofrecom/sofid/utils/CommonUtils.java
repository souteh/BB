package com.sofrecom.sofid.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.sofrecom.sofid.R;

import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by oibnchahdia on 11/04/2019
 */
public final class CommonUtils {

    public static CustomAlertDialog getLoadingDialog(Context context) {
        CustomAlertDialog progressDialog = new CustomAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.setTitleText(context.getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String getDBPassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
