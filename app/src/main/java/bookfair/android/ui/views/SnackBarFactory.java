package bookfair.android.ui.views;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import bookfair.android.R;

public class SnackBarFactory {

    public static Snackbar createSnackbar(Context context, android.view.View view, @StringRes int message) {
        Snackbar snackbar = Snackbar.make(view, context.getString(message), Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
        return snackbar;
    }

    public static Snackbar createSnackbar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black));
        return snackbar;
    }
}
