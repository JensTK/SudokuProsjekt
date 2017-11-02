package jens.sudokuprosjekt;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Jens on 30.10.2017.
 */

public class LagActivity extends Activity {
    private BrettFragment brettFrag;

    @Override
    public void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        setContentView(R.layout.activity_sudoku);

        Brett brettet = new Brett(this);
        brettFrag = BrettManager.lagBrettFragment(this, brettet);

        FragmentManager fgm = getFragmentManager();
        FragmentTransaction tran = fgm.beginTransaction();
        tran.replace(R.id.knappView, new LagKnappFragment());
        tran.commit();
    }

    public BrettFragment getBrettFrag() {
        return brettFrag;
    }
}
