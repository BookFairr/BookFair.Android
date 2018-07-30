package bookfair.android;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

import bookfair.android.injection.component.ApplicationComponent;
import bookfair.android.injection.component.DaggerApplicationComponent;
import bookfair.android.injection.module.ApplicationModule;
import jonathanfinerty.once.Once;

public class BookFairApp extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();


        JodaTimeAndroid.init(this);
        Once.initialise(this);

    }

    public static BookFairApp get(Context context) {
        return (BookFairApp) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }


}
