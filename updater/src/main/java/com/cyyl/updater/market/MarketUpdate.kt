package com.cyyl.updater.market

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri

/**
 * Created on 2019/12/23
 * Title: 应用市场更新
 * Description:参考[https://www.jianshu.com/p/fc340cc6f75f]
 * 参考[https://www.jianshu.com/p/a4a806567368]
 *
 * @author Android-张康
 */
internal object MarketUpdate {

    /**
     * 根据包名更新 跳转到应用商店，打开对应包名的应用
     * @param context 上下文
     * @param packageName 包名
     */
    fun updateByPackageName(context: Context, packageName: String): Boolean {
        if (packageName.isEmpty()) {
            return false
        }
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return true
    }


    /**
     * 根据应用名更新 跳转到应用商店，搜索应用名
     * @param context 上下文
     * @param appName 应用名
     */
    fun updateByAppName(context: Context, appName: String): Boolean {
        if (appName.isEmpty()) {
            return false
        }
        val uri: Uri = Uri.parse("market://search?q=$appName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return true
    }


    fun getAllMarket(context: Context): List<String> {
        val intent = Intent()
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_APP_MARKET)
        val queryIntentActivities: List<ResolveInfo>? =
            context.packageManager.queryIntentActivities(intent, 0)
        val list = mutableListOf<String>()
        queryIntentActivities?.let {
            for (resolveInfo in it) {
                resolveInfo.activityInfo?.let { activityInfo ->
                    activityInfo.packageName?.let { packageName ->
                        list.add(packageName)
                    }
                }
            }
        }
        return list
    }
}