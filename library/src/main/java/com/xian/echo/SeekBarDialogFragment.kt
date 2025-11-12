package com.xian.echo

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.os.bundleOf
import com.xian.echo.EchoDialog
import com.xian.echo.core.BaseDialogFragment
import com.xian.echo.core.DialogConfig
import com.xian.echo.core.DialogResult
import com.xian.echo.core.ThemeApplier

class SeekBarDialogFragment : BaseDialogFragment() {

    override lateinit var config: DialogConfig

    private lateinit var seekBar: SeekBar
    private lateinit var valueText: TextView
    private lateinit var positive: TextView
    private lateinit var negative: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config = requireArguments().getParcelable(ARG_CONFIG)!!
    }

    override fun onCreateDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        val root = inflater.inflate(R.layout.seek_bar_dialog, container, false)

        val title = root.findViewById<TextView>(R.id.title)
        seekBar = root.findViewById(R.id.seekBar)
        valueText = root.findViewById(R.id.valueText)
        positive = root.findViewById(R.id.btn_positive)
        negative = root.findViewById(R.id.btn_negative)

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

        // 应用全局主题
        val theme = EchoDialog.getTheme()
        ThemeApplier.applyToTextView(title, theme)
        ThemeApplier.applyToSeekBar(seekBar, theme)
        ThemeApplier.applyToButton(positive, theme, true)
        ThemeApplier.applyToButton(negative, theme, false)

        // 应用配置中的样式（优先级更高）
        config.positiveBgResId?.let { positive.setBackgroundResource(it) }
        config.negativeBgResId?.let { negative.setBackgroundResource(it) }
        config.positiveTextColor?.let { positive.setTextColor(it) }
        config.negativeTextColor?.let { negative.setTextColor(it) }

        // Configure SeekBar
        val minValue = requireArguments().getInt(ARG_MIN_VALUE, 0)
        val maxValue = requireArguments().getInt(ARG_MAX_VALUE, 100)
        val defaultValue = requireArguments().getInt(ARG_DEFAULT_VALUE, minValue)
        val step = requireArguments().getInt(ARG_STEP, 1)

        seekBar.max = (maxValue - minValue) / step
        seekBar.progress = (defaultValue - minValue) / step
        updateValueText(minValue, step)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val actualValue = minValue + (progress * step)
                updateValueText(actualValue, step)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        positive.setOnClickListener {
            val currentValue = minValue + (seekBar.progress * step)
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY, 
                bundleOf(KEY_RESULT to DialogResult.SeekBarValue(currentValue) as Parcelable)
            )
            dismiss()
        }
        negative.setOnClickListener { dismiss() }

        return root
    }

    private fun updateValueText(value: Int, step: Int) {
        if (valueText != null) {
            valueText.text = value.toString()
        }
    }

    companion object {
        const val REQUEST_KEY = "seekbar_dialog_request"
        const val KEY_RESULT = "result"
        private const val ARG_CONFIG = "config"
        private const val ARG_MIN_VALUE = "minValue"
        private const val ARG_MAX_VALUE = "maxValue"
        private const val ARG_DEFAULT_VALUE = "defaultValue"
        private const val ARG_STEP = "step"

        fun newInstance(
            config: DialogConfig,
            minValue: Int = 0,
            maxValue: Int = 100,
            defaultValue: Int = 0,
            step: Int = 1
        ): SeekBarDialogFragment {
            val f = SeekBarDialogFragment()
            f.arguments = bundleOf(
                ARG_CONFIG to config,
                ARG_MIN_VALUE to minValue,
                ARG_MAX_VALUE to maxValue,
                ARG_DEFAULT_VALUE to defaultValue,
                ARG_STEP to step
            )
            return f
        }

        // 便捷方法：音量调节
        fun newInstanceForVolume(config: DialogConfig, currentVolume: Int = 50): SeekBarDialogFragment {
            return newInstance(config, 0, 100, currentVolume, 1)
        }

        // 便捷方法：亮度调节
        fun newInstanceForBrightness(config: DialogConfig, currentBrightness: Int = 50): SeekBarDialogFragment {
            return newInstance(config, 0, 100, currentBrightness, 1)
        }

        // 便捷方法：温度调节
        fun newInstanceForTemperature(config: DialogConfig, currentTemp: Int = 20): SeekBarDialogFragment {
            return newInstance(config, 10, 40, currentTemp, 1)
        }
    }
}
