package com.example.dam203.proyecto1eva.dialogos;

/**
 * Created by dam203 on 31/10/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.example.dam203.proyecto1eva.R;

public class LoginDialogo extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialogo_titulo).setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.dialogo_mensaje)
                .setPositiveButton(R.string.aceptar, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Código asociado al botón Aceptar. Por ejemplo:
                        //Toast.makeText(getActivity(), "PULSADA OPCION BOA", Toast.LENGTH_LONG).show();
                    }
                });
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.fuente);
        this.setCancelable(false);
        return builder.create();
    }
}
