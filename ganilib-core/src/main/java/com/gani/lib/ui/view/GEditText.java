package com.gani.lib.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class GEditText<T extends GEditText> extends AppCompatEditText implements IView {
  private ViewHelper helper;

  public GEditText(Context context) {
    super(context);
    init();
  }

  public GEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    this.helper = new ViewHelper(this);
  }

  private T self() {
    return (T) this;
  }

  @Override
  public GEditText padding(Integer left, Integer top, Integer right, Integer bottom) {
    helper.padding(left, top, right, bottom);
    return self();
  }

  @Override
  public GEditText margin(Integer left, Integer top, Integer right, Integer bottom) {
    helper.margin(left, top, right, bottom);
    return self();
  }
}
