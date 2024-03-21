package com.moutamid.antitiktok;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fxn.stash.Stash;

public class MyApp extends Application implements Application.ActivityLifecycleCallbacks {
    private boolean isAppInBackground = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        isAppInBackground = false;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        isAppInBackground = true;
    }

    public boolean isAppInBackground() {
        return isAppInBackground;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        isAppInBackground = true;
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        isAppInBackground = true;
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        isAppInBackground = true;
    }
}
