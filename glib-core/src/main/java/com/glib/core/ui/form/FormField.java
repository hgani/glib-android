package com.glib.core.ui.form;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glib.core.ui.layout.GLinearLayout;
import com.glib.core.ui.panel.GVerticalPanel;
import com.glib.core.ui.view.GTextView;

//import com.clsreview.clsreview.R;

public abstract class FormField extends GLinearLayout<FormField> {
  private TextView label;
  private View editView;

  public FormField(Context context, String labelText, GTextView.Spec labelSpec) {
    super(context);

    horizontal();
    setGravity(Gravity.CENTER_VERTICAL);
    width(ViewGroup.LayoutParams.MATCH_PARENT).padding(0, 10, 0, 10);

    this.label = createLabel(context).specs(labelSpec);
    this.editView = createEditView(context);

    GLinearLayout editLayout = new GVerticalPanel(context);
    editLayout.addView(editView);

    setWeightOf(label, 10);
    setWeightOf(editLayout, 5);

//    label.setBackgroundColor(Color.RED);
//    editLayout.setBackgroundColor(Color.BLUE);

    addView(label);
    addView(editLayout);

//    setBackground(Ui.drawable(R.drawable.border_set));

    label(labelText);
  }

  public FormField label(String text) {
    label.setText(text);
    return this;
  }

  private GTextView createLabel(Context context) {
    return new GTextView(context);
  }

  protected abstract View createEditView(Context context);

  TextView getLabel() {
    return label;
  }
}
