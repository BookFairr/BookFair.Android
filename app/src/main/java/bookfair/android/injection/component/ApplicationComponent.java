package bookfair.android.injection.component;


import javax.inject.Singleton;

import bookfair.android.db.BookFairRepository;
import bookfair.android.injection.module.ApplicationModule;
import bookfair.android.ui.activities.BaseActivity;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject (BaseActivity baseActivity);
    void inject (BookFairRepository bookFairRepository);

}
