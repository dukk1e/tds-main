package com.vnh.apptds.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private static final String PREF_NAME = "app_preferences";

    private static final String ACCESS_TOKEN = "access_token";
    private static final String KEY_RANDOM_MIN = "random_min";
    private static final String KEY_RANDOM_MAX = "random_max";
    private static final String KEY_JOB_TO_GET_COIN = "job_to_get_coin";
    private static final String KEY_SWITCH_ACCOUNT_DELAY = "switch_account_delay";
    private static final String KEY_DELAY_BETWEEN_TASKS = "delay_between_tasks";
    private static final String KEY_TASK_TYPE = "task_type";

    // AccessToken
    public static void saveAccessToken(Context context, String token) {
        getPrefs(context).edit().putString(ACCESS_TOKEN, token).apply();
    }
    public static String getAccessToken(Context context) {
        return getPrefs(context).getString(ACCESS_TOKEN, null);
    }
    public static void clearAccessToken(Context context) {
        getPrefs(context).edit().remove(ACCESS_TOKEN).apply();
    }

    // Random Min
    public static void saveRandomMin(Context context, int value) {
        getPrefs(context).edit().putInt(KEY_RANDOM_MIN, value).apply();
    }
    public static int getRandomMin(Context context) {
        return getPrefs(context).getInt(KEY_RANDOM_MIN, 8);
    }

    // Random Max
    public static void saveRandomMax(Context context, int value) {
        getPrefs(context).edit().putInt(KEY_RANDOM_MAX, value).apply();
    }
    public static int getRandomMax(Context context) {
        return getPrefs(context).getInt(KEY_RANDOM_MAX, 8);
    }

    // Job to Get Coin
    public static void saveJobToGetCoin(Context context, int value) {
        getPrefs(context).edit().putInt(KEY_JOB_TO_GET_COIN, value).apply();
    }
    public static int getJobToGetCoin(Context context) {
        return getPrefs(context).getInt(KEY_JOB_TO_GET_COIN, 8);
    }

    // Switch Account Delay
    public static void saveSwitchAccountDelay(Context context, int value) {
        getPrefs(context).edit().putInt(KEY_SWITCH_ACCOUNT_DELAY, value).apply();
    }
    public static int getSwitchAccountDelay(Context context) {
        return getPrefs(context).getInt(KEY_SWITCH_ACCOUNT_DELAY, 8);
    }

    // Delay Between Tasks
    public static void saveDelayBetweenTasks(Context context, int value) {
        getPrefs(context).edit().putInt(KEY_DELAY_BETWEEN_TASKS, value).apply();
    }
    public static int getDelayBetweenTasks(Context context) {
        return getPrefs(context).getInt(KEY_DELAY_BETWEEN_TASKS, 1);
    }

    // Task Type
    public static void saveTaskType(Context context, String value) {
        getPrefs(context).edit().putString(KEY_TASK_TYPE, value).apply();
    }
    public static String getTaskType(Context context) {
        return getPrefs(context).getString(KEY_TASK_TYPE, "follow");
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
