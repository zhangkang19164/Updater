package com.cyyl.updater

import com.cyyl.updater.browser.BrowserUpdate
import com.cyyl.updater.download.DownloadUpdate
import com.cyyl.updater.market.MarketUpdate

/**
 * Created on 2019/12/23
 * Title:
 * Description:
 *
 * @author Android-张康
 */
object AppUpdater {
    /**
     * 应用市场更新
     */
    const val UPDATE_TYPE_MARKET = 0

    /**
     * 浏览器更新
     */
    const val UPDATE_TYPE_BROWSER = 1

    /**
     * 内部更新
     */
    const val UPDATE_TYPE_INTERNAL = 2

    /**
     * 所有浏览器
     */
    private const val BROWSER_TYPE_ALL = BrowserUpdate.BROWSER_TYPE_ALL

    /**
     * 默认浏览器
     */
    const val BROWSER_TYPE_DEFAULT = BrowserUpdate.BROWSER_TYPE_DEFAULT

    /**
     * 系统浏览器
     */
    private const val BROWSER_TYPE_SYSTEM = BrowserUpdate.BROWSER_TYPE_SYSTEM

    /**
     * UC浏览器
     */
    const val BROWSER_TYPE_UC = BrowserUpdate.BROWSER_TYPE_UC

    /**
     * 腾讯QQ浏览器
     */
    const val BROWSER_TYPE_TENCENT = BrowserUpdate.BROWSER_TYPE_TENCENT

    /**
     * 谷歌浏览器
     */
    const val BROWSER_TYPE_CHROME = BrowserUpdate.BROWSER_TYPE_CHROME


    @JvmStatic
    @JvmOverloads
    fun appUpdate(
        appUpdateBuilder: AppUpdateBuilder,
        appUpdateCallback: AppUpdateCallback? = null
    ) {
        when (appUpdateBuilder.getUpdateType()) {
            UPDATE_TYPE_MARKET -> {
                updateByMarket(appUpdateBuilder, appUpdateCallback)
            }
            UPDATE_TYPE_BROWSER -> {
                updateByBrowser(appUpdateBuilder, appUpdateCallback)
            }
            UPDATE_TYPE_INTERNAL -> {
                updateByInternal(appUpdateBuilder, appUpdateCallback)
            }
            else -> {
                appUpdateCallback?.failure()
            }
        }
    }


    private fun updateByMarket(
        appUpdateBuilder: AppUpdateBuilder,
        appUpdateCallback: AppUpdateCallback? = null
    ) {
        val packageName = appUpdateBuilder.getPackageName()
        if (packageName?.isNotEmpty() == true) {
            val updateResult = MarketUpdate.updateByPackageName(
                appUpdateBuilder.getContext(),
                packageName
            )
            appUpdateCallback?.let {
                if (updateResult) {
                    it.successful()
                } else {
                    it.failure()
                }
            }
            return
        }
        val appName = appUpdateBuilder.getAppName()
        if (appName?.isNotEmpty() == true) {
            val updateResult = MarketUpdate.updateByAppName(
                appUpdateBuilder.getContext(),
                appName
            )
            appUpdateCallback?.let {
                if (updateResult) {
                    it.successful()
                } else {
                    it.failure()
                }
            }
        }
    }

    private fun updateByBrowser(
        appUpdateBuilder: AppUpdateBuilder,
        appUpdateCallback: AppUpdateCallback? = null
    ) {
        val updateWebLinks = appUpdateBuilder.getUpdateWebLinks()
        if (updateWebLinks?.isNotEmpty() == true) {
            val updateResult = BrowserUpdate.updateByBrowser(
                appUpdateBuilder.getContext(),
                updateWebLinks,
                appUpdateBuilder.getBrowserType()
            )
            appUpdateCallback?.let {
                if (updateResult) {
                    it.successful()
                } else {
                    it.failure()
                }
            }
        }
    }

    private fun updateByInternal(
        appUpdateBuilder: AppUpdateBuilder,
        appUpdateCallback: AppUpdateCallback? = null
    ) {
        val downloadUrl = appUpdateBuilder.getDownloadUrl()
        val fileName = appUpdateBuilder.getFileName()
        if (!downloadUrl.isNullOrEmpty() && !fileName.isNullOrEmpty()) {
            DownloadUpdate.downloadAPK(
                appUpdateBuilder.getContext(),
                downloadUrl, fileName
            )
        } else {
            appUpdateCallback?.failure()
        }
    }

    interface AppUpdateCallback {
        fun successful()

        fun updating()

        fun failure()
    }
}