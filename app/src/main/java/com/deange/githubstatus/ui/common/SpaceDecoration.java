package com.deange.githubstatus.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class SpaceDecoration
    extends RecyclerView.ItemDecoration {

  public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
  public static final int VERTICAL = LinearLayout.VERTICAL;
  private static final String TAG = "SpaceDecoration";
  private static final int[] ATTRS = new int[]{android.R.attr.dividerHeight};

  private int dividerSize;
  private int orientation;

  public SpaceDecoration(Context context, @Orientation int orientation) {
    TypedArray a = context.obtainStyledAttributes(ATTRS);

    if (a.hasValue(0)) {
      dividerSize = a.getDimensionPixelSize(0, 0);

    } else {
      Log.w(TAG, "@android:attr/dividerHeight was not set in the theme used for this "
          + "SpaceDecoration. Please set that attribute or call setDividerSize()");
    }

    a.recycle();
    setOrientation(orientation);
  }

  public void setOrientation(@Orientation int orientation) {
    if (orientation != HORIZONTAL && orientation != VERTICAL) {
      throw new IllegalArgumentException(
          "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
    }
    this.orientation = orientation;
  }

  @Override
  public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
    // No-op
  }

  @Override
  public void getItemOffsets(
      Rect outRect,
      View view,
      RecyclerView parent,
      RecyclerView.State state) {

    int position = parent.getChildAdapterPosition(view);
    int firstPositionSize = (position == 0) ? dividerSize : 0;

    if (dividerSize == 0) {
      outRect.set(0, 0, 0, 0);

    } else {
      if (orientation == VERTICAL) {
        outRect.set(0, firstPositionSize, 0, dividerSize);

      } else {
        outRect.set(firstPositionSize, 0, dividerSize, 0);
      }
    }
  }

  @IntDef({VERTICAL, HORIZONTAL})
  public @interface Orientation {
  }
}
