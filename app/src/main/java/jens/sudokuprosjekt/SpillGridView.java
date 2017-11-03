package jens.sudokuprosjekt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * Created by Jens on 03.11.2017.
 */

public class SpillGridView extends GridView {
    private tallAdapter adapter;

    public SpillGridView(Context context) {
        super(context);
    }
    public SpillGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int breddeSpec, int høydeSpec) {
        int bredde = MeasureSpec.getSize(breddeSpec);
        int høyde = MeasureSpec.getSize(høydeSpec);
        if (bredde <= høyde) {
            super.onMeasure(breddeSpec, breddeSpec - 10);
        }
        else {
            super.onMeasure(høydeSpec, høydeSpec - 10);
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = (tallAdapter) adapter;
    }

    @Override
    public ListAdapter getAdapter() {
        return adapter;
    }
}
