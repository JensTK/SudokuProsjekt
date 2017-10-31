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
    private Brett brettet;

    @Override
    public void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        setContentView(R.layout.activity_sudoku);

        FragmentManager fgm = getFragmentManager();
        brettFrag = new BrettFragment();
        Bundle fragBun = new Bundle();
        fragBun.putBoolean(VanskeligFrag.newNavn, true);
        fragBun.putInt(VanskeligFrag.vanskNavn, 0);
        fragBun.putBoolean(VanskeligFrag.lagNavn, true);
        brettFrag.setArguments(fragBun);
        FragmentTransaction tran = fgm.beginTransaction();
        tran.replace(R.id.brettView, brettFrag);
        tran.replace(R.id.knappView, new LagKnappFragment());
        tran.commit();

        getIntent().putExtra(VanskeligFrag.newNavn, false);
    }
    public boolean sjekkSvar() {
        return brettet.sjekkSvar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BrettManager.lagreTilMinne(brettFrag.getActivity(), brettet);
    }
}
