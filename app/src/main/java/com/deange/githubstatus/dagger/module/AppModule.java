package com.deange.githubstatus.dagger.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

  private final Application application;

  public static AppModule create(Application application) {
    return new AppModule(application);
  }

  private AppModule(Application application) {
    this.application = application;
  }

  @Provides
  public Context providesApplicationContext() {
    return application.getApplicationContext();
  }
}
