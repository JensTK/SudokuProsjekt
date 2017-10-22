package jens.sudokuprosjekt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Jens on 22.10.2017.
 */

public class KvadratLayout extends LinearLayout {
    public KvadratLayout(Context cont) {
        super(cont);
    }
    public KvadratLayout(Context cont, AttributeSet atr) {
        super(cont, atr);
    }

    @Override
    protected void onMeasure(int breddeSpec, int høydeSpec) {
        int bredde = MeasureSpec.getSize(breddeSpec);
        int høyde = MeasureSpec.getSize(høydeSpec);
        if (bredde <= høyde) {
            super.onMeasure(breddeSpec, breddeSpec);
        }
        else {
            super.onMeasure(høydeSpec, høydeSpec);
        }
    }
}
