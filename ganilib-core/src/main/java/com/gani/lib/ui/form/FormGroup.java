package com.gani.lib.ui.form;

import android.content.Context;

import com.gani.lib.ui.layout.GLinearLayout;

public class FormGroup extends GLinearLayout {
  private Float labelFontSize;

  public FormGroup(Context context) {
    super(context);

    vertical().width(LayoutParams.MATCH_PARENT);
  }

  public FormGroup labelFontSize(float labelFontSize) {
    this.labelFontSize = labelFontSize;
    return this;
  }

  public void add(FormField field) {
    if (labelFontSize != null) {
      field.getLabel().setTextSize(labelFontSize);
    }
    addView(field);
  }
}
