package com.xian.echo.core

import android.graphics.Color
import com.xian.echo.R

/**
 * EchoDialog 预设主题集合
 * 提供常用的主题配置，供外部直接使用
 */
object EchoDialogThemes {
    
    /**
     * 默认主题
     * 使用系统默认样式
     */
    val DEFAULT = EchoDialogTheme()
    
    /**
     * 浅色主题
     * 适合浅色背景的应用
     */
    val LIGHT = EchoDialogTheme(
        dialogBackgroundColor = Color.parseColor("#FFFFFFFF"), // 使用颜色值以便动态切换
        titleTextColor = Color.parseColor("#FF212121"),
        titleTextSize = 18f,
        messageTextColor = Color.parseColor("#FF424242"),
        messageTextSize = 14f,
        positiveButtonBackground = R.drawable.text_shape,
        positiveButtonTextColor = Color.WHITE,
        positiveButtonTextSize = 16f,
        negativeButtonBackground = R.drawable.text_cancel_shape,
        negativeButtonTextColor = Color.WHITE,
        negativeButtonTextSize = 16f,
        inputBackground = R.drawable.edit_shape,
        inputTextColor = Color.parseColor("#FF212121"),
        inputHintColor = Color.parseColor("#FF757575"),
        seekBarProgressColor = Color.parseColor("#FF1976D2"),
        seekBarThumbColor = Color.parseColor("#FF1976D2"),
        listItemTextColor = Color.parseColor("#FF212121"),
        checkboxColor = Color.parseColor("#FF1976D2")
    )
    
    /**
     * 深色主题
     * 适合深色背景的应用
     */
    val DARK = EchoDialogTheme(
        dialogBackgroundColor = Color.parseColor("#FF1E1E1E"), // 使用颜色值以便动态切换
        titleTextColor = Color.WHITE,
        titleTextSize = 18f,
        messageTextColor = Color.parseColor("#FFB3B3B3"),
        messageTextSize = 14f,
        positiveButtonBackground = R.drawable.text_shape,
        positiveButtonTextColor = Color.WHITE,
        positiveButtonTextSize = 16f,
        negativeButtonBackground = R.drawable.text_cancel_shape,
        negativeButtonTextColor = Color.WHITE,
        negativeButtonTextSize = 16f,
        inputBackground = R.drawable.edit_shape,
        inputTextColor = Color.WHITE,
        inputHintColor = Color.parseColor("#FF757575"),
        seekBarProgressColor = Color.parseColor("#FFBB86FC"),
        seekBarThumbColor = Color.parseColor("#FFBB86FC"),
        listItemTextColor = Color.WHITE,
        checkboxColor = Color.parseColor("#FFBB86FC")
    )
    
    /**
     * Material Design 主题
     * 遵循 Material Design 规范
     */
    val MATERIAL = EchoDialogTheme(
        dialogBackground = R.drawable.echo_dialog_background,
        titleTextColor = Color.parseColor("#FF212121"),
        titleTextSize = 20f,
        messageTextColor = Color.parseColor("#FF424242"),
        messageTextSize = 16f,
        positiveButtonBackground = R.drawable.text_shape,
        positiveButtonTextColor = Color.WHITE,
        positiveButtonTextSize = 14f,
        negativeButtonBackground = R.drawable.text_cancel_shape,
        negativeButtonTextColor = Color.WHITE,
        negativeButtonTextSize = 14f,
        inputBackground = R.drawable.edit_shape,
        inputTextColor = Color.parseColor("#FF212121"),
        inputHintColor = Color.parseColor("#FF757575"),
        seekBarProgressColor = Color.parseColor("#FF6200EE"),
        seekBarThumbColor = Color.parseColor("#FF6200EE"),
        listItemTextColor = Color.parseColor("#FF212121"),
        checkboxColor = Color.parseColor("#FF6200EE")
    )
    
    /**
     * 创建自定义主题的便捷方法
     * @param primaryColor 主色调
     * @param backgroundColor 背景色
     * @param textColor 文字颜色
     * @return 自定义主题
     */
    fun createCustom(
        primaryColor: Int,
        backgroundColor: Int = Color.WHITE,
        textColor: Int = Color.BLACK
    ): EchoDialogTheme {
        return EchoDialogTheme(
            dialogBackground = R.drawable.echo_dialog_background,
            titleTextColor = textColor,
            titleTextSize = 18f,
            messageTextColor = textColor,
            messageTextSize = 14f,
            positiveButtonBackground = R.drawable.text_shape,
            positiveButtonTextColor = Color.WHITE,
            positiveButtonTextSize = 16f,
            negativeButtonBackground = R.drawable.text_cancel_shape,
            negativeButtonTextColor = Color.WHITE,
            negativeButtonTextSize = 16f,
            inputBackground = R.drawable.edit_shape,
            inputTextColor = textColor,
            inputHintColor = Color.parseColor("#FF757575"),
            seekBarProgressColor = primaryColor,
            seekBarThumbColor = primaryColor,
            listItemTextColor = textColor,
            checkboxColor = primaryColor
        )
    }
    
    /**
     * 创建品牌主题的便捷方法
     * @param brandColor 品牌色
     * @param isDark 是否为深色主题
     * @return 品牌主题
     */
    fun createBrand(
        brandColor: Int,
        isDark: Boolean = false
    ): EchoDialogTheme {
        val textColor = if (isDark) Color.WHITE else Color.BLACK
        val messageColor = if (isDark) Color.parseColor("#FFB3B3B3") else Color.parseColor("#FF424242")
        
        return EchoDialogTheme(
            dialogBackground = R.drawable.echo_dialog_background,
            titleTextColor = textColor,
            titleTextSize = 18f,
            messageTextColor = messageColor,
            messageTextSize = 14f,
            positiveButtonBackground = R.drawable.text_shape,
            positiveButtonTextColor = Color.WHITE,
            positiveButtonTextSize = 16f,
            negativeButtonBackground = R.drawable.text_cancel_shape,
            negativeButtonTextColor = Color.WHITE,
            negativeButtonTextSize = 16f,
            inputBackground = R.drawable.edit_shape,
            inputTextColor = textColor,
            inputHintColor = Color.parseColor("#FF757575"),
            seekBarProgressColor = brandColor,
            seekBarThumbColor = brandColor,
            listItemTextColor = textColor,
            checkboxColor = brandColor
        )
    }
}
