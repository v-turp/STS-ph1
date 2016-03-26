// Generated code from Butter Knife. Do not modify!
package com.score.sts;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ManageContacts$$ViewBinder<T extends com.score.sts.ManageContacts> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296403, "field 'etFirstName'");
    target.etFirstName = finder.castView(view, 2131296403, "field 'etFirstName'");
    view = finder.findRequiredView(source, 2131296404, "field 'etLastName'");
    target.etLastName = finder.castView(view, 2131296404, "field 'etLastName'");
    view = finder.findRequiredView(source, 2131296405, "field 'etEmail'");
    target.etEmail = finder.castView(view, 2131296405, "field 'etEmail'");
    view = finder.findRequiredView(source, 2131296407, "field 'etFirstNameSearch'");
    target.etFirstNameSearch = finder.castView(view, 2131296407, "field 'etFirstNameSearch'");
    view = finder.findRequiredView(source, 2131296408, "field 'etLastNameSearch'");
    target.etLastNameSearch = finder.castView(view, 2131296408, "field 'etLastNameSearch'");
    view = finder.findRequiredView(source, 2131296409, "field 'etEmailSearch'");
    target.etEmailSearch = finder.castView(view, 2131296409, "field 'etEmailSearch'");
    view = finder.findRequiredView(source, 2131296411, "field 'etFirstNameUpdate'");
    target.etFirstNameUpdate = finder.castView(view, 2131296411, "field 'etFirstNameUpdate'");
    view = finder.findRequiredView(source, 2131296412, "field 'etLastNameUpdate'");
    target.etLastNameUpdate = finder.castView(view, 2131296412, "field 'etLastNameUpdate'");
    view = finder.findRequiredView(source, 2131296413, "field 'etEmailUpdate'");
    target.etEmailUpdate = finder.castView(view, 2131296413, "field 'etEmailUpdate'");
    view = finder.findRequiredView(source, 2131296415, "field 'etFirstNameDelete'");
    target.etFirstNameDelete = finder.castView(view, 2131296415, "field 'etFirstNameDelete'");
    view = finder.findRequiredView(source, 2131296416, "field 'etLastNameDelete'");
    target.etLastNameDelete = finder.castView(view, 2131296416, "field 'etLastNameDelete'");
    view = finder.findRequiredView(source, 2131296417, "field 'etEmailDelete'");
    target.etEmailDelete = finder.castView(view, 2131296417, "field 'etEmailDelete'");
    view = finder.findRequiredView(source, 2131296422, "field 'ivNetworkImage'");
    target.ivNetworkImage = finder.castView(view, 2131296422, "field 'ivNetworkImage'");
    view = finder.findRequiredView(source, 2131296406, "method 'saveContact'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.saveContact();
        }
      });
    view = finder.findRequiredView(source, 2131296410, "method 'searchContacts'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.searchContacts();
        }
      });
    view = finder.findRequiredView(source, 2131296414, "method 'updateContact'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.updateContact();
        }
      });
    view = finder.findRequiredView(source, 2131296418, "method 'deleteContact'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.deleteContact();
        }
      });
    view = finder.findRequiredView(source, 2131296419, "method 'showContacts'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.showContacts();
        }
      });
    view = finder.findRequiredView(source, 2131296420, "method 'uploadContent'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.uploadContent(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.etFirstName = null;
    target.etLastName = null;
    target.etEmail = null;
    target.etFirstNameSearch = null;
    target.etLastNameSearch = null;
    target.etEmailSearch = null;
    target.etFirstNameUpdate = null;
    target.etLastNameUpdate = null;
    target.etEmailUpdate = null;
    target.etFirstNameDelete = null;
    target.etLastNameDelete = null;
    target.etEmailDelete = null;
    target.ivNetworkImage = null;
  }
}
