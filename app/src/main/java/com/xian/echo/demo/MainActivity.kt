package com.xian.echo.demo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import com.xian.echo.ConfirmDialogFragment
import com.xian.echo.InputDialogFragment
import com.xian.echo.ChecklistDialogFragment
import com.xian.echo.core.DialogConfig
import com.xian.echo.core.DialogResult

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val menu = findViewById<RecyclerView>(R.id.menuRecycler)
        menu.layoutManager = LinearLayoutManager(this)
        val items = listOf("Confirm Dialog", "Input Dialog (number)", "Checklist Dialog")
        menu.adapter = object : RecyclerView.Adapter<MenuVH>() {
            override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): MenuVH {
                val v = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
                return MenuVH(v)
            }
            override fun onBindViewHolder(holder: MenuVH, position: Int) {
                holder.text.text = items[position]
                holder.itemView.setOnClickListener {
                    when (position) {
                        0 -> showConfirm()
                        1 -> showInputNumber()
                        2 -> showChecklist()
                    }
                }
            }
            override fun getItemCount(): Int = items.size
        }
    }

    private fun showConfirm() {
        val config = DialogConfig(
            title = "用户协议",
            message = "请阅读并同意我们的《隐私政策》和《用户协议》以继续使用服务",
            positiveText = "同意",
            negativeText = "拒绝",
            links = listOf(
                com.xian.echo.core.LinkInfo(
                    text = "《隐私政策》",
                    url = "https://example.com/privacy",
                    color = android.graphics.Color.BLUE
                ),
                com.xian.echo.core.LinkInfo(
                    text = "《用户协议》",
                    url = "https://example.com/terms",
                    color = android.graphics.Color.BLUE
                )
            )
        )
        val f = ConfirmDialogFragment.newInstance(config)
        supportFragmentManager.setFragmentResultListener(ConfirmDialogFragment.REQUEST_KEY, this) { _, b ->
            val result = b.get("result")
            when (result) {
                is com.xian.echo.core.DialogResult.Positive -> {
                    // 用户点击了"同意"
                    android.widget.Toast.makeText(this, "用户同意", android.widget.Toast.LENGTH_SHORT).show()
                }
                is com.xian.echo.core.DialogResult.Negative -> {
                    // 用户点击了"拒绝"
                    android.widget.Toast.makeText(this, "用户拒绝", android.widget.Toast.LENGTH_SHORT).show()
                }
                is com.xian.echo.core.DialogResult.LinkClick -> {
                    // 用户点击了链接
                    android.widget.Toast.makeText(this, "点击了链接: ${result.text} -> ${result.url}", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }
        f.show(supportFragmentManager, "confirm")
    }

    private fun showInputNumber() {
        val config = DialogConfig(
            positiveText = "确定",
            negativeText = "取消"
        )
        val f = InputDialogFragment.newInstance(config, android.text.InputType.TYPE_CLASS_NUMBER)
        supportFragmentManager.setFragmentResultListener(InputDialogFragment.REQUEST_KEY, this) { _, b ->
            val r = b.get("result")
            if (r is DialogResult.Input) {
                // use r.text
            }
        }
        f.show(supportFragmentManager, "input")
    }

    private fun showChecklist() {
        val config = DialogConfig(
            positiveText = "保存",
            negativeText = "取消"
        )
        val items = arrayListOf(
            "查看文档 https://example.com/docs",
            "查看协议 https://example.com/license",
            "更多示例 https://example.com/samples"
        )
        val f = ChecklistDialogFragment.newInstance(config, items)
        supportFragmentManager.setFragmentResultListener(ChecklistDialogFragment.REQUEST_KEY, this) { _, b ->
            val r = b.get("result")
            if (r is DialogResult.Selection) {
                // use r.indices
            }
        }
        f.show(supportFragmentManager, "checklist")
    }
}

class MenuVH(v: View) : RecyclerView.ViewHolder(v) {
    val text: TextView = v.findViewById(android.R.id.text1)
}