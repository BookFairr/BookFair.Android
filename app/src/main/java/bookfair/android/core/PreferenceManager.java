package bookfair.android.core;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

     private static SharedPreferences preferences;
     private SharedPreferences.Editor editor;

     public static final String PREF_FILE_NAME = "book_fair_preferences";
     private static final String IS_LOGIN = "is_logged_in";

     public PreferenceManager(Context context) {
          preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
          editor = this.preferences.edit();
     }
     public void clear() {
          preferences.edit().clear().apply();
     }

     /**
      * sets current status of the user
      *
      * @param status
      */
     public void setLoggedInStatus(boolean status) {
         editor.putBoolean(IS_LOGIN, status);
         editor.commit();
     }

}
