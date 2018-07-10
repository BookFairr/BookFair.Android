package bookfair.android.core;

import android.content.Context;

import bookfair.android.BookFairApp;
import bookfair.android.injection.component.ApplicationComponent;

public abstract class BaseComponent {

    private Context context;

    public BaseComponent(Context context) {
        this.context = context;
    }

    protected ApplicationComponent applicationComponent() {
        return BookFairApp.get(context).getComponent();
    }

    public Context getContext() {
        return context;
    }


}
