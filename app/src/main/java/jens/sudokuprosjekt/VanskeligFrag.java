package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by Jens on 30.10.2017.
 */

public class VanskeligFrag extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder bldr = new AlertDialog.Builder(getActivity());
        bldr.setTitle(R.string.vanskeligMessage);
        bldr.setItems(R.array.vanskeligArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (getArguments().getBoolean(MainActivity.lag)) {
                    final LagActivity act = (LagActivity) getActivity();
                    final Brett brett = act.getBrettet();
                    brett.setDiff(i);
                    DialogFragment frag = new NavnFrag();
                    frag.show(getFragmentManager(), "");
                }
                else {
                    DialogFragment frag = new VelgBrettFrag();
                    frag.setArguments(getArguments());
                    frag.show(getFragmentManager(), "");
                }
            }
        });
        return bldr.create();
    }
}