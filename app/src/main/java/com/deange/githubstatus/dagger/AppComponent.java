package com.deange.githubstatus.dagger;

import com.deange.githubstatus.dagger.module.AppModule;
import com.deange.githubstatus.dagger.module.GsonModule;
import com.deange.githubstatus.dagger.module.OkHttpModule;
import com.deange.githubstatus.dagger.module.RetrofitModule;
import com.deange.githubstatus.dagger.module.SharedPrefsModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
    AppModule.class,
    GsonModule.class,
    OkHttpModule.class,
    RetrofitModule.class,
    SharedPrefsModule.class,
})
public interface AppComponent
    extends BaseAppComponent {

  @Component.Builder
  interface Builder {
    Builder appModule(AppModule appModule);
    AppComponent build();
  }

}
