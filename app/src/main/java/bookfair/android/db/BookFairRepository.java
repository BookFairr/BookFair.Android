package bookfair.android.db;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import bookfair.android.core.BaseComponent;
import bookfair.android.db.models.UserProfile;
import io.realm.Realm;
import io.realm.RealmResults;

public class BookFairRepository extends BaseComponent {

    @Inject
    Provider <Realm> database;

    public BookFairRepository(Context context) {
        super(context);

        applicationComponent().inject(this);
    }

    public UserProfile getUserProfile() {
        return database.get().where(UserProfile.class).findFirst();
    }

    public void saveUserProfile(UserProfile userProfile) {
        database.get().executeTransaction(realm -> realm.copyToRealmOrUpdate(userProfile) );
    }

    public Provider<Realm> getDatabase() {
        return database;
    }
}
