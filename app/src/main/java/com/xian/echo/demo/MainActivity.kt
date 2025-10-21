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
import com.xian.echo.EchoDialog

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
        val items = listOf(
            "Confirm Dialog", 
            "Input Dialog (number)", 
            "Input Dialog (email)",
            "Input Dialog (password)",
            "Input Dialog (text)",
            "Checklist Dialog",
            "SeekBar Dialog"
        )
        
        menu.adapter = DialogMenuAdapter(items) { position ->
            // 使用 post 确保在 UI 完全准备好后再显示对话框
            menu.post {
                when (position) {
                    0 -> showConfirm()
                    1 -> showInputNumber()
                    2 -> showInputEmail()
                    3 -> showInputPassword()
                    4 -> showInputText()
                    5 -> showChecklist()
                    6 -> showSeekBar()
                }
            }
        }
    }

    private fun showConfirm() {
        EchoDialog.showConfirm(
            context = this,
            title = "用户协议",
            message = "请阅读并同意我们的《隐私政策》和《用户协议》以继续使用服务",
            positiveText = "同意",
            negativeText = "拒绝",
            onPositive = {
                android.widget.Toast.makeText(this, "用户同意", android.widget.Toast.LENGTH_SHORT).show()
            },
            onNegative = {
                android.widget.Toast.makeText(this, "用户拒绝", android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun showInputNumber() {
        EchoDialog.showInput(
            context = this,
            title = "输入数字",
            hint = "请输入数字",
            inputType = android.text.InputType.TYPE_CLASS_NUMBER,
            onResult = { text ->
                android.widget.Toast.makeText(this, "输入的数字: $text", android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun showInputEmail() {
        EchoDialog.showInput(
            context = this,
            title = "输入邮箱",
            hint = "请输入邮箱地址",
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
            onResult = { text ->
                android.widget.Toast.makeText(this, "邮箱: $text", android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun showInputPassword() {
        EchoDialog.showInput(
            context = this,
            title = "输入密码",
            hint = "请输入密码",
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD,
            onResult = { text ->
                android.widget.Toast.makeText(this, "密码长度: ${text.length}", android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun showInputText() {
        EchoDialog.showInput(
            context = this,
            title = "输入文本",
            hint = "请输入文本内容",
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE,
            onResult = { text ->
                android.widget.Toast.makeText(this, "文本: $text", android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun showChecklist() {
        val items = listOf(
            "查看文档 https://example.com/docs",
            "查看协议 https://example.com/license",
            "更多示例 https://example.com/samples"
        )
        EchoDialog.showChecklist(
            context = this,
            title = "选择项目",
            items = items,
            positiveText = "保存",
            onResult = { selection ->
                val message = "选择了 ${selection.selectedIndices.size} 项:\n${selection.selectedItems.joinToString("\n")}"
                android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun showSeekBar() {
        EchoDialog.showSeekBar(
            context = this,
            title = "音量调节",
            minValue = 0,
            maxValue = 100,
            defaultValue = 50,
            onResult = { value ->
                android.widget.Toast.makeText(this, "音量设置为: $value", android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }
}