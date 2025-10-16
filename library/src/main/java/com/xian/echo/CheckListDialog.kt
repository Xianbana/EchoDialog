package com.xian.echo

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.TextView


class CheckListDialog(private val context: Context) {

    private var dialog: Dialog? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n", "InflateParams")
    fun show(onSave: () -> Unit) {
        if (dialog == null) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.check_list_dialog, null)

            // 设置背景为圆角样式
            dialogView.setBackgroundResource(R.drawable.layout_shape)

            dialog = Dialog(context).apply {
                setContentView(dialogView)
                setCancelable(false) // 不可取消
                window?.setBackgroundDrawableResource(R.drawable.layout_shape)
                show()
            }

            // 获取布局中的控件
            val saveButton = dialogView.findViewById<TextView>(R.id.save)
            val cancelButton = dialogView.findViewById<TextView>(R.id.cancel)
            val checkboxName = dialogView.findViewById<CheckBox>(R.id.checkbox_name)
            val checkboxSize = dialogView.findViewById<CheckBox>(R.id.checkbox_size)
            val checkboxResolution = dialogView.findViewById<CheckBox>(R.id.checkbox_resolution)



            cancelButton.setOnClickListener {
                dismiss()
            }
            
            // 设置保存按钮点击事件
            saveButton.setOnClickListener {

                onSave()
                dismiss()
            }
        }
    }

    // 关闭对话框
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}