package com.example.dam203.proyecto1eva;

/**
 * Created by dam203 on 10/11/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class PassInvalidaDialogo extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.titulo_password_invalida).setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.password_invalida)
                .setPositiveButton(R.string.aceptar, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Código asociado al botón Aceptar. Por ejemplo:
                        //Toast.makeText(getActivity(), "PULSADA OPCION BOA", Toast.LENGTH_LONG).show();
                    }
                });
        this.setCancelable(false);
        return builder.create();
    }
}
