package com.xian.echo

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView


class InputDialog(private val context: Context) {

    private var dialog: Dialog? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n", "InflateParams")
    fun show(onYes: (Int) -> Unit) {
        if (dialog == null) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.input_dialog, null)

            // 设置背景为圆角样式
            dialogView.setBackgroundResource(R.drawable.layout_shape)

            dialog = Dialog(context).apply {
                setContentView(dialogView)
                setCancelable(false) // 不可取消
                window?.setBackgroundDrawableResource(R.drawable.layout_shape)
                show()
            }

            // 获取布局中的控件
            val yesButton = dialogView.findViewById<TextView>(R.id.yes)
            val cancelButton = dialogView.findViewById<TextView>(R.id.cancel)
            val content = dialogView.findViewById<EditText>(R.id.content)


            cancelButton.setOnClickListener {
                dismiss()
            }
            // 设置按钮点击事件
            yesButton.setOnClickListener {
                val res = content.text.toString()
                if (res.isNotEmpty()) {
                    onYes(res.toInt())
                    dismiss()
                }
            }
        }
    }

    // 关闭对话框
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}