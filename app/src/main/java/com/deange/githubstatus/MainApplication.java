package com.deange.githubstatus;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.deange.githubstatus.dagger.BaseAppComponent;
import com.deange.githubstatus.dagger.DaggerAppComponent;
import com.deange.githubstatus.dagger.module.AppModule;
import com.deange.githubstatus.util.FontUtils;

import net.danlew.android.joda.JodaTimeAndroid;


public class MainApplication
    extends Application {

  private static final String TAG = "MainApplication";

  private BaseAppComponent appComponent;

  public static MainApplication get(Context context) {
    return (MainApplication) context.getApplicationContext();
  }

  @Override
  public void onCreate() {
    Log.d(TAG, "onCreate()");
    super.onCreate();

    appComponent = buildAppComponent();
    appComponent.notificationController().register();

    FontUtils.init(this);
    JodaTimeAndroid.init(this);
  }

  BaseAppComponent buildAppComponent() {
    return DaggerAppComponent.builder()
                             .appModule(AppModule.create(this))
                             .build();
  }

  public static <T extends BaseAppComponent> T component(Context context) {
    //noinspection unchecked
    return (T) ((MainApplication) context.getApplicationContext()).appComponent;
  }

}
