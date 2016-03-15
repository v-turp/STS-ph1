// Generated code from Butter Knife. Do not modify!
package com.score.sts;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContactRecyclerView$$ViewBinder<T extends com.score.sts.ContactRecyclerView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296368, "field 'rvContacts'");
    target.rvContacts = finder.castView(view, 2131296368, "field 'rvContacts'");
  }

  @Override public void unbind(T target) {
    target.rvContacts = null;
  }
}
