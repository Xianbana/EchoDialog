package com.xian.echo.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DialogMenuAdapter(
    private val items: List<String>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<DialogMenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialog_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.tv_title)
        private val descText: TextView = itemView.findViewById(R.id.tv_description)

        fun bind(item: String, position: Int) {
            val (title, description) = getTitleAndDescription(item)
            titleText.text = title
            descText.text = description
            
            itemView.setOnClickListener {
                onItemClick(position)
            }
        }

        private fun getTitleAndDescription(item: String): Pair<String, String> {
            return when (item) {
                "Confirm Dialog" -> Pair("确认对话框", "支持富文本和超链接点击")
                "Input Dialog (number)" -> Pair("数字输入", "数字键盘输入")
                "Input Dialog (email)" -> Pair("邮箱输入", "邮箱格式验证")
                "Input Dialog (password)" -> Pair("密码输入", "隐藏输入内容")
                "Input Dialog (text)" -> Pair("文本输入", "多行文本输入")
                "Checklist Dialog" -> Pair("列表选择", "多选列表对话框")
                else -> Pair(item, "点击查看详情")
            }
        }
    }
}

