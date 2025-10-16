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

        val input = root.findViewById<EditText>(R.id.content)
        val positive = root.findViewById<TextView>(R.id.btn_positive)
        val negative = root.findViewById<TextView>(R.id.btn_negative)

        // Allow single-button mode
        if (config.negativeText.isNullOrEmpty()) {
            negative.visibility = View.GONE
        } else {
            negative.text = config.negativeText
        }
        config.positiveText?.let { positive.text = it }

        config.positiveBgResId?.let { positive.setBackgroundResource(it) }
        config.negativeBgResId?.let { negative.setBackgroundResource(it) }
        config.positiveTextColor?.let { positive.setTextColor(it) }
        config.negativeTextColor?.let { negative.setTextColor(it) }

        val specifiedInputType = requireArguments().getInt(ARG_INPUT_TYPE, InputType.TYPE_CLASS_TEXT)
        input.inputType = specifiedInputType

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
    }
}


