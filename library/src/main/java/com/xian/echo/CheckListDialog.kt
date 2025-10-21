package com.xian.echo

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.xian.echo.core.DialogConfig
import com.xian.echo.core.DialogResult.ChecklistSelection

class ChecklistDialog(private val context: Context) {
    
    private var dialog: Dialog? = null
    private val checkedItems = mutableSetOf<Int>()
    
    fun show(
        config: DialogConfig,
        items: List<String>,
        onResult: (ChecklistSelection) -> Unit
    ) {
        if (dialog == null) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_checklist, null)
            
            val title = dialogView.findViewById<TextView>(R.id.tv_title)
            val recyclerView = dialogView.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler)
            val positive = dialogView.findViewById<TextView>(R.id.btn_positive)
            val negative = dialogView.findViewById<TextView>(R.id.btn_negative)
            
            // 设置内容
            if (config.title.isNullOrEmpty()) {
                title.visibility = View.GONE
            } else {
                title.text = config.title
                title.visibility = View.VISIBLE
            }
            
            positive.text = config.positiveText ?: "确定"
            
            // 处理取消按钮
            if (config.negativeText.isNullOrEmpty()) {
                negative.visibility = View.GONE
            } else {
                negative.text = config.negativeText
                negative.visibility = View.VISIBLE
            }
            
            // 应用样式
            config.positiveBgResId?.let { positive.setBackgroundResource(it) }
            config.negativeBgResId?.let { negative.setBackgroundResource(it) }
            config.positiveTextColor?.let { positive.setTextColor(it) }
            config.negativeTextColor?.let { negative.setTextColor(it) }
            
            // 设置 RecyclerView
            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            val adapter = ChecklistAdapter(items) { position, isChecked ->
                if (isChecked) {
                    checkedItems.add(position)
                } else {
                    checkedItems.remove(position)
                }
            }
            recyclerView.adapter = adapter
            
            // 设置点击事件
            positive.setOnClickListener {
                val selectedIndices = checkedItems.sorted()
                val selectedItems = selectedIndices.map { items[it] }
                onResult(ChecklistSelection(selectedIndices, selectedItems))
                dismiss()
            }
            
            negative.setOnClickListener {
                dismiss()
            }
            
            // 创建并显示对话框
            dialog = Dialog(context).apply {
                setContentView(dialogView)
                setCancelable(config.cancelable)
                setCanceledOnTouchOutside(config.canceledOnTouchOutside)
                window?.setBackgroundDrawableResource(R.drawable.echo_dialog_background)
                
                // 确保在正确的时机显示对话框
                try {
                    show()
                } catch (e: Exception) {
                    // 如果无法显示对话框，清理资源
                    dialog = null
                    throw e
                }
            }
        }
    }
    
    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
    
    private inner class ChecklistAdapter(
        private val items: List<String>,
        private val onItemChecked: (Int, Boolean) -> Unit
    ) : androidx.recyclerview.widget.RecyclerView.Adapter<ChecklistAdapter.ViewHolder>() {
        
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checklist, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = items[position]
            holder.checkbox.isChecked = checkedItems.contains(position)
            
            holder.itemView.setOnClickListener {
                val newState = !holder.checkbox.isChecked
                holder.checkbox.isChecked = newState
                onItemChecked(position, newState)
            }
            
            holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onItemChecked(position, isChecked)
            }
        }
        
        override fun getItemCount(): Int = items.size
        
        inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
            val text: TextView = itemView.findViewById(R.id.text)
            val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        }
    }
}