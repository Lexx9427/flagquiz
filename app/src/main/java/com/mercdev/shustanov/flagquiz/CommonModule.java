package com.mercdev.shustanov.flagquiz;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;

import com.mercdev.shustanov.flagquiz.view.activity.GameScreenActivity;
import com.mercdev.shustanov.flagquiz.view.activity.MainActivity;
import com.mercdev.shustanov.flagquiz.view.activity.ScoresActivity;
import com.mercdev.shustanov.flagquiz.view.dialog.CongratulationsActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * alexander.shustanov on 10/13/2014.
 */
@Module(injects = {GameScreenActivity.class, MainActivity.class, CongratulationsActivity.class, ScoresActivity.class})
public class CommonModule {

    private final FlagQuizApp app;

    public CommonModule(FlagQuizApp app) {
        this.app = app;
    }

    @Provides
    Context provideContext() {
        return app;
    }

    @Provides
    @Named("db_version")
    int provideDbVersion() {
        try {
            return app.getPackageManager().getApplicationInfo(app.getPackageName(), PackageManager.GET_META_DATA).metaData.getInt("db_version", 1);
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    @Provides
    @Named("db_name")
    String provideDbName() {
        try {
            return app.getPackageManager().getApplicationInfo(app.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("db_name");
        } catch (PackageManager.NameNotFoundException e) {
            return "scores";
        }
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(app);
    }
}
