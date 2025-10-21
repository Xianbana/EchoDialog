package com.xian.echo.demo

import android.app.Application
import com.xian.echo.EchoDialog

class DemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化 EchoDialog
        EchoDialog.init(this)
        
        // 设置主题（示例：使用浅色主题）
        EchoDialog.setDarkTheme()
        
        // 或者使用其他预设主题
        // EchoDialog.setDarkTheme()
        // EchoDialog.setMaterialTheme()
        
        // 或者使用自定义主题
        // EchoDialog.setCustomTheme(
        //     primaryColor = android.graphics.Color.parseColor("#FF6200EE"),
        //     backgroundColor = android.graphics.Color.parseColor("#FFFFFFFF"),
        //     textColor = android.graphics.Color.parseColor("#FF000000")
        // )
        
        // 或者使用品牌主题
        // EchoDialog.setBrandTheme(
        //     brandColor = android.graphics.Color.parseColor("#FF1976D2"),
        //     isDark = false
        // )
    }
}
