package com.alpha.jxust.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.alpha.jxust.tools.ToolDialog;

/**
 * @author SongJian
 * @created 2016/3/2.
 * @e-mail 1129574214@qq.com
 */
public abstract class StatusNetAsyncTask extends AsyncTask<Void, Void, Void> {
    protected Context context;
    ProgressDialog dialog;
    boolean openDialog = true;
    Exception exception;

    public StatusNetAsyncTask(Context context) {
        this.context = context;
    }

    public StatusNetAsyncTask(Context context, boolean openDialog) {
        this.context = context;
        this.openDialog = openDialog;
    }

    public StatusNetAsyncTask setOpenDialog(boolean openDialog) {
        this.openDialog = openDialog;
        return this;
    }

    public ProgressDialog getDialog() {
        return dialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (openDialog) {
            dialog = ToolDialog.showSpinnerDialog((Activity) context);
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            doInBack();
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (openDialog){
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }
        onPost(exception);
    }

    protected abstract void doInBack() throws Exception;

    protected abstract void onPost(Exception e);
}
