package com.example.androidapp.base;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.example.androidapp.R;
import com.example.androidapp.UI.ScreenNavigator;
import com.example.androidapp.di.Injector;
import com.example.androidapp.di.ScreenInjector;

import java.util.UUID;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {
    private static String INSTANCE_ID_KEY = "instance_id";

    @Inject
    ScreenInjector screenInjector;

    @Inject
    ScreenNavigator screenNavigator;

    private String instanceId;

    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            instanceId = savedInstanceState.getString(INSTANCE_ID_KEY);
        } else {
            instanceId = UUID.randomUUID().toString();
        }
        Injector.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        ViewGroup screenContainer = findViewById(R.id.screen_container);
        if (screenContainer == null) {
            throw new NullPointerException("Activity must contain the view with id screen container");
        }
        router = Conductor.attachRouter(this, screenContainer, savedInstanceState);
        screenNavigator.initWithRouter(router, initialScreen());
        monitorBackStack();
    }

    @LayoutRes
    protected abstract int layoutRes();

    protected abstract Controller initialScreen();

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_ID_KEY, instanceId);
    }

    public String getInstanceId() {
        return instanceId;
    }

    @Override
    public void onBackPressed() {
        if (!screenNavigator.pop())
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenNavigator.clear();
        if (isFinishing()) {
            Injector.clearComponent(this);
        }
    }

    public ScreenInjector getScreenInjector() {
        return screenInjector;
    }

    private void monitorBackStack() {
        router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener() {
            @Override
            public void onChangeStarted(@Nullable Controller to,
                                        @Nullable Controller from, boolean isPush,
                                        @NonNull ViewGroup container, @NonNull
                                                ControllerChangeHandler handler) {


            }

            @Override
            public void onChangeCompleted(@Nullable Controller to,
                                          @Nullable Controller from,
                                          boolean isPush,
                                          @NonNull ViewGroup container,
                                          @NonNull ControllerChangeHandler handler) {
                if (!isPush && from != null) {
                    Injector.clearComponent(from);
                }

            }
        });
    }
}
