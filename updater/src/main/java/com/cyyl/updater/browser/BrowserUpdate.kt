package com.cyyl.updater.browser

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created on 2019/12/23
 * Title: 浏览器更新
 * Description: 通过浏览器更新
 *
 * @author Android-张康
 */
internal object BrowserUpdate {

    /**
     * 所有浏览器
     */
    internal const val BROWSER_TYPE_ALL = 0

    /**
     * 默认浏览器
     */
    internal const val BROWSER_TYPE_DEFAULT = 1

    /**
     * 系统浏览器
     */
    internal const val BROWSER_TYPE_SYSTEM = 2

    /**
     * UC浏览器
     */
    internal const val BROWSER_TYPE_UC = 3

    /**
     * 腾讯QQ浏览器
     */
    internal const val BROWSER_TYPE_TENCENT = 4

    /**
     * 谷歌浏览器
     */
    internal const val BROWSER_TYPE_CHROME = 5

    /**
     * 通过浏览器更新
     *
     * @param context 上下文
     * @param webLinks 下载链接
     * @param browserType 浏览器类型
     */
    @JvmStatic
    @JvmOverloads
    fun updateByBrowser(
        context: Context,
        webLinks: String,
        browserType: Int = BROWSER_TYPE_DEFAULT
    ): Boolean {
        if (webLinks.isEmpty()) {
            return false
        }
        return when (browserType) {
            BROWSER_TYPE_ALL -> {
                openAllBrowser(context, webLinks)
            }
            BROWSER_TYPE_DEFAULT -> {
                openDefaultBrowser(context, webLinks)
            }
            BROWSER_TYPE_SYSTEM -> {
                openSystemBrowser(context, webLinks)
            }
            BROWSER_TYPE_UC -> {
                openUCBrowser(context, webLinks)
            }
            BROWSER_TYPE_TENCENT -> {
                openTencentBrowser(context, webLinks)
            }
            BROWSER_TYPE_CHROME -> {
                openChromeBrowser(context, webLinks)
            }
            else -> {
                false
            }
        }
    }

    /**
     * 打开系统所有浏览器加载指定页面
     *
     * @param context 上下文对象
     * @param webLinks 链接
     */
    private fun openAllBrowser(context: Context, webLinks: String): Boolean {
        //暂未实现

        return false
    }

    /**
     * 打开默认浏览器加载指定页面
     *
     * @param context 上下文对象
     * @param webLinks 链接
     */
    private fun openDefaultBrowser(context: Context, webLinks: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webLinks))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            return false
        }
        return true
    }

    /**
     * 打开系统浏览器加载指定页面
     *
     * @param context 上下文对象
     * @param webLinks 链接
     */
    private fun openSystemBrowser(context: Context, webLinks: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webLinks))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //在华为手机上有问题
//        if (android.os.Build.BRAND.contains("HUAWEI")) {
//            intent.setClassName("com.huawei.browser", "com.huawei.browser.MainActivity")
//        } else {
//        }
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity")
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            return false
        }
        return true
    }

    /**
     * 跳转UC流量器打开指定页面
     *
     * @param context 上下文对象
     * @param webLinks 链接
     */
    private fun openUCBrowser(context: Context, webLinks: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webLinks))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setClassName("com.UCMobile", "com.UCMobile.main.UCMobile")
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            return false
        }
        return true
    }

    /**
     * 跳转腾讯QQ流量器打开指定页面
     *
     * @param context 上下文对象
     * @param webLinks 链接
     */
    private fun openTencentBrowser(context: Context, webLinks: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webLinks))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity")
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            return false
        }
        return true
    }

    /**
     * 跳转谷歌流量器打开指定页面
     *
     * @param context 上下文对象
     * @param webLinks 链接
     */
    private fun openChromeBrowser(context: Context, webLinks: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webLinks))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setClassName("com.android.chrome", "com.google.android.apps.chrome.Main")
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            return false
        }
        return true
    }
}