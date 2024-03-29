package com.example.androidapp.home;


import com.example.androidapp.UI.NavigationModule;
import com.example.androidapp.di.ActivityScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {
        MainScreenBindingModule.class,
        NavigationModule.class,
})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
        @Override
        public void seedInstance(MainActivity instance) {

        }
    }
}
