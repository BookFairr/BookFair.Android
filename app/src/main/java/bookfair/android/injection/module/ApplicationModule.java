package bookfair.android.injection.module;

import android.app.Application;

import javax.inject.Singleton;

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

}
