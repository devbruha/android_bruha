// Copyright 2015, Thomas Miele and Bilal Chowdhry, All rights reserved.

package com.bruha.bruha.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;


/**
 * Created by Thomas on 5/6/2015.
 */

public class AlertDialogFragment extends DialogFragment{

    private String errorTitle;
    private String errorMessage;

    static AlertDialogFragment newInstance(String errorTitle, String errorMessage) {
        AlertDialogFragment f = new AlertDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();

        args.putString("Error Title", errorTitle);
        args.putString("Error Message", errorMessage);

        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        errorTitle = getArguments().getString("Error Title");
        errorMessage = getArguments().getString("Error Message");

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(errorTitle)
                .setMessage(errorMessage)
                .setPositiveButton("OK", null);


        AlertDialog dialog = builder.create();
        return dialog;
    }
}