package jens.sudokuprosjekt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jenstobiaskaarud on 10/31/17.
 */

public class VelgBrettFrag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final FilBehandler filer = new FilBehandler(getActivity());
        final int vansk = getArguments().getInt(MainActivity.diff);
        Log.i(MainActivity.tagg, "Valgt vansk: " + vansk);
        final String[] navnene = filer.getNavnene(vansk, true);
        final AlertDialog.Builder bldr = new AlertDialog.Builder(getActivity());
        bldr.setTitle(R.string.velgBrett);
        bldr.setItems(navnene, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Brett brettet = filer.getBrett(navnene[i]);
                BrettManager.lagreTilMinne(getActivity(), brettet, true);
                getActivity().startActivity(new Intent(".SudokuActivity"));
            }
        });
        bldr.setPositiveButton(getString(R.string.tilfBrett), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Random rand = new Random();
                Brett brettet = filer.getBrett(navnene[rand.nextInt(navnene.length)]);
                BrettManager.lagreTilMinne(getActivity(), brettet, true);
                getActivity().startActivity(new Intent((".SudokuActivity")));
            }
        });
        return bldr.create();
    }
}
