package com.example.androidapp.UI;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.androidapp.di.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class DefaultScreenNavigator implements ScreenNavigator {
    private Router router;

    @Inject
    public DefaultScreenNavigator() {
    }

    @Override
    public void initWithRouter(Router router, Controller rootScreen) {
            this.router = router;
            if(!router.hasRootController()){
                router.setRoot(RouterTransaction.with(rootScreen));
            }
    }

    @Override
    public boolean pop() {
        return router != null && router.handleBack();
    }

    @Override
    public void clear() {
        router = null;
    }
}
