package com.xian.echo.core

import android.content.res.ColorStateList
import android.widget.TextView
import android.widget.EditText
import android.widget.SeekBar
import android.widget.CheckBox
import androidx.core.content.ContextCompat

/**
 * 主题应用工具类
 * 用于将主题配置应用到具体的UI组件
 */
object ThemeApplier {
    
    /**
     * 应用主题到TextView
     */
    fun applyToTextView(textView: TextView, theme: EchoDialogTheme) {
        theme.titleTextColor?.let { textView.setTextColor(it) }
        theme.titleTextSize?.let { textView.textSize = it }
    }
    
    /**
     * 应用主题到消息TextView
     */
    fun applyToMessageTextView(textView: TextView, theme: EchoDialogTheme) {
        theme.messageTextColor?.let { textView.setTextColor(it) }
        theme.messageTextSize?.let { textView.textSize = it }
    }
    
    /**
     * 应用主题到按钮
     */
    fun applyToButton(textView: TextView, theme: EchoDialogTheme, isPositive: Boolean) {
        if (isPositive) {
            theme.positiveButtonBackground?.let { textView.setBackgroundResource(it) }
            theme.positiveButtonTextColor?.let { textView.setTextColor(it) }
            theme.positiveButtonTextSize?.let { textView.textSize = it }
        } else {
            theme.negativeButtonBackground?.let { textView.setBackgroundResource(it) }
            theme.negativeButtonTextColor?.let { textView.setTextColor(it) }
            theme.negativeButtonTextSize?.let { textView.textSize = it }
        }
    }
    
    /**
     * 应用主题到输入框
     */
    fun applyToEditText(editText: EditText, theme: EchoDialogTheme) {
        theme.inputBackground?.let { editText.setBackgroundResource(it) }
        theme.inputTextColor?.let { editText.setTextColor(it) }
        theme.inputHintColor?.let { editText.setHintTextColor(it) }
    }
    
    /**
     * 应用主题到SeekBar
     */
    fun applyToSeekBar(seekBar: SeekBar, theme: EchoDialogTheme) {
        theme.seekBarProgressColor?.let { color ->
            // 尝试作为资源 ID 获取，如果失败则作为颜色值使用
            try {
                seekBar.progressTintList = ContextCompat.getColorStateList(seekBar.context, color)
            } catch (e: android.content.res.Resources.NotFoundException) {
                // 不是资源 ID，直接使用颜色值
                seekBar.progressTintList = ColorStateList.valueOf(color)
            } catch (e: Exception) {
                // 其他异常，也使用颜色值
                seekBar.progressTintList = ColorStateList.valueOf(color)
            }
        }
        theme.seekBarThumbColor?.let { color ->
            // 尝试作为资源 ID 获取，如果失败则作为颜色值使用
            try {
                seekBar.thumbTintList = ContextCompat.getColorStateList(seekBar.context, color)
            } catch (e: android.content.res.Resources.NotFoundException) {
                // 不是资源 ID，直接使用颜色值
                seekBar.thumbTintList = ColorStateList.valueOf(color)
            } catch (e: Exception) {
                // 其他异常，也使用颜色值
                seekBar.thumbTintList = ColorStateList.valueOf(color)
            }
        }
    }
    
    /**
     * 应用主题到CheckBox
     */
    fun applyToCheckBox(checkBox: CheckBox, theme: EchoDialogTheme) {
        theme.checkboxColor?.let { color ->
            // 尝试作为资源 ID 获取，如果失败则作为颜色值使用
            try {
                checkBox.buttonTintList = ContextCompat.getColorStateList(checkBox.context, color)
            } catch (e: android.content.res.Resources.NotFoundException) {
                // 不是资源 ID，直接使用颜色值
                checkBox.buttonTintList = ColorStateList.valueOf(color)
            } catch (e: Exception) {
                // 其他异常，也使用颜色值
                checkBox.buttonTintList = ColorStateList.valueOf(color)
            }
        }
    }
    
    /**
     * 应用主题到列表项文本
     */
    fun applyToListItemText(textView: TextView, theme: EchoDialogTheme) {
        theme.listItemTextColor?.let { textView.setTextColor(it) }
    }
}
