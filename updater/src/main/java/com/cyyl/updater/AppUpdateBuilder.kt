package com.cyyl.updater

import android.content.Context
import com.cyyl.updater.browser.BrowserUpdate

/**
 * Created on 2019/12/23
 * Title:
 * Description:
 *
 * @author Android-张康
 */
class AppUpdateBuilder(context: Context, updateType: Int = AppUpdater.UPDATE_TYPE_INTERNAL) {

    private var mContext: Context = context
    private var mUpdateType: Int = updateType

    private var mPackageName: String? = null

    private var mAppName: String? = null

    private var mUpdateWebLinks: String? = null

    private var mBrowserType: Int = BrowserUpdate.BROWSER_TYPE_DEFAULT

    private var mDownloadUrl: String? = null

    private var mFileName: String? = null

    fun getContext() = mContext
    fun getUpdateType() = mUpdateType
    fun getPackageName() = mPackageName

    fun setPackageName(packageName: String): AppUpdateBuilder {
        mUpdateType = AppUpdater.UPDATE_TYPE_MARKET
        mPackageName = packageName
        return this
    }

    fun getAppName() = mAppName

    fun setAppName(appName: String): AppUpdateBuilder {
        mUpdateType = AppUpdater.UPDATE_TYPE_MARKET
        mAppName = appName
        return this
    }

    fun getUpdateWebLinks() = mUpdateWebLinks

    fun getBrowserType() = mBrowserType

    fun setUpdateWebLinks(
        updateWebLinks: String,
        browserType: Int = AppUpdater.BROWSER_TYPE_DEFAULT
    ): AppUpdateBuilder {
        mUpdateType = AppUpdater.UPDATE_TYPE_BROWSER
        mUpdateWebLinks = updateWebLinks
        mBrowserType = browserType
        return this
    }

    fun getDownloadUrl() = mDownloadUrl

    fun setDownloadUrl(downloadUrl: String): AppUpdateBuilder {
        mUpdateType = AppUpdater.UPDATE_TYPE_INTERNAL
        mDownloadUrl = downloadUrl
        return this
    }

    fun getFileName() = mFileName

    fun setFileName(fileName: String): AppUpdateBuilder {
        mFileName = fileName
        return this
    }

    fun download() {
        AppUpdater.appUpdate(this)
    }

}