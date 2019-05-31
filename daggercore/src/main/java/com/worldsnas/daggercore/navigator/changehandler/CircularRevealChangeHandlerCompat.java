package com.worldsnas.daggercore.navigator.changehandler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;

public class CircularRevealChangeHandlerCompat extends CircularRevealChangeHandler {

    @Keep
    public CircularRevealChangeHandlerCompat() { }

    public CircularRevealChangeHandlerCompat(@NonNull View fromView, @NonNull View containerView) {
        super(fromView, containerView);
    }

    public CircularRevealChangeHandlerCompat(int cx, int cy){
        super(cx, cy);
    }

    public CircularRevealChangeHandlerCompat(int cx, int cy, long duration){
        super(cx, cy, duration);
    }

    @Override @NonNull
    protected Animator getAnimator(@NonNull ViewGroup container, View from, View to, boolean isPush, boolean toAddedToContainer) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return super.getAnimator(container, from, to, isPush, toAddedToContainer);
        } else {
            AnimatorSet animator = new AnimatorSet();
            if (to != null) {
                float start = toAddedToContainer ? 0 : to.getAlpha();
                animator.play(ObjectAnimator.ofFloat(to, View.ALPHA, start, 1));
            }

            if (from != null) {
                animator.play(ObjectAnimator.ofFloat(from, View.ALPHA, 0));
            }

            return animator;
        }
    }
}
