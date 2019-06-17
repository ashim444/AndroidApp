package com.example.androidapp.di;

import android.app.Activity;
import android.content.Context;

import com.example.androidapp.base.BaseActivity;
import com.example.androidapp.base.MyApplication;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjector;

public class ActivityInjector {

    private final Map<String, AndroidInjector<? extends Activity>> cache = new HashMap<>();
    private Map<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>> activityInjectors;

    @Inject
    ActivityInjector(Map<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>> activityInjectors) {

        this.activityInjectors = activityInjectors;
    }

    static ActivityInjector get(Context context) {
        return ((MyApplication) context.getApplicationContext()).getActivityInjector();
    }

    void inject(Activity activity) {
        if (!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Activity must extends BaseActivity");
        }

        String instanceId = ((BaseActivity) activity).getInstanceId();
        if (cache.containsKey(instanceId)) {
            ((AndroidInjector<Activity>) cache.get(instanceId)).inject(activity);
            return;
        }

        AndroidInjector.Factory<Activity> injectorFactory = (AndroidInjector.Factory<Activity>) activityInjectors.get(activity.getClass()).get();
        AndroidInjector<Activity> injector = injectorFactory.create(activity);
        cache.put(instanceId, injector);
        injector.inject(activity);
    }

    void clear(Activity activity) {
        if (!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Activity must extends BaseActivity");
        }
        cache.remove(((BaseActivity) activity).getInstanceId());
    }
}
