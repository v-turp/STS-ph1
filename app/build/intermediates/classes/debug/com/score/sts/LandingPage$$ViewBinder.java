// Generated code from Butter Knife. Do not modify!
package com.score.sts;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LandingPage$$ViewBinder<T extends com.score.sts.LandingPage> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296402, "field 'rvLandingDrawer'");
    target.rvLandingDrawer = finder.castView(view, 2131296402, "field 'rvLandingDrawer'");
    view = finder.findRequiredView(source, 2131296399, "field 'etUsername'");
    target.etUsername = finder.castView(view, 2131296399, "field 'etUsername'");
    view = finder.findRequiredView(source, 2131296400, "field 'etPassword'");
    target.etPassword = finder.castView(view, 2131296400, "field 'etPassword'");
    view = finder.findRequiredView(source, 2131296398, "field 'ivLandingLogo'");
    target.ivLandingLogo = finder.castView(view, 2131296398, "field 'ivLandingLogo'");
    view = finder.findRequiredView(source, 2131296401, "method 'loginInUser'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.loginInUser();
        }
      });
  }

  @Override public void unbind(T target) {
    target.rvLandingDrawer = null;
    target.etUsername = null;
    target.etPassword = null;
    target.ivLandingLogo = null;
  }
}
