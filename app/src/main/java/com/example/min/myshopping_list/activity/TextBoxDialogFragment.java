package com.example.min.myshopping_list.activity;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.min.myshopping_list.R;



public class TextBoxDialogFragment extends DialogFragment {

    private static final String ARG_TITLE= "title";
    public static final String ARG_INPUT = "userInput";
    private static final String ARG_REQUEST_CODE = "request_code";
    private  int requestCode;

    public static  TextBoxDialogFragment newInstance(String title, String userInput, int requestCode ) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TITLE, title);
        args.putSerializable(ARG_INPUT, userInput);
        args.putSerializable(ARG_REQUEST_CODE, requestCode);
        TextBoxDialogFragment fragment = new TextBoxDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.text_box_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInputView = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        TextView titleView = (TextView) promptsView.findViewById(R.id.textDialogTitle);
        String title= (String) getArguments().getSerializable(ARG_TITLE);
        String userInput = (String) getArguments().getSerializable(ARG_INPUT);
        requestCode = (int) getArguments().getSerializable(ARG_REQUEST_CODE);

        titleView.setText(title);
        userInputView.setText(userInput);

        userInputView.setSelection(userInputView.getText().length()); //cursor at end of text

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //return update text
                        onInputSet(userInputView.getText().toString().trim());
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); //show keyboard


        return alertDialog;
    }

    public void onInputSet(String input) {
        Intent intent = new Intent();
        intent.putExtra(ARG_INPUT, input);
        getTargetFragment().onActivityResult(requestCode, 0, intent);
    }
}

