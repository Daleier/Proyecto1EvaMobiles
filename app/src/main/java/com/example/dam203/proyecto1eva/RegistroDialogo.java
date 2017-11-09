package com.example.dam203.proyecto1eva;

/**
 * Created by dam203 on 09/11/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class RegistroDialogo extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.titulo_campos_registro_no_validos).setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.campos_registro_vacios)
                .setPositiveButton(R.string.aceptar, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        this.setCancelable(false);
        return builder.create();
    }
}
