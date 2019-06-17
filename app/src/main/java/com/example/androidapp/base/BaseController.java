package com.example.androidapp.base;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bluelinelabs.conductor.Controller;
import com.example.androidapp.di.Injector;

public abstract class BaseController extends Controller {
    private boolean inject = false;

    @Override
    protected void onContextAvailable(@NonNull Context context) {
        if(!inject){
            Injector.inject(this);
            inject = true;
        }
        super.onContextAvailable(context);
    }
}
