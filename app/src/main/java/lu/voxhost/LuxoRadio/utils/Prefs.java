package lu.voxhost.LuxoRadio.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Prefs {
    public static final String PREFERENCES = "PREFS", SET_PREFERENCES = "set_prefs";
    public static final String FAV_ID = "fav_id";
    public static final String USER_TOKEN = "user_token";
    public static final String APP_CHECK = "app_check";

    public static boolean setPreference(Context context, String Preference_NAME, String key, String value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Preference_NAME, MODE_PRIVATE).edit();
        edit.putString(key, value);
        return edit.commit();
    }

    public static String getPreference(Context context, String Preference_NAME, String key, String value) {
        return context.getSharedPreferences(Preference_NAME, MODE_PRIVATE).getString(key,value);
    }

    public static boolean setUserToken(Context context, String value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES, MODE_PRIVATE).edit();
        edit.putString(USER_TOKEN, value);
        return edit.commit();
    }

    public static String getUserToken(Context context) {
        return context.getSharedPreferences(PREFERENCES, MODE_PRIVATE).getString(USER_TOKEN,"");
    }

}
