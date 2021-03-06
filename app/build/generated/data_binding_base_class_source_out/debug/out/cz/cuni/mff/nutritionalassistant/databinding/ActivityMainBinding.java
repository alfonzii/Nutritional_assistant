// Generated by view binder compiler. Do not edit!
package cz.cuni.mff.nutritionalassistant.databinding;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.viewbinding.ViewBinding;
import cz.cuni.mff.nutritionalassistant.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final ContentMainBinding content;

  @NonNull
  public final FloatingActionButton fab;

  @NonNull
  public final Toolbar toolbar;

  private ActivityMainBinding(@NonNull CoordinatorLayout rootView,
      @NonNull ContentMainBinding content, @NonNull FloatingActionButton fab,
      @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.content = content;
    this.fab = fab;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      View content = rootView.findViewById(R.id.content);
      if (content == null) {
        missingId = "content";
        break missingId;
      }
      ContentMainBinding contentBinding = ContentMainBinding.bind(content);
      FloatingActionButton fab = rootView.findViewById(R.id.fab);
      if (fab == null) {
        missingId = "fab";
        break missingId;
      }
      Toolbar toolbar = rootView.findViewById(R.id.toolbar);
      if (toolbar == null) {
        missingId = "toolbar";
        break missingId;
      }
      return new ActivityMainBinding((CoordinatorLayout) rootView, contentBinding, fab, toolbar);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
