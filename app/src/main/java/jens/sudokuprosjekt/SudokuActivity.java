package jens.sudokuprosjekt;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Jens on 22.10.2017.
 */

public class SudokuActivity extends Activity {
    private Brett brettet;

    @Override
    public void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        setContentView(R.layout.activity_sudoku);

        if (!getIntent().getBooleanExtra(VanskeligFrag.newNavn, true)) {
            brettet = BrettManager.fortsFraMinne(this);
        }
        else {
            brettet = BrettManager.lesFraFil(this, "");
        }

        BrettManager.lagBrettFragment(this, brettet);

        FragmentManager fgm = getFragmentManager();
        FragmentTransaction tran = fgm.beginTransaction();
        tran.replace(R.id.knappView, new KnappFragment());
        tran.commit();

        getIntent().putExtra(VanskeligFrag.newNavn, false);
    }

    public Brett getBrettet() {
        return brettet;
    }
}
