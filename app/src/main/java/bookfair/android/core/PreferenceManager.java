package bookfair.android.core;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

     private static SharedPreferences preferences;
     private SharedPreferences.Editor editor;

     private static final String PREF_FILE_NAME = "book_fair_preferences";
     private static final String LOGGED_IN_STATUS = "logged_in_status";
     private static final String USER_FULL_NAME = "user_full_name";
     private static final String USERNAME = "username";


     public PreferenceManager(Context context) {
          preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
          editor = this.preferences.edit();
     }
     public void clear() {
          preferences.edit().clear().apply();
     }

     /**
      * set the login status
      * @param context
      * @param status
      */
     public void setLoggedInStatus(Context context, boolean status) {
         editor.putBoolean(LOGGED_IN_STATUS, status);
         editor.apply();
     }

     /**
      * Get the Login Status
      * @param context
      * @return boolean: login status
      */
     public static boolean getLoggedInStatus(Context context) {
          return preferences.getBoolean(LOGGED_IN_STATUS, false);
     }

     public void setUserFullName (String fullName) {
         editor.putString(USER_FULL_NAME, fullName);
         editor.commit();
     }

     public void setUsername (String username) {
         editor.putString(USERNAME, username);
         editor.commit();
     }

    public String getUserFullName() {
        return preferences.getString(USER_FULL_NAME, "");
    }

    public String getUsername() {
        return preferences.getString(USERNAME, "");
    }

}
