package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Jens on 30.10.2017.
 */

public class VanskeligFrag extends DialogFragment {
    public static String vanskNavn = "vansk";
    public static String newNavn = "new";
    public static String lagNavn = "lag";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder bldr = new AlertDialog.Builder(getActivity());
        bldr.setTitle(R.string.vanskeligMessage);
        bldr.setItems(R.array.vanskeligArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (getArguments().getBoolean(lagNavn)) {

                }
                else {
                    Intent intent = new Intent(".SudokuActivity");
                    intent.putExtra(vanskNavn, i);
                    intent.putExtra(newNavn, true);
                    startActivity(intent);
                }
            }
        });
        return bldr.create();
    }
}
