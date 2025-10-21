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
import com.xian.echo.SeekBarDialogFragment
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
            title = "输入数字",
            positiveText = "确定",
            negativeText = "取消",
            inputHint = "请输入数字"
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

    private fun showInputEmail() {
        val config = DialogConfig(
            title = "输入邮箱",
            positiveText = "确定",
            negativeText = "取消",
            inputHint = "请输入邮箱地址"
        )
        val f = InputDialogFragment.newInstance(
            config, 
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        )
        supportFragmentManager.setFragmentResultListener(InputDialogFragment.REQUEST_KEY, this) { _, b ->
            val r = b.get("result")
            if (r is DialogResult.Input) {
                android.widget.Toast.makeText(this, "邮箱: ${r.text}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        f.show(supportFragmentManager, "input_email")
    }

    private fun showInputPassword() {
        val config = DialogConfig(
            title = "输入密码",
            positiveText = "确定",
            negativeText = "取消",
            inputHint = "请输入密码"
        )
        val f = InputDialogFragment.newInstance(
            config, 
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        )
        supportFragmentManager.setFragmentResultListener(InputDialogFragment.REQUEST_KEY, this) { _, b ->
            val r = b.get("result")
            if (r is DialogResult.Input) {
                android.widget.Toast.makeText(this, "密码长度: ${r.text.length}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        f.show(supportFragmentManager, "input_password")
    }

    private fun showInputText() {
        val config = DialogConfig(
            title = "输入文本",
            positiveText = "确定",
            negativeText = "取消",
            inputHint = "请输入文本内容"
        )
        val f = InputDialogFragment.newInstance(
            config, 
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
        )
        supportFragmentManager.setFragmentResultListener(InputDialogFragment.REQUEST_KEY, this) { _, b ->
            val r = b.get("result")
            if (r is DialogResult.Input) {
                android.widget.Toast.makeText(this, "文本: ${r.text}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        f.show(supportFragmentManager, "input_text")
    }

    private fun showChecklist() {
        val config = DialogConfig(
            title = "选择项目",
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

    private fun showSeekBar() {
        val config = DialogConfig(
            title = "音量调节",
            positiveText = "确定",
            negativeText = "取消"
        )
        val f = SeekBarDialogFragment.newInstanceForVolume(config, 50)
        supportFragmentManager.setFragmentResultListener(SeekBarDialogFragment.REQUEST_KEY, this) { _, b ->
            val r = b.get("result")
            if (r is DialogResult.SeekBarValue) {
                android.widget.Toast.makeText(this, "音量设置为: ${r.value}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        f.show(supportFragmentManager, "seekbar")
    }
}