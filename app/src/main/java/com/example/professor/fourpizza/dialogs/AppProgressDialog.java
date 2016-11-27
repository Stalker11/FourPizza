package com.example.professor.fourpizza.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.professor.fourpizza.R;

public class AppProgressDialog {
    private static final String TAG = AppProgressDialog.class.getSimpleName();
    private static ProgressDialog progress;
    private static Context context;

    public static void setContext(Context cont) {
        context = cont;
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(context.getResources().getString(R.string.load_image));
    }

    public static void showProgressDialog() {
        if (progress.isShowing()) return;
        progress.show();
        Log.d(TAG, "showProgressDialog: " + progress.getProgress());
    }

    public static void hideProgressDialog() {
        if (progress != null) {
            progress.cancel();
            Log.d(TAG, "showProgressDialog:  finished");
        }

    }
}
