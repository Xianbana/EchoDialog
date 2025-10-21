package com.xian.echo.core

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
        theme.seekBarProgressColor?.let { 
            seekBar.progressTintList = ContextCompat.getColorStateList(seekBar.context, it)
        }
        theme.seekBarThumbColor?.let { 
            seekBar.thumbTintList = ContextCompat.getColorStateList(seekBar.context, it)
        }
    }
    
    /**
     * 应用主题到CheckBox
     */
    fun applyToCheckBox(checkBox: CheckBox, theme: EchoDialogTheme) {
        theme.checkboxColor?.let { 
            checkBox.buttonTintList = ContextCompat.getColorStateList(checkBox.context, it)
        }
    }
    
    /**
     * 应用主题到列表项文本
     */
    fun applyToListItemText(textView: TextView, theme: EchoDialogTheme) {
        theme.listItemTextColor?.let { textView.setTextColor(it) }
    }
}
