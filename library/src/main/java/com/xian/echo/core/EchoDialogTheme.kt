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
    // 对话框背景（drawable 资源 ID）
    val dialogBackground: Int? = null,
    // 对话框背景颜色（颜色值，优先级高于 dialogBackground）
    val dialogBackgroundColor: Int? = null,
    
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
    
    /**
     * 创建对话框背景 Drawable
     * 如果提供了 dialogBackgroundColor，则使用颜色值创建
     * 否则使用 dialogBackground 资源
     * 如果都没有，返回 null
     */
    fun createDialogBackground(context: android.content.Context): Drawable? {
        return when {
            dialogBackgroundColor != null -> {
                // 使用颜色值创建带圆角的 drawable
                GradientDrawable().apply {
                    setColor(dialogBackgroundColor)
                    cornerRadius = context.resources.getDimension(
                        com.xian.echo.R.dimen.echo_dialog_corner_radius
                    )
                }
            }
            dialogBackground != null -> {
                // 使用 drawable 资源
                ContextCompat.getDrawable(context, dialogBackground)
            }
            else -> null
        }
    }
    
    companion object {
        /**
         * 创建默认主题
         */
        fun createDefault(): EchoDialogTheme {
            return EchoDialogTheme()
        }
        
        /**
         * 从资源文件自动创建主题
         * 会尝试从应用的 colors.xml 中读取颜色，如果没有定义则使用默认值
         * 
         * 支持的资源名称：
         * - echo_dialog_bg: 对话框背景颜色
         * - echo_dialog_title: 标题文本颜色
         * - echo_dialog_message: 消息文本颜色
         * - echo_dialog_positive_bg: 确定按钮背景颜色（资源 ID 或颜色值）
         * - echo_dialog_positive_text: 确定按钮文本颜色
         * - echo_dialog_negative_bg: 取消按钮背景颜色（资源 ID 或颜色值）
         * - echo_dialog_negative_text: 取消按钮文本颜色
         * - echo_dialog_input_text: 输入框文本颜色
         * - echo_dialog_input_hint: 输入框提示文本颜色
         * - echo_dialog_seekbar_progress: SeekBar 进度条颜色
         * - echo_dialog_seekbar_thumb: SeekBar 滑块颜色
         * - echo_dialog_list_item_text: 列表项文本颜色
         * - echo_dialog_checkbox: 复选框颜色
         * 
         * @param context Context 用于访问资源
         * @param baseTheme 基础主题，如果资源文件中没有定义颜色，使用此主题的默认值
         * @return 从资源文件创建的主题
         */
        fun fromResources(context: android.content.Context, baseTheme: EchoDialogTheme = EchoDialogThemes.DEFAULT): EchoDialogTheme {
            val resources = context.resources
            val packageName = context.packageName
            
            // 辅助函数：尝试获取颜色资源，如果不存在返回 null
            fun getColorResource(name: String): Int? {
                return try {
                    val resId = resources.getIdentifier(name, "color", packageName)
                    if (resId != 0) {
                        ContextCompat.getColor(context, resId)
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }
            
            // 辅助函数：尝试获取 drawable 资源 ID，如果不存在返回 null
            fun getDrawableResource(name: String): Int? {
                return try {
                    val resId = resources.getIdentifier(name, "drawable", packageName)
                    if (resId != 0) resId else null
                } catch (e: Exception) {
                    null
                }
            }
            
            return EchoDialogTheme(
                dialogBackground = baseTheme.dialogBackground,
                dialogBackgroundColor = getColorResource("echo_dialog_bg") ?: baseTheme.dialogBackgroundColor,
                titleTextColor = getColorResource("echo_dialog_title") ?: baseTheme.titleTextColor,
                titleTextSize = baseTheme.titleTextSize,
                messageTextColor = getColorResource("echo_dialog_message") ?: baseTheme.messageTextColor,
                messageTextSize = baseTheme.messageTextSize,
                positiveButtonBackground = getDrawableResource("echo_dialog_positive_bg") 
                    ?: (getColorResource("echo_dialog_positive_bg")?.let { 
                        // 如果是颜色值，需要创建 drawable，这里先返回 null，后续可以扩展
                        null 
                    } ?: baseTheme.positiveButtonBackground),
                positiveButtonTextColor = getColorResource("echo_dialog_positive_text") ?: baseTheme.positiveButtonTextColor,
                positiveButtonTextSize = baseTheme.positiveButtonTextSize,
                negativeButtonBackground = getDrawableResource("echo_dialog_negative_bg")
                    ?: (getColorResource("echo_dialog_negative_bg")?.let { null } ?: baseTheme.negativeButtonBackground),
                negativeButtonTextColor = getColorResource("echo_dialog_negative_text") ?: baseTheme.negativeButtonTextColor,
                negativeButtonTextSize = baseTheme.negativeButtonTextSize,
                inputBackground = baseTheme.inputBackground,
                inputTextColor = getColorResource("echo_dialog_input_text") ?: baseTheme.inputTextColor,
                inputHintColor = getColorResource("echo_dialog_input_hint") ?: baseTheme.inputHintColor,
                seekBarProgressColor = getColorResource("echo_dialog_seekbar_progress") ?: baseTheme.seekBarProgressColor,
                seekBarThumbColor = getColorResource("echo_dialog_seekbar_thumb") ?: baseTheme.seekBarThumbColor,
                listItemTextColor = getColorResource("echo_dialog_list_item_text") ?: baseTheme.listItemTextColor,
                checkboxColor = getColorResource("echo_dialog_checkbox") ?: baseTheme.checkboxColor
            )
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
                dialogBackgroundColor = backgroundColor, // 使用颜色值而不是固定 drawable
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
