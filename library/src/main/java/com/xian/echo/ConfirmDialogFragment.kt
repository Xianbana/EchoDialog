package com.xian.echo

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.os.Parcelable
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.xian.echo.EchoDialog
import com.xian.echo.core.BaseDialogFragment
import com.xian.echo.core.DialogConfig
import com.xian.echo.core.DialogResult
import com.xian.echo.core.ThemeApplier

class ConfirmDialogFragment : BaseDialogFragment() {

    override lateinit var config: DialogConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config = requireArguments().getParcelable(ARG_CONFIG)!!
    }

    override fun onCreateDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        val root = inflater.inflate(R.layout.dialog_confirm, container, false)

        val title = root.findViewById<TextView>(R.id.tv_title)
        val message = root.findViewById<TextView>(R.id.tv_message)
        val positive = root.findViewById<TextView>(R.id.btn_positive)
        val negative = root.findViewById<TextView>(R.id.btn_negative)

        title.text = config.title
        message.text = config.message
        
        // Handle rich text with clickable links
        if (config.links.isNotEmpty()) {
            val spannableString = SpannableString(config.message)
            var startIndex = 0
            
            for (link in config.links) {
                val linkStart = config.message?.indexOf(link.text, startIndex) ?: -1
                if (linkStart != -1) {
                    val linkEnd = linkStart + link.text.length
                    
                    val clickableSpan = object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            parentFragmentManager.setFragmentResult(
                                REQUEST_KEY,
                                bundleOf(KEY_RESULT to DialogResult.LinkClick(link.url, link.text) as Parcelable)
                            )
                        }
                    }
                    
                    spannableString.setSpan(clickableSpan, linkStart, linkEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    
                    // Apply custom color if specified
                    link.color?.let { color ->
                        spannableString.setSpan(
                            ForegroundColorSpan(color),
                            linkStart,
                            linkEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    
                    startIndex = linkEnd
                }
            }
            
            message.text = spannableString
            message.movementMethod = LinkMovementMethod.getInstance()
        }
        
        positive.text = config.positiveText ?: positive.text
        negative.text = config.negativeText ?: negative.text

        if (config.negativeText.isNullOrEmpty()) {
            negative.visibility = View.GONE
        } else {
            negative.visibility = View.VISIBLE
        }

        // 应用全局主题
        val theme = EchoDialog.getTheme()
        ThemeApplier.applyToTextView(title, theme)
        ThemeApplier.applyToMessageTextView(message, theme)
        ThemeApplier.applyToButton(positive, theme, true)
        ThemeApplier.applyToButton(negative, theme, false)

        // 应用配置中的样式（优先级更高）
        config.positiveBgResId?.let { positive.setBackgroundResource(it) }
        config.negativeBgResId?.let { negative.setBackgroundResource(it) }
        config.positiveTextColor?.let { positive.setTextColor(it) }
        config.negativeTextColor?.let { negative.setTextColor(it) }

        positive.setOnClickListener {
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESULT to DialogResult.Positive as Parcelable))
            dismiss()
        }
        negative.setOnClickListener {
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESULT to DialogResult.Negative as Parcelable))
            dismiss()
        }

        return root
    }

    companion object {
        const val REQUEST_KEY = "confirm_dialog_request"
        const val KEY_RESULT = "result"
        private const val ARG_CONFIG = "config"

        fun newInstance(config: DialogConfig): ConfirmDialogFragment {
            val f = ConfirmDialogFragment()
            f.arguments = bundleOf(ARG_CONFIG to config)
            return f
        }
    }
}


