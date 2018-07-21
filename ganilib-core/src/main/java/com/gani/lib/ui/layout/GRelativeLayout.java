package com.gani.lib.ui.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;

import com.gani.lib.ui.view.ViewHelper;

public class GRelativeLayout<T extends GRelativeLayout> extends RelativeLayout {
  private ViewHelper helper;

  public GRelativeLayout(Context context) {
    super(context);

    init();
  }

  private void init() {
    this.helper = new ViewHelper(this);
  }

  private T self() {
    return (T) this;
  }

  public T width(Integer width) {
    helper.width(width);
    return self();
  }

  public T height(Integer height) {
    helper.height(height);
    return self();
  }

  public T padding(Integer left, Integer top, Integer right, Integer bottom) {
    helper.padding(left, top, right, bottom);
    return self();
  }

  public T margin(Integer left, Integer top, Integer right, Integer bottom) {
    helper.margin(left, top, right, bottom);
    return self();
  }

  public T gravity(int gravity) {
    setGravity(gravity);
    return self();
  }

  public T bgColor(int color) {
    helper.bgColor(color);
    return self();
  }

  public T bgColor(String color) {
    helper.bgColor(color);
    return self();
  }

  public T bg(Drawable drawable) {
    helper.bg(drawable);
    return self();
  }

  public T append(View child) {
    addView(child);
    return self();
  }
}
