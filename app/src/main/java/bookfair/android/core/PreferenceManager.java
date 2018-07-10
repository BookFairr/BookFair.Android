package bookfair.android.core;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

     private static SharedPreferences preferences;
     private SharedPreferences.Editor editor;

     public static final String PREF_FILE_NAME = "staff_genius_preferences";
     private static final String IS_LOGIN = "is_logged_in";

     public PreferenceManager(Context context) {
          preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
          editor = this.preferences.edit();
     }
     public void clear() {
          preferences.edit().clear().apply();
     }

     public boolean isUserLoggedIn() {
          return preferences.getBoolean(IS_LOGIN, false);
     }
}
