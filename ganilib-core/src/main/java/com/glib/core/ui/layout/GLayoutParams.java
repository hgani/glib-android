package com.glib.core.ui.layout;

import android.widget.LinearLayout;

import com.glib.core.ui.style.Length;

public class GLayoutParams<T extends GLayoutParams> extends LinearLayout.LayoutParams {
  public GLayoutParams() {
    super(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
  }

  private T self() {
    return (T) this;
  }

  public T width(int width) {
    this.width = Length.INSTANCE.dpToPx(width);
    return self();
  }

  public T height(int height) {
    this.height = Length.INSTANCE.dpToPx(height);
    return self();
  }

  public T margins(int left, int top, int right, int bottom){
    super.setMargins(Length.INSTANCE.dpToPx(left), Length.INSTANCE.dpToPx(top), Length.INSTANCE.dpToPx(right), Length.INSTANCE.dpToPx(bottom));
    return self();
  }
}
