package com.xian.echo.core

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.xian.echo.R

/**
 * EchoDialog 主题配置
 * 用于统一设置对话框的样式
 */
data class EchoDialogTheme(
    // 对话框背景
    val dialogBackground: Int? = null,
    
    // 标题样式
    val titleTextColor: Int? = null,
    val titleTextSize: Float? = null,
    
    // 消息文本样式
    val messageTextColor: Int? = null,
    val messageTextSize: Float? = null,
    
    // 确定按钮样式
    val positiveButtonBackground: Int? = null,
    val positiveButtonTextColor: Int? = null,
    val positiveButtonTextSize: Float? = null,
    
    // 取消按钮样式
    val negativeButtonBackground: Int? = null,
    val negativeButtonTextColor: Int? = null,
    val negativeButtonTextSize: Float? = null,
    
    // 输入框样式
    val inputBackground: Int? = null,
    val inputTextColor: Int? = null,
    val inputHintColor: Int? = null,
    
    // SeekBar样式
    val seekBarProgressColor: Int? = null,
    val seekBarThumbColor: Int? = null,
    
    // 列表项样式
    val listItemTextColor: Int? = null,
    val checkboxColor: Int? = null
) {
    
    companion object {
        /**
         * 创建默认主题
         */
        fun createDefault(): EchoDialogTheme {
            return EchoDialogTheme()
        }
        
        /**
         * 创建深色主题
         */
        fun createDark(): EchoDialogTheme {
            return EchoDialogTheme(
                dialogBackground = R.drawable.echo_dialog_background,
                titleTextColor = Color.WHITE,
                messageTextColor = Color.LTGRAY,
                positiveButtonBackground = R.drawable.text_shape,
                positiveButtonTextColor = Color.WHITE,
                negativeButtonBackground = R.drawable.text_cancel_shape,
                negativeButtonTextColor = Color.WHITE,
                inputBackground = R.drawable.edit_shape,
                inputTextColor = Color.WHITE,
                inputHintColor = Color.GRAY,
                listItemTextColor = Color.WHITE,
                checkboxColor = Color.WHITE
            )
        }
        
        /**
         * 创建浅色主题
         */
        fun createLight(): EchoDialogTheme {
            return EchoDialogTheme(
                dialogBackground = R.drawable.echo_dialog_background,
                titleTextColor = Color.BLACK,
                messageTextColor = Color.DKGRAY,
                positiveButtonBackground = R.drawable.text_shape,
                positiveButtonTextColor = Color.WHITE,
                negativeButtonBackground = R.drawable.text_cancel_shape,
                negativeButtonTextColor = Color.WHITE,
                inputBackground = R.drawable.edit_shape,
                inputTextColor = Color.BLACK,
                inputHintColor = Color.GRAY,
                listItemTextColor = Color.BLACK,
                checkboxColor = Color.BLACK
            )
        }
        
        /**
         * 创建自定义主题
         */
        fun createCustom(
            primaryColor: Int,
            backgroundColor: Int,
            textColor: Int
        ): EchoDialogTheme {
            return EchoDialogTheme(
                dialogBackground = R.drawable.echo_dialog_background,
                titleTextColor = textColor,
                messageTextColor = textColor,
                positiveButtonBackground = R.drawable.text_shape,
                positiveButtonTextColor = Color.WHITE,
                negativeButtonBackground = R.drawable.text_cancel_shape,
                negativeButtonTextColor = Color.WHITE,
                inputBackground = R.drawable.edit_shape,
                inputTextColor = textColor,
                inputHintColor = Color.GRAY,
                seekBarProgressColor = primaryColor,
                seekBarThumbColor = primaryColor,
                listItemTextColor = textColor,
                checkboxColor = primaryColor
            )
        }
    }
}
