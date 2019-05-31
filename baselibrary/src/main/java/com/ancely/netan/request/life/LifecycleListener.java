package com.ancely.netan.request.life;

/**
 * An interface for listener to {@link android.app.Fragment} and {@link android.app.Activity}
 * lifecycle events.
 */
public interface LifecycleListener {

    void onStart();

    void onStop();

    void onDestroy();
}
