package com.xian.echo

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView


class CustomDialog(private val context: Context) {

    // Builder模式用于创建不同类型的对话框
    class Builder(private val context: Context) {
        private var title: String = ""
        private var message: String = ""
        private var positiveButtonText: String = "确定"
        private var negativeButtonText: String = "取消"
        private var onPositiveClick: (() -> Unit)? = null
        private var onNegativeClick: (() -> Unit)? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setPositiveButton(text: String, onClick: (() -> Unit)? = null): Builder {
            this.positiveButtonText = text
            this.onPositiveClick = onClick
            return this
        }

        fun setNegativeButton(text: String, onClick: (() -> Unit)? = null): Builder {
            this.negativeButtonText = text
            this.onNegativeClick = onClick
            return this
        }

        fun show() {
            val dialog = Dialog(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
            
            dialogView.setBackgroundResource(R.drawable.layout_shape)
            
            val titleView = dialogView.findViewById<TextView>(R.id.tv_title)
            val messageView = dialogView.findViewById<TextView>(R.id.tv_message)
            val positiveButton = dialogView.findViewById<TextView>(R.id.btn_positive)
            val negativeButton = dialogView.findViewById<TextView>(R.id.btn_negative)
            
            titleView.text = title
            messageView.text = message
            positiveButton.text = positiveButtonText
            negativeButton.text = negativeButtonText
            
            positiveButton.setOnClickListener {
                onPositiveClick?.invoke()
                dialog.dismiss()
            }
            
            negativeButton.setOnClickListener {
                onNegativeClick?.invoke()
                dialog.dismiss()
            }
            
            dialog.apply {
                setContentView(dialogView)
                setCancelable(false)
                window?.setBackgroundDrawableResource(R.drawable.layout_shape)
                show()
            }
        }
    }
}
