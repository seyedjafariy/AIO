package com.worldsnas.daggercore.navigator.changehandler;

import androidx.annotation.Keep;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat;

public class ArcFadeMoveChangeHandlerCompat extends TransitionChangeHandlerCompat {

    @Keep
    public ArcFadeMoveChangeHandlerCompat() {
        super();
    }

    public ArcFadeMoveChangeHandlerCompat(String... transitionNames) {
        super(new ArcFadeMoveChangeHandler(transitionNames), new FadeChangeHandler());
    }

}
