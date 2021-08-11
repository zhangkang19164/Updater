package com.cyyl.updater

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val downloadUrlE = findViewById<EditText>(R.id.download_url)
//        val url = "https://assistant-res.niucoo.cn/game/1624616191520_cn.niucoo.niucooapp_91.apk"
        val url =
            "https://fmapp-cos.chinafamilymart.com.cn/image/20210809/1628501171048/fmapp_v2.6.0_2021-08-06_OpenInstall_release_260_jiagu_sign.apk"
        downloadUrlE.setText(url)
        findViewById<View>(R.id.start_download).setOnClickListener {
            val downloadUrl = downloadUrlE.text.toString()
            if (downloadUrl.isBlank()) {
                Toast.makeText(this, "请输入下载链接", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AppUpdateBuilder(this)
                .setDownloadUrl(downloadUrl)
                .setFileName("update.apk")
                .download()
        }

    }
}