package bookfair.android.injection.component;


import javax.inject.Singleton;

import bookfair.android.api.RetrofitHelper;
import bookfair.android.injection.module.ApplicationModule;
import bookfair.android.ui.activities.BaseActivity;
import bookfair.android.ui.activities.LoginActivity;
import bookfair.android.ui.activities.MainActivity;
import bookfair.android.ui.activities.SignUpActivity;
import bookfair.android.ui.activities.WelcomeActivity;
import bookfair.android.ui.fragments.ProfileFragment;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject (BaseActivity baseActivity);
    void inject (WelcomeActivity welcomeActivity);
    void inject (LoginActivity loginActivity);
    void inject (SignUpActivity signUpActivity);
    void inject (RetrofitHelper retrofitHelper);
    void inject (MainActivity mainActivity);
    void inject (ProfileFragment profileFragment);

}
