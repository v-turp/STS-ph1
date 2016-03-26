// Generated code from Butter Knife. Do not modify!
package com.score.sts;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContactFrag$$ViewBinder<T extends com.score.sts.ContactFrag> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296377, "field 'rvContact'");
    target.rvContact = finder.castView(view, 2131296377, "field 'rvContact'");
  }

  @Override public void unbind(T target) {
    target.rvContact = null;
  }
}
