package com.example.androidapp.home;


import com.bluelinelabs.conductor.Controller;
import com.example.androidapp.di.ControllerKey;
import com.example.androidapp.trending.TrendingReposComponent;
import com.example.androidapp.trending.TrendingReposController;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = TrendingReposComponent.class)
public abstract class MainScreenBindingModule {

    @Binds
    @IntoMap
    @ControllerKey(TrendingReposController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindTrendingReposInjector(TrendingReposComponent.Builder builder);

}
