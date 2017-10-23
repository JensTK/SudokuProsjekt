package jens.sudokuprosjekt;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jens on 22.10.2017.
 */

public class tallAdapter extends BaseAdapter {
    private Context cont;
    private int[] tallene;
    private boolean[] disabled;

    public tallAdapter(Context cont) {
        this.cont = cont;
    }
    public tallAdapter(Context cont, int[] tallene, boolean[] disabled) {
        this.cont = cont;
        this.tallene = tallene;
        this.disabled = disabled;
    }

    public int[] getTallene() {
        return tallene;
    }
    public boolean[] getDisabled() {
        return disabled;
    }

    @Override
    public int getCount() {
        return tallene.length;
    }

    @Override
    public Object getItem(int i) {
        return tallene[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        EditText edit;
        if (view == null) {
            edit = new EditText(cont);
            /*if (tallene[i] >= 0) {
                edit.setEnabled(false);
                edit.setFocusable(false);
            }*/
        }
        else {
            edit = (EditText)view;
        }
        if (tallene[i] >= 0) {
            edit.setText(Integer.toString(tallene[i]));
        }
        else {
            edit.getText().clear();
        }
        if (disabled[i]) {
            edit.setEnabled(false);
            edit.setFocusable(false);
        }
        edit.setId(i);
        edit.setBackgroundColor(Color.TRANSPARENT);
        edit.setGravity(Gravity.CENTER);
        edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        InputFilter[] filts = { new InputFilter.LengthFilter(1) };
        edit.setFilters(filts);
        edit.setSelectAllOnFocus(true);
        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        edit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edit.setPadding(0, 0, 0, 0);

        final EditText editfin = edit;

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String text = editfin.getText().toString();
                if (! text.equals("")) {
                    int tall = Integer.parseInt(editfin.getText().toString());
                    //Log.i("tagg", "Endret: " + tall);
                    tallene[i] = tall;
                }
                else {
                    tallene[i] = -1;
                }
            }
        });

        return editfin;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
