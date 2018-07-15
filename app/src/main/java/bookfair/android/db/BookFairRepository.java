package bookfair.android.db;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Provider;

import bookfair.android.core.BaseComponent;
import io.realm.Realm;

public class BookFairRepository extends BaseComponent {

    @Inject
    Provider <Realm> database;

    public BookFairRepository(Context context) {
        super(context);

        applicationComponent().inject(this);
    }


}
