package com.xian.echo

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.TextView


class SeekBarDialog(private val context: Context) {

    private var dialog: Dialog? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n", "InflateParams")
    fun show(onScaleChanged: (Int, Boolean) -> Unit) {
        if (dialog == null) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.seek_bar_dialog, null)

            // 设置背景为圆角样式
            dialogView.setBackgroundResource(R.drawable.layout_shape)

            dialog = Dialog(context).apply {
                setContentView(dialogView)
                setCancelable(true) // 可以取消
                window?.setBackgroundDrawableResource(R.drawable.layout_shape)
                show()
            }

            // 获取布局中的控件
            val confirmButton = dialogView.findViewById<TextView>(R.id.yes)
            val seekBar = dialogView.findViewById<SeekBar>(R.id.seekBar)
            val cancelButton = dialogView.findViewById<TextView>(R.id.cancel)
            val title = dialogView.findViewById<TextView>(R.id.title)



            // 设置SeekBar的范围 (0-100)
            seekBar.max = 100


            // 设置取消按钮点击事件
            cancelButton.setOnClickListener {
                dismiss()
            }

            // 设置确认按钮点击事件
            confirmButton.setOnClickListener {
                onScaleChanged(seekBar.progress, true) // true表示点击按钮
                dismiss()
            }

            // 设置SeekBar拖动监听
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    // 实时更新标题显示当前数值
                    title.text = "功能列表间隔: $progress"
                    // 实时更新间距预览（不保存到mmkv）
                    if (fromUser) {
                        onScaleChanged(progress, false) // false表示拖动
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // 开始拖动
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // 拖动结束，保存最终值
                    seekBar?.progress?.let { progress ->
                        onScaleChanged(progress, false) // false表示拖动
                    }
                }
            })
        }
    }

    // 关闭对话框
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}