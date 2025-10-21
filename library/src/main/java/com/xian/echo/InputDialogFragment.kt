package com.xian.echo

import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import com.xian.echo.core.BaseDialogFragment
import com.xian.echo.core.DialogConfig
import com.xian.echo.core.DialogResult

class InputDialogFragment : BaseDialogFragment() {

    override lateinit var config: DialogConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config = requireArguments().getParcelable(ARG_CONFIG)!!
    }

    override fun onCreateDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        val root = inflater.inflate(R.layout.input_dialog, container, false)

        val title = root.findViewById<TextView>(R.id.tv_title)
        val input = root.findViewById<EditText>(R.id.content)
        val positive = root.findViewById<TextView>(R.id.btn_positive)
        val negative = root.findViewById<TextView>(R.id.btn_negative)

        // Handle title visibility
        if (config.title.isNullOrEmpty()) {
            title.visibility = View.GONE
        } else {
            title.text = config.title
            title.visibility = View.VISIBLE
        }

        // Allow single-button mode
        if (config.negativeText.isNullOrEmpty()) {
            negative.visibility = View.GONE
        } else {
            negative.text = config.negativeText
            negative.visibility = View.VISIBLE
        }
        config.positiveText?.let { positive.text = it }

        config.positiveBgResId?.let { positive.setBackgroundResource(it) }
        config.negativeBgResId?.let { negative.setBackgroundResource(it) }
        config.positiveTextColor?.let { positive.setTextColor(it) }
        config.negativeTextColor?.let { negative.setTextColor(it) }

        val specifiedInputType = requireArguments().getInt(ARG_INPUT_TYPE, InputType.TYPE_CLASS_TEXT)
        input.inputType = specifiedInputType
        
        // 设置输入框提示文本
        config.inputHint?.let { hint ->
            input.hint = hint
        }

        positive.setOnClickListener {
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESULT to DialogResult.Input(input.text.toString()) as Parcelable))
            dismiss()
        }
        negative.setOnClickListener { dismiss() }

        return root
    }

    companion object {
        const val REQUEST_KEY = "input_dialog_request"
        const val KEY_RESULT = "result"
        private const val ARG_CONFIG = "config"
        private const val ARG_INPUT_TYPE = "inputType"

        fun newInstance(config: DialogConfig, inputType: Int): InputDialogFragment {
            val f = InputDialogFragment()
            f.arguments = bundleOf(
                ARG_CONFIG to config,
                ARG_INPUT_TYPE to inputType
            )
            return f
        }

        // 便捷方法：常用输入类型
        fun newInstanceForText(config: DialogConfig): InputDialogFragment {
            return newInstance(config, android.text.InputType.TYPE_CLASS_TEXT)
        }

        fun newInstanceForNumber(config: DialogConfig): InputDialogFragment {
            return newInstance(config, android.text.InputType.TYPE_CLASS_NUMBER)
        }

        fun newInstanceForEmail(config: DialogConfig): InputDialogFragment {
            return newInstance(config, android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        }

        fun newInstanceForPassword(config: DialogConfig): InputDialogFragment {
            return newInstance(config, android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }

        fun newInstanceForPhone(config: DialogConfig): InputDialogFragment {
            return newInstance(config, android.text.InputType.TYPE_CLASS_PHONE)
        }

        fun newInstanceForMultiLineText(config: DialogConfig): InputDialogFragment {
            return newInstance(config, android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        }

        // 带默认 hint 的便捷方法
        fun newInstanceForEmailWithHint(config: DialogConfig, hint: String = "请输入邮箱地址"): InputDialogFragment {
            val configWithHint = config.copy(inputHint = hint)
            return newInstance(configWithHint, android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        }

        fun newInstanceForPasswordWithHint(config: DialogConfig, hint: String = "请输入密码"): InputDialogFragment {
            val configWithHint = config.copy(inputHint = hint)
            return newInstance(configWithHint, android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }

        fun newInstanceForNumberWithHint(config: DialogConfig, hint: String = "请输入数字"): InputDialogFragment {
            val configWithHint = config.copy(inputHint = hint)
            return newInstance(configWithHint, android.text.InputType.TYPE_CLASS_NUMBER)
        }
    }
}


