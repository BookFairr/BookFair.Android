package bookfair.android.injection.module;

import android.app.Application;

import javax.inject.Singleton;

import bookfair.android.BuildConfig;
import bookfair.android.api.BookFairApiService;
import bookfair.android.api.RetrofitHelper;
import bookfair.android.core.PreferenceManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    PreferenceManager providePreferenceManager() {
        return new PreferenceManager(app);
    }


    @Singleton
    @Provides
    BookFairApiService provideBookFairApiService() {
        return new RetrofitHelper(app).bookFairApiService();
    }

}
