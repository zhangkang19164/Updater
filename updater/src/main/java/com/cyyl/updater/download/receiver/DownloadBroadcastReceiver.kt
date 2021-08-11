package com.cyyl.updater.download.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cyyl.updater.download.RequestInstallPackagesActivity

/**
 * @author : Android-张康
 * created on: 2021/8/11 10:54
 * description: 下载监听
 */
class DownloadBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent?.action) {
            context.startActivity(Intent(intent).apply {
                setClass(context, RequestInstallPackagesActivity::class.java)
            })
            //下载完成，移除掉监听
            context.unregisterReceiver(this)
        }
    }
}