package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by jenstobiaskaarud on 10/31/17.
 */

public class NavnFrag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder bldr2 = new AlertDialog.Builder(getActivity());
        bldr2.setTitle(R.string.navnTekst);
        final EditText tekst = new EditText(getActivity());
        tekst.setInputType(InputType.TYPE_CLASS_TEXT);
        bldr2.setView(tekst);
        bldr2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LagActivity act = (LagActivity) getActivity();
                Brett brett = act.getBrettet();
                brett.setNavn(tekst.getText().toString());
                FilBehandler fil = new FilBehandler(act);
                fil.putBrett(brett);
                fil.skrivTilFil();
                startActivity(new Intent(act, MainActivity.class));
            }
        });
        return bldr2.create();
    }
}
