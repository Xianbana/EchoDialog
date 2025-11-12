package com.xian.echo.demo

import android.app.Application
import com.xian.echo.EchoDialog

class DemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化 EchoDialog
        EchoDialog.init(this)

        // 从资源文件自动加载主题（推荐方式）
        // 系统会根据当前是深色还是浅色模式，自动从 values/colors.xml 或 values-night/colors.xml 读取颜色
        EchoDialog.loadThemeFromResources()

        // 其他主题设置方式（已注释，用于测试对比）：
        // EchoDialog.setDarkTheme()  // 使用库的默认深色主题
        // EchoDialog.setLightTheme() // 使用库的默认浅色主题
        // EchoDialog.setMaterialTheme() // Material Design 主题
        
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
