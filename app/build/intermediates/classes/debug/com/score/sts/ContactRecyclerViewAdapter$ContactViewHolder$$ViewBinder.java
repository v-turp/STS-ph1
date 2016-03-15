// Generated code from Butter Knife. Do not modify!
package com.score.sts;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContactRecyclerViewAdapter$ContactViewHolder$$ViewBinder<T extends com.score.sts.ContactRecyclerViewAdapter.ContactViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131296380, null);
    target.tvNavOption = finder.castView(view, 2131296380, "field 'tvNavOption'");
    view = finder.findOptionalView(source, 2131296379, null);
    target.ivNavIcon = finder.castView(view, 2131296379, "field 'ivNavIcon'");
    view = finder.findOptionalView(source, 2131296385, null);
    target.ivContactPhoto = finder.castView(view, 2131296385, "field 'ivContactPhoto'");
    view = finder.findOptionalView(source, 2131296386, null);
    target.ivMusic = finder.castView(view, 2131296386, "field 'ivMusic'");
    view = finder.findOptionalView(source, 2131296387, null);
    target.ivVideo = finder.castView(view, 2131296387, "field 'ivVideo'");
    view = finder.findOptionalView(source, 2131296376, null);
    target.ivContact = finder.castView(view, 2131296376, "field 'ivContact'");
    view = finder.findOptionalView(source, 2131296388, null);
    target.ivBio = finder.castView(view, 2131296388, "field 'ivBio'");
  }

  @Override public void unbind(T target) {
    target.tvNavOption = null;
    target.ivNavIcon = null;
    target.ivContactPhoto = null;
    target.ivMusic = null;
    target.ivVideo = null;
    target.ivContact = null;
    target.ivBio = null;
  }
}
