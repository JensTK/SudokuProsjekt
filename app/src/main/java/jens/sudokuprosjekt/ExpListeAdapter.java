package jens.sudokuprosjekt;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Created by Jens on 03.11.2017.
 */

public class ExpListeAdapter extends BaseExpandableListAdapter {
    private String[][] brettNavna;
    private String[] vanskeligNavn;
    private Activity cont;
    private boolean[][] selected;

    public ExpListeAdapter(Activity cont, String[][] brettNavna, String[] vanskeligNavn) {
        this.brettNavna = brettNavna;
        this.vanskeligNavn = vanskeligNavn;
        this.cont = cont;
        selected = new boolean[brettNavna.length][];
        for (int i = 0; i < brettNavna.length; i++) {
            selected[i] = new boolean[brettNavna[i].length];
        }
    }
    @Override
    public int getGroupCount() {
        return brettNavna.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return brettNavna[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return brettNavna[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return brettNavna[i][i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public boolean[][] getSelected() {
        return selected;
    }

    @Override
    public View getGroupView(int i, final boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = new TextView(cont);
        }
        TextView ret = (TextView) view;
        ret.setText(vanskeligNavn[i]);
        ret.setTypeface(null, Typeface.BOLD);

        ret.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return ret;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = new TextView(cont);
        }
        final TextView ret = (TextView) view;
        ret.setText(brettNavna[i][i1]);
        ret.setPadding(40, 5, 5, 5);
        ret.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        ret.setBackgroundColor(Color.TRANSPARENT);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int farge = ((ColorDrawable)ret.getBackground()).getColor();
                if (farge == Color.TRANSPARENT) {
                    ret.setBackgroundColor(Color.LTGRAY);
                    selected[i][i1] = true;
                }
                else if (farge == Color.LTGRAY) {
                    ret.setBackgroundColor(Color.TRANSPARENT);
                    selected[i][i1] = false;
                }
                BrettManageActivity act = (BrettManageActivity)cont;
                act.enableKnapp();
            }
        });

        return ret;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        selected = new boolean[brettNavna.length][];
        for (int i = 0; i < brettNavna.length; i++) {
            selected[i] = new boolean[brettNavna[i].length];
        }
        BrettManageActivity act = (BrettManageActivity)cont;
        act.enableKnapp();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
