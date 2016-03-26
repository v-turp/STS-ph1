// Generated code from Butter Knife. Do not modify!
package com.score.sts;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContactRecyclerViewAdapter$ContactViewHolder$$ViewBinder<T extends com.score.sts.ContactRecyclerViewAdapter.ContactViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131296389, null);
    target.tvNavOption = finder.castView(view, 2131296389, "field 'tvNavOption'");
    view = finder.findOptionalView(source, 2131296388, null);
    target.ivNavIcon = finder.castView(view, 2131296388, "field 'ivNavIcon'");
    view = finder.findOptionalView(source, 2131296394, null);
    target.ivContactPhoto = finder.castView(view, 2131296394, "field 'ivContactPhoto'");
    view = finder.findOptionalView(source, 2131296395, null);
    target.ivMusic = finder.castView(view, 2131296395, "field 'ivMusic'");
    view = finder.findOptionalView(source, 2131296396, null);
    target.ivVideo = finder.castView(view, 2131296396, "field 'ivVideo'");
    view = finder.findOptionalView(source, 2131296385, null);
    target.ivContact = finder.castView(view, 2131296385, "field 'ivContact'");
    view = finder.findOptionalView(source, 2131296397, null);
    target.ivBio = finder.castView(view, 2131296397, "field 'ivBio'");
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
