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
    private BrettFragment brettFrag;

    @Override
    public void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        setContentView(R.layout.activity_sudoku);

        FragmentManager fgm = getFragmentManager();
        brettFrag = new BrettFragment();
        Bundle fragBun = new Bundle();
        fragBun.putBoolean("new", getIntent().getBooleanExtra("new", false));
        brettFrag.setArguments(fragBun);
        FragmentTransaction tran = fgm.beginTransaction();
        tran.replace(R.id.brettView, brettFrag);
        tran.replace(R.id.knappView, new KnappFragment());
        tran.commit();

        getIntent().putExtra("new", false);
    }
    public boolean sjekkSvar() {
        return brettFrag.sjekkSvar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        brettFrag.lagre();
    }


}
