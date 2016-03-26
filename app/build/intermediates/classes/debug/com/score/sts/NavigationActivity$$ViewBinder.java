// Generated code from Butter Knife. Do not modify!
package com.score.sts;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NavigationActivity$$ViewBinder<T extends com.score.sts.NavigationActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296424, "field 'vpNavigation'");
    target.vpNavigation = finder.castView(view, 2131296424, "field 'vpNavigation'");
  }

  @Override public void unbind(T target) {
    target.vpNavigation = null;
  }
}
