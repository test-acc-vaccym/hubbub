package com.deange.githubstatus.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.util.TypedValue;
import android.view.View;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public final class ViewUtils {

    private ViewUtils() {
        throw new AssertionError();
    }

    public static void setVisibility(final View view, final boolean visible) {
        view.setVisibility(visible ? VISIBLE : GONE);
    }

    public static int getAttr(@AttrRes final int attr, final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.resourceId;
    }

    public static Drawable getDrawable(@AttrRes final int attr, final Context context) {
        return context.getDrawable(getAttr(attr, context));
    }

    public static String getString(@AttrRes final int attr, final Context context) {
        return context.getString(getAttr(attr, context));
    }

    public static int getInteger(@AttrRes final int attr, final Context context) {
        return context.getResources().getInteger(getAttr(attr, context));
    }

    public static boolean getBoolean(@AttrRes final int attr, final Context context) {
        return context.getResources().getBoolean(getAttr(attr, context));
    }

    public static int getColor(@AttrRes final int attr, final Context context) {
        return context.getResources().getColor(getAttr(attr, context));
    }

}
