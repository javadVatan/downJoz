package com.vallco.downjoz.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vallco.downjoz.R;

/**
 * Created by Javad on 7/29/2016.
 */
public class ShowDialog extends AlertDialog implements View.OnClickListener {
    Context mContext;
    TextView dialog_tvTitle, dialog_tv_Message;
    Button dialog_btnPositive, dialog_btnNegative;
    ConfirmationDialogEventHandler interfaceEventListener = null;

    public ShowDialog(Context context) {
        super(context);
        mContext = context;
    }

    public void setErrorDialog(ConfirmationDialogEventHandler listener, String title, String message, String negative, String positive) {
        initVariableForErrorDialog();
        interfaceEventListener = listener;
        dialog_tvTitle.setText(title);
        dialog_tv_Message.setText(message);
        dialog_btnPositive.setText(positive);
        dialog_btnNegative.setText(negative);

        if (negative != null || negative != "") {
            dialog_btnNegative.setVisibility(View.VISIBLE);
            dialog_btnNegative.setText(negative);
            dialog_btnNegative.setOnClickListener(this);
        }
        if (positive != null || positive != "") {
            dialog_btnPositive.setVisibility(View.VISIBLE);
            dialog_btnPositive.setText(positive);
            dialog_btnPositive.setOnClickListener(this);
        }
        show();

    }

    private void initVariableForErrorDialog() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        dialog_btnPositive = (Button) dialogView.findViewById(R.id.dialog_btnPositive);
        dialog_btnNegative = (Button) dialogView.findViewById(R.id.dialog_btnNegative);
        dialog_tv_Message = (TextView) dialogView.findViewById(R.id.dialog_tvMessage);
        dialog_tvTitle = (TextView) dialogView.findViewById(R.id.dialog_tvTitle);
        setView(dialogView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btnNegative:
                dismiss();
                interfaceEventListener.negativePressed();
                break;

            case R.id.dialog_btnPositive:
                dismiss();
                interfaceEventListener.positivePressed();
                break;
        }
    }

    public interface ConfirmationDialogEventHandler {
        void positivePressed();

        void negativePressed();
    }
}

