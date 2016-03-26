// Generated code from Butter Knife. Do not modify!
package com.score.sts;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContactRecyclerViewAdapter$ContactViewHolder$$ViewBinder<T extends com.score.sts.ContactRecyclerViewAdapter.ContactViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131296387, null);
    target.tvNavOption = finder.castView(view, 2131296387, "field 'tvNavOption'");
    view = finder.findOptionalView(source, 2131296386, null);
    target.ivNavIcon = finder.castView(view, 2131296386, "field 'ivNavIcon'");
    view = finder.findOptionalView(source, 2131296392, null);
    target.ivContactPhoto = finder.castView(view, 2131296392, "field 'ivContactPhoto'");
    view = finder.findOptionalView(source, 2131296393, null);
    target.ivMusic = finder.castView(view, 2131296393, "field 'ivMusic'");
    view = finder.findOptionalView(source, 2131296394, null);
    target.ivVideo = finder.castView(view, 2131296394, "field 'ivVideo'");
    view = finder.findOptionalView(source, 2131296383, null);
    target.ivContact = finder.castView(view, 2131296383, "field 'ivContact'");
    view = finder.findOptionalView(source, 2131296395, null);
    target.ivBio = finder.castView(view, 2131296395, "field 'ivBio'");
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
