package com.example.androidapp.home;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.bluelinelabs.conductor.Controller;
import com.example.androidapp.R;
import com.example.androidapp.base.BaseActivity;
import com.example.androidapp.trending.TrendingReposController;

public class MainActivity extends BaseActivity {


    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected Controller initialScreen() {
        return new TrendingReposController();
    }
}
