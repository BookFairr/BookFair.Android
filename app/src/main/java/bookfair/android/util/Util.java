package bookfair.android.util;

import android.util.Patterns;

public class Util {

    public final static boolean isEmailValid (CharSequence target) {
        if (target == null) {
            return false;
        }else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean passwordStrength(String password) {
        if (password.trim().length() > 6)
            return true;
        else
            return false;

    }

}
