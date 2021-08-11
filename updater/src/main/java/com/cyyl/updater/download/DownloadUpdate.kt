package com.cyyl.updater.download

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.cyyl.updater.download.receiver.DownloadBroadcastReceiver
import com.cyyl.updater.preferences.PreferencesHelper
import java.util.*

/**
 * Created on 2019/12/23
 * Title:
 * Description:
 *
 * @author Android-张康
 */
internal object DownloadUpdate {

    private const val TAG = "DownloadUpdate"
    private const val SP_KEYS_LAST_DOWNLOAD_ID = "last_download_id"

    fun downloadAPK(context: Context, downloadUrl: String, fileName: String) {
        downloadAPKByDownloadManager(context, downloadUrl, fileName)
    }

    /**
     * 通过DownloadManager下载
     * 参考[https://blog.csdn.net/johnny901114/article/details/51472600]
     * [https://www.jianshu.com/p/7ad92b3d9069]
     * @param context 上下文
     * @param downloadUrl 下载地址
     * @param fileName 保存的文件名称
     */
    fun downloadAPKByDownloadManager(context: Context, downloadUrl: String, fileName: String) {
        //注册下载完成监听
        context.registerReceiver(
            DownloadBroadcastReceiver(),
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        if (check(context, 593L)) {
            return
        }
        val saveFileName = "update.apk"
        val request =
            DownloadManager.Request(Uri.parse(downloadUrl))
                //设置下载的路径 外部缓存路径
                .setDestinationInExternalFilesDir(
                    context,
                    Environment.DIRECTORY_DOWNLOADS,
                    saveFileName
                )
                //下载的标题,会在通知中显示
                .setTitle("正在下载")
                //下载的描述，会在通知中显示
                .setDescription("正在下载最新版本")
                //设置通知的显示方式
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                //设置下载的类型
                .setMimeType("application/vnd.android.package-archive")
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //将最后一次下载的id保存到本地
        val enqueue = downloadManager.enqueue(request)
        PreferencesHelper.encode(context, SP_KEYS_LAST_DOWNLOAD_ID, enqueue)
    }

    /**
     * 检查是否已经有下载过的文件
     * 判定条件
     * 1、下载地址一致
     * 2、
     *
     */
    private fun check(context: Context, lastDownloadId: Long): Boolean {
        if (lastDownloadId != -1L) {
            val query = DownloadManager.Query()
            query.setFilterById(lastDownloadId)
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val cursor: Cursor = downloadManager.query(query)
            Log.i(TAG, "check: columnCount = ${cursor.columnCount}")
            Log.i(TAG, "check: columnNames = ${Arrays.toString(cursor.columnNames)}")
            Log.i(TAG, "check: count = ${cursor.count}")
            if (cursor.moveToFirst()) {
                val uri=cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI))

                do {
                    val columnCount = cursor.columnCount
                    for (index in 0 until columnCount) {
                        try {
                            Log.i(
                                TAG,
                                "check: ${cursor.getColumnName(index)} = ${cursor.getString(index)}"
                            )
                        } catch (e: SecurityException) {

                        }
                    }
                } while (cursor.moveToNext())
            }


//            if (cursor.moveToFirst()) {
//                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
//                when (status) {
//                    //下载暂停
//                    DownloadManager.STATUS_PAUSED -> {
//                    }
//                    //下载延迟
//                    DownloadManager.STATUS_PENDING -> {
//                    }
//                    //正在下载
//                    DownloadManager.STATUS_RUNNING -> {
//                    }
//                    //下载完成
//                    DownloadManager.STATUS_SUCCESSFUL -> {
//                        cursor.close()
//                    }
//                    //下载失败
//                    DownloadManager.STATUS_FAILED -> {
//                        cursor.close()
//                    }
//                }
//            }
        }
        return true
    }


}