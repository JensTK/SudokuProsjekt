package jens.sudokuprosjekt;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Jens on 22.10.2017.
 */

public class SudokuActivity extends MainActivity {
    private BrettFragment brettFrag;

    @Override
    public void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        setContentView(R.layout.activity_sudoku);
        FragmentManager fgm = getFragmentManager();
        FragmentTransaction tran = fgm.beginTransaction();
        tran.replace(R.id.knappView, new KnappFragment());
        tran.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        brettFrag = BrettManager.lagBrettFragment(this);
    }

    public BrettFragment getBrettFrag() {
        return brettFrag;
    }
}
