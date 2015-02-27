package com.tonyw.sampleapps.palettecolorextraction;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

/**
 * A transition between Activities that circularly reveals views when appearing (API 21+).
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CircularRevealTransition extends Visibility {
    /** The x-coordinate of the revealing circle. */
    private static int x;
    /** The y-coordinate of the revealing circle. */
    private static int y;

    public CircularRevealTransition() {
    }

    public CircularRevealTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set the start position of a the animation by passing in a view where the revealing circle
     * will originate.
     */
    public static void setStartPosition(Activity activity, View v) {
        Rect rect = new Rect();
        v.getGlobalVisibleRect(rect);
        x = rect.centerX();
        y = rect.centerY()
                // Subtract off height of action bar and status bar, which apparently isn't
                // done when calculating the global visible rect... (status bar height=25dp)
                - activity.getActionBar().getHeight()
                - (int) Math.ceil(25 * activity.getResources().getDisplayMetrics().density);
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues,
                             TransitionValues endValues) {
        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        return ViewAnimationUtils.createCircularReveal(view, x, y, 0, finalRadius);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, final View view, TransitionValues startValues,
                                TransitionValues endValues) {
        // get the initial radius for the clipping circle
        int initialRadius = view.getWidth();

        // create the animation (the final radius is zero)
        return ViewAnimationUtils.createCircularReveal(view, x, y, initialRadius, 0);
    }
}
