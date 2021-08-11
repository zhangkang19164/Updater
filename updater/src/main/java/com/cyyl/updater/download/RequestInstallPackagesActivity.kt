package com.cyyl.updater.download

import android.annotation.TargetApi
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File


/**
 * Created on 2019/12/23
 * Title:
 * Description: https://blog.csdn.net/johnny901114/article/details/51472600
 * https://blog.csdn.net/feibendexiaoma/article/details/80093354
 *
 * @author Android-张康
 */
class RequestInstallPackagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        val haveInstallPermission: Boolean =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                packageManager.canRequestPackageInstalls()
            } else {
                true
            }
        if (!haveInstallPermission) {
            //权限没有打开则提示用户去手动打开
            toInstallPermissionSettingIntent()
        } else {
            installApk()
        }

    }

    /**
     * 开启安装未知来源权限
     */
    @TargetApi(Build.VERSION_CODES.O)
    private fun toInstallPermissionSettingIntent() {
        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    installApk()
                } else {
                    Toast.makeText(this, "安装更新需要权限", Toast.LENGTH_SHORT).show()
                }
            }
        val packageURI = Uri.parse("package:$packageName")
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
        registerForActivityResult.launch(intent)
    }


    private fun installApk() {
        val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        if (downloadId != -1L) {
            val dManager: DownloadManager =
                getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val install = Intent(Intent.ACTION_VIEW)
            val downloadFileUri: Uri? = dManager.getUriForDownloadedFile(downloadId)
            if (downloadFileUri != null) {
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive")
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(install)
            }
        }
    }


}