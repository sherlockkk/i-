package com.alpha.jxust.tools;

import android.app.Activity;
import android.app.ProgressDialog;

import com.alpha.jxust.R;

/**
 * @author SongJian
 * @created 2016/3/2.
 * @e-mail 1129574214@qq.com
 */
public class ToolDialog {
    public static ProgressDialog showSpinnerDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getString(R.string.hardLoading));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(true);
        dialog.show();
        return dialog;
    }
}
