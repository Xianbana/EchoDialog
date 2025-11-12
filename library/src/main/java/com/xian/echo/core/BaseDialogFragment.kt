package com.xian.echo.core

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import com.xian.echo.EchoDialog
import com.xian.echo.R

abstract class BaseDialogFragment : DialogFragment() {

    protected abstract val config: DialogConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Defer reading config until onStart when subclasses have initialized it
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            // 修复透明问题：先设置窗口背景为透明
            window.setBackgroundDrawableResource(android.R.color.transparent)
            
            // 获取全局主题并应用背景
            val theme = EchoDialog.getTheme()
            val backgroundDrawable = theme.createDialogBackground(requireContext())
            if (backgroundDrawable != null) {
                window.setBackgroundDrawable(backgroundDrawable)
            } else {
                // 如果没有主题背景，使用默认背景
                window.setBackgroundDrawableResource(R.drawable.echo_dialog_background)
            }
            
            val params = window.attributes
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = params
        }
        // Apply configuration now that subclasses have initialized it
        isCancelable = config.cancelable
        dialog?.setCanceledOnTouchOutside(config.canceledOnTouchOutside)
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = onCreateDialogView(inflater, container)
        // 应用主题背景到根视图
        val theme = EchoDialog.getTheme()
        val backgroundDrawable = theme.createDialogBackground(requireContext())
        if (backgroundDrawable != null) {
            view.background = backgroundDrawable
        }
        return view
    }

    protected abstract fun onCreateDialogView(inflater: LayoutInflater, container: ViewGroup?): View
}


