package com.xian.echo.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
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
            window.setBackgroundDrawableResource(R.drawable.echo_dialog_background)
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
        return onCreateDialogView(inflater, container)
    }

    protected abstract fun onCreateDialogView(inflater: LayoutInflater, container: ViewGroup?): View
}


