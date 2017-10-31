package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by jenstobiaskaarud on 10/31/17.
 */

public class VelgBrettFrag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int vansk = getArguments().getInt(MainActivity.diff);
        final String[] navnene = new FilBehandler(getActivity()).getNavnene(vansk);
        final AlertDialog.Builder bldr = new AlertDialog.Builder(getActivity());
        bldr.setTitle(R.string.velgBrett);
        bldr.setItems(navnene, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(".SudokuActivity");
                    intent.putExtra(MainActivity.navn, navnene[i]);
                    intent.putExtra(MainActivity.ny, true);
                    getActivity().startActivity(intent);
            }
        });
        return bldr.create();
    }
}
