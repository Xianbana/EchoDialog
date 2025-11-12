package com.xian.echo

import android.os.Bundle
import android.os.Parcelable
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xian.echo.EchoDialog
import com.xian.echo.core.BaseDialogFragment
import com.xian.echo.core.DialogConfig
import com.xian.echo.core.DialogResult
import com.xian.echo.core.ThemeApplier

class ChecklistDialogFragment : BaseDialogFragment() {

    override lateinit var config: DialogConfig

    private lateinit var recyclerView: RecyclerView
    private lateinit var positive: TextView
    private lateinit var negative: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config = requireArguments().getParcelable(ARG_CONFIG)!!
    }

    override fun onCreateDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        val root = inflater.inflate(R.layout.dialog_checklist, container, false)
        recyclerView = root.findViewById(R.id.recycler)
        positive = root.findViewById(R.id.btn_positive)
        negative = root.findViewById(R.id.btn_negative)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val items = requireArguments().getStringArrayList(ARG_ITEMS) ?: arrayListOf()
        val adapter = ChecklistAdapter(items)
        recyclerView.adapter = adapter

        positive.text = config.positiveText ?: positive.text
        if (config.negativeText.isNullOrEmpty()) {
            negative.visibility = View.GONE
        } else {
            negative.text = config.negativeText
        }

        // 应用全局主题
        val theme = EchoDialog.getTheme()
        ThemeApplier.applyToButton(positive, theme, true)
        ThemeApplier.applyToButton(negative, theme, false)

        // 应用配置中的样式（优先级更高）
        config.positiveBgResId?.let { positive.setBackgroundResource(it) }
        config.negativeBgResId?.let { negative.setBackgroundResource(it) }
        config.positiveTextColor?.let { positive.setTextColor(it) }
        config.negativeTextColor?.let { negative.setTextColor(it) }

        positive.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY,
                bundleOf(KEY_RESULT to DialogResult.Selection(adapter.getCheckedIndices()) as Parcelable)
            )
            dismiss()
        }
        negative.setOnClickListener { dismiss() }

        return root
    }

    class ChecklistAdapter(private val items: List<String>) : RecyclerView.Adapter<ChecklistViewHolder>() {
        private val checked = HashSet<Int>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_checklist, parent, false)
            return ChecklistViewHolder(v)
        }

        override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
            val text = items[position]
            holder.text.text = text
            holder.text.movementMethod = LinkMovementMethod.getInstance()
            holder.checkbox.isChecked = checked.contains(position)
            
            // 应用全局主题
            val theme = EchoDialog.getTheme()
            ThemeApplier.applyToListItemText(holder.text, theme)
            ThemeApplier.applyToCheckBox(holder.checkbox, theme)
            holder.itemView.setOnClickListener {
                val newState = !holder.checkbox.isChecked
                holder.checkbox.isChecked = newState
                if (newState) checked.add(position) else checked.remove(position)
            }
            holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) checked.add(position) else checked.remove(position)
            }
        }

        override fun getItemCount(): Int = items.size

        fun getCheckedIndices(): List<Int> = checked.sorted()
    }

    class ChecklistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.text)
        val checkbox: android.widget.CheckBox = view.findViewById(R.id.checkbox)
    }

    companion object {
        const val REQUEST_KEY = "checklist_dialog_request"
        const val KEY_RESULT = "result"
        private const val ARG_CONFIG = "config"
        private const val ARG_ITEMS = "items"

        fun newInstance(config: DialogConfig, items: ArrayList<String>): ChecklistDialogFragment {
            val f = ChecklistDialogFragment()
            f.arguments = bundleOf(
                ARG_CONFIG to config,
                ARG_ITEMS to items
            )
            return f
        }
    }
}


