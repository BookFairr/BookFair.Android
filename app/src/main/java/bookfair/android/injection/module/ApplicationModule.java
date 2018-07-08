package bookfair.android.injection.module;

import android.app.Application;

import dagger.Module;

@Module
public class ApplicationModule {
    private Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

}
