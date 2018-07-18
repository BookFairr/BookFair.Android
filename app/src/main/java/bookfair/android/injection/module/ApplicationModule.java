package bookfair.android.injection.module;

import android.app.Application;

import javax.inject.Singleton;

import bookfair.android.BuildConfig;
import bookfair.android.api.BookFairApiService;
import bookfair.android.api.RetrofitHelper;
import bookfair.android.core.PreferenceManager;
import bookfair.android.db.BookFairRepository;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

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

    RealmConfiguration provideRealmConfiguration () {

        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded();
        }
        return builder.build();
    }

    @Provides
    Realm provideRealm () {
        return Realm.getInstance(provideRealmConfiguration());
    }

    @Provides
    BookFairRepository provideBookFairRepository () {
        return new BookFairRepository(app);
    }

    @Singleton
    @Provides
    BookFairApiService provideBookFairApiService() {
        return new RetrofitHelper(app).bookFairApiService();
    }

}
