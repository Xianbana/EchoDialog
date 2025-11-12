package com.xian.echo

import android.app.Dialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.xian.echo.EchoDialog
import com.xian.echo.core.DialogConfig
import com.xian.echo.core.ThemeApplier

class InputDialog(private val context: Context) {
    
    private var dialog: Dialog? = null
    
    fun show(
        config: DialogConfig,
        inputType: Int = InputType.TYPE_CLASS_TEXT,
        onResult: (String) -> Unit
    ) {
        if (dialog == null) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.input_dialog, null)
            
            val title = dialogView.findViewById<TextView>(R.id.tv_title)
            val input = dialogView.findViewById<EditText>(R.id.content)
            val positive = dialogView.findViewById<TextView>(R.id.btn_positive)
            val negative = dialogView.findViewById<TextView>(R.id.btn_negative)
            
            // 设置内容
            if (config.title.isNullOrEmpty()) {
                title.visibility = View.GONE
            } else {
                title.text = config.title
                title.visibility = View.VISIBLE
            }
            
            input.inputType = inputType
            config.inputHint?.let { input.hint = it }
            
            positive.text = config.positiveText ?: "确定"
            
            // 处理取消按钮
            if (config.negativeText.isNullOrEmpty()) {
                negative.visibility = View.GONE
            } else {
                negative.text = config.negativeText
                negative.visibility = View.VISIBLE
            }
            
            // 应用主题背景到根视图
            val theme = EchoDialog.getTheme()
            val rootView = dialogView.rootView ?: dialogView
            val backgroundDrawable = theme.createDialogBackground(context)
            if (backgroundDrawable != null) {
                rootView.background = backgroundDrawable
            }
            
            // 应用主题
            ThemeApplier.applyToTextView(title, theme)
            ThemeApplier.applyToEditText(input, theme)
            ThemeApplier.applyToButton(positive, theme, true)
            ThemeApplier.applyToButton(negative, theme, false)
            
            // 应用样式
            config.positiveBgResId?.let { positive.setBackgroundResource(it) }
            config.negativeBgResId?.let { negative.setBackgroundResource(it) }
            config.positiveTextColor?.let { positive.setTextColor(it) }
            config.negativeTextColor?.let { negative.setTextColor(it) }
            
            // 设置点击事件
            positive.setOnClickListener {
                onResult(input.text.toString())
                dismiss()
            }
            
            negative.setOnClickListener {
                dismiss()
            }
            
            // 创建并显示对话框
            dialog = Dialog(context).apply {
                setContentView(dialogView)
                setCancelable(config.cancelable)
                setCanceledOnTouchOutside(config.canceledOnTouchOutside)
                
                // 修复透明问题：先设置窗口背景为透明
                window?.setBackgroundDrawableResource(android.R.color.transparent)
                
                // 应用主题背景
                val backgroundDrawable = theme.createDialogBackground(context)
                if (backgroundDrawable != null) {
                    window?.setBackgroundDrawable(backgroundDrawable)
                } else {
                    // 如果没有主题背景，使用默认背景
                    window?.setBackgroundDrawableResource(R.drawable.echo_dialog_background)
                }
                
                // 确保在正确的时机显示对话框
                try {
                    show()
                } catch (e: Exception) {
                    // 如果无法显示对话框，清理资源
                    dialog = null
                    throw e
                }
            }
        }
    }
    
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}
