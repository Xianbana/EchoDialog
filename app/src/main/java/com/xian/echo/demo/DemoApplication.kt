package com.xian.echo.demo

import android.app.Application
import com.xian.echo.EchoDialog

class DemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化 EchoDialog
        EchoDialog.init(this)
    }
}
