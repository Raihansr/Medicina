package com.raihan_sr.medicina.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.raihan_sr.medicina.R;

public class CustomSnackbar {

    public enum Duration {
        SHORT, LONG, INDEFINITE
    }

    public static Snackbar      snackbar;
    private Context             mContext;
    private View                layout;
    private String              message;
    private String              buttonText;
    private Duration            duration;
    private OnDismissListener   mOnDismissListener;

    public CustomSnackbar(Context mContext, View layout, String message, Duration duration) {
        this.mContext = mContext;
        this.layout = layout;
        this.message = message;
        this.duration = duration;
    }

    public CustomSnackbar(Context mContext, View layout, String message, String buttonText, Duration duration) {
        this.mContext = mContext;
        this.layout = layout;
        this.message = message;
        this.buttonText = buttonText;
        this.duration = duration;
    }

    public void show(){
        switch (duration){
            case SHORT : snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
                break;
            case LONG : snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
                break;
            case INDEFINITE : snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_INDEFINITE);
                break;
        }

        Typeface snackBarTf = Typeface.createFromAsset(mContext.getAssets(), "pf_regular.ttf");

        snackbar.setAction(buttonText != null ? buttonText : mContext.getResources().getString(R.string.dismiss), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDismissListener != null) mOnDismissListener.onDismiss(snackbar);
                else snackbar.dismiss();
            }
        }).setActionTextColor(Color.WHITE);

        Snackbar.SnackbarLayout s_layout = (Snackbar.SnackbarLayout) snackbar.getView();

        TextView textView = s_layout.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setMaxLines(3);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setTypeface(snackBarTf);

        s_layout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_snackbar));

        snackbar.show();
    }

    public void dismiss(){
        snackbar.dismiss();
    }

    public void setOnDismissListener(OnDismissListener mOnDismissListener) {
        this.mOnDismissListener = mOnDismissListener;
    }

    public interface OnDismissListener {
        void onDismiss(Snackbar snackbar);
    }
}
