package ua.kaganovych.weatherapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utils {

    public static void showOkDialog(Context ctx, int title, int message)
    {
        showOkDialog(ctx, title, message, null);
    }
    public static void showOkDialog(Context ctx, int title, int message, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(android.R.string.ok, listener);
        alert.show();
    }
}
