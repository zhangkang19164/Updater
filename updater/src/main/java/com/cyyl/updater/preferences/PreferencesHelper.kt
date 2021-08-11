package com.cyyl.updater.preferences

import android.content.Context

/**
 * @author : Android-张康
 * created on: 2021/8/11 11:06
 * description:
 */
object PreferencesHelper {
    private const val SP_NAME_DOWNLOAD_ID = "updater_ids"

    /**
     * 保存配置
     *
     * @param context 上下文
     * @param key
     * @param value 值
     */
    fun encode(context: Context, key: String, value: Long) {
        getSharedPreferences(context).edit().putLong(key, value).apply()
    }

    /**
     * 获取保留的数据
     *
     * @param context 上下文
     * @param key
     */
    fun decode(context: Context, key: String, defValue: Long = -1L): Long {
        return getSharedPreferences(context).getLong(key, defValue)
    }


    private fun getSharedPreferences(context: Context) =
        context.getSharedPreferences(SP_NAME_DOWNLOAD_ID, Context.MODE_PRIVATE)
}