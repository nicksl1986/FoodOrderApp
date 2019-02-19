package com.slobodsky.nick.foodorderapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SharedPref {

    public static final String PREFS_NAME = "MyPrefsFile";

    private static final SharedPref ourInstance = new SharedPref();

    Context mContext;

    String key;

    Map<String, String> inputMap;

    public static SharedPref sherdInstance() {

        return ourInstance;
    }

    private SharedPref() {

    }


    public void deleteAll(Context mContext) {

        SharedPreferences pSharedPref = mContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        pSharedPref.edit().clear().commit();

    }

    public void saveDictunary(Context mContext, Map<String, String> inputMap, String key) {

        if (inputMap == null) {
            saveStringWithKey(mContext, null, key);

            return;
        }

        SharedPreferences pSharedPref = mContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (pSharedPref != null) {

            JSONObject jsonObject = new JSONObject(inputMap);

            String jsonString = jsonObject.toString();

            SharedPreferences.Editor editor = pSharedPref.edit();


            editor.remove(key).commit();

            editor.putString(key, jsonString);

            editor.commit();

        }
    }


    public Map<String, String> loadMap(Context mContext, String dictName) {
        Map<String, String> outputMap = new HashMap<String, String>();

        try {
            SharedPreferences pSharedPref = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            if (pSharedPref != null)
            {
                String jsonString = pSharedPref.getString(dictName, (new JSONObject()).toString());

                JSONObject jsonObject = new JSONObject(jsonString);

                Iterator<String> keysItr = jsonObject.keys();

                while (keysItr.hasNext()) {

                    String key = keysItr.next();

                    String value = (String) jsonObject.get(key);

                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return outputMap;
    }

    public void saveStringWithKey(Context mContext, String str, String key) {

        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

        SharedPreferences.Editor editor = settings.edit();

        editor.putString(key, str);

        editor.commit();

    }

    public void saveBoolWithKey(Context mContext, boolean bool, String key) {

        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(key, bool);

        editor.commit();

    }

    public boolean getBoolForKey(Context mContext, String key) {

        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

        boolean bool = settings.getBoolean(key, false);

        return bool;

    }

    public void saveIntWithKey(Context mContext, int num, String key) {

        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(key, num);

        editor.commit();

    }

    public int getIntForKey(Context mContext, String key) {

        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

        int num = settings.getInt(key, 0);

        return num;

    }

    public String loadStringForKey(Context mContext, String key) {

        String str = "";

        try {

            SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

            str = settings.getString(key, null);
        } catch (Exception e) {
            return "";
        }

        return str;

    }

    public JSONObject getLanguageTranslationForKey(Context context, String dictKey) {

        String dict = com.slobodsky.nick.foodorderapp.SharedPref.sherdInstance().
                loadStringForKey(context, dictKey);


        if (dict != null) {
            try {

                JSONObject json = new JSONObject(dict);

                return json;

            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
        return null;
    }
}





