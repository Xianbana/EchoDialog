package com.xian.echo

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import com.xian.echo.core.DialogConfig

class SeekBarDialog(private val context: Context) {
    
    private var dialog: Dialog? = null
    
    fun show(
        config: DialogConfig,
        minValue: Int = 0,
        maxValue: Int = 100,
        defaultValue: Int = 0,
        step: Int = 1,
        onResult: (Int) -> Unit
    ) {
        if (dialog == null) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.seek_bar_dialog, null)
            
            val title = dialogView.findViewById<TextView>(R.id.title)
            val seekBar = dialogView.findViewById<SeekBar>(R.id.seekBar)
            val valueText = dialogView.findViewById<TextView>(R.id.valueText)
            val positive = dialogView.findViewById<TextView>(R.id.btn_positive)
            val negative = dialogView.findViewById<TextView>(R.id.btn_negative)
            
            // 设置内容
            if (config.title.isNullOrEmpty()) {
                title.visibility = View.GONE
            } else {
                title.text = config.title
                title.visibility = View.VISIBLE
            }
            
            positive.text = config.positiveText ?: "确定"
            
            // 处理取消按钮
            if (config.negativeText.isNullOrEmpty()) {
                negative.visibility = View.GONE
            } else {
                negative.text = config.negativeText
                negative.visibility = View.VISIBLE
            }
            
            // 应用样式
            config.positiveBgResId?.let { positive.setBackgroundResource(it) }
            config.negativeBgResId?.let { negative.setBackgroundResource(it) }
            config.positiveTextColor?.let { positive.setTextColor(it) }
            config.negativeTextColor?.let { negative.setTextColor(it) }
            
            // 配置 SeekBar
            seekBar.max = (maxValue - minValue) / step
            seekBar.progress = (defaultValue - minValue) / step
            updateValueText(valueText, minValue, step)
            
            // 设置 SeekBar 监听
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val actualValue = minValue + (progress * step)
                    updateValueText(valueText, actualValue, step)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            
            // 设置点击事件
            positive.setOnClickListener {
                val currentValue = minValue + (seekBar.progress * step)
                onResult(currentValue)
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
                window?.setBackgroundDrawableResource(R.drawable.echo_dialog_background)
                
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
    
    private fun updateValueText(valueText: TextView, value: Int, step: Int) {
        valueText.text = value.toString()
    }
    
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}
