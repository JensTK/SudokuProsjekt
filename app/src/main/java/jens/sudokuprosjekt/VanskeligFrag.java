package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

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
                if (getActivity() instanceof LagActivity) {
                    final LagActivity act = (LagActivity) getActivity();
                    final Brett brett = act.getBrettFrag().getBrettet();
                    brett.setDiff(i);
                    DialogFragment frag = new NavnFrag();
                    frag.show(getFragmentManager(), "");
                }
                else {
                    DialogFragment frag = new VelgBrettFrag();
                    Bundle bndl = new Bundle();
                    bndl.putInt(MainActivity.diff, i);
                    frag.setArguments(bndl);
                    frag.show(getFragmentManager(), "");
                }
            }
        });
        return bldr.create();
    }
}