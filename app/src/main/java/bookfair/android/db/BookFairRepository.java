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

    public RealmResults<UserProfile> fetchProfiles() {
        return database.get().where(UserProfile.class).findAll();
    }

    public RealmResults<UserProfile> fetchProfilesAsync () {
        return database.get().where(UserProfile.class).findAllAsync();
    }
}
