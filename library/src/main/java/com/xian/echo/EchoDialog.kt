package com.xian.echo

import android.content.Context
import com.xian.echo.core.DialogConfig
import com.xian.echo.core.DialogResult.ChecklistSelection
import com.xian.echo.core.EchoDialogTheme
import com.xian.echo.core.EchoDialogThemes

/**
 * EchoDialog 全局管理器
 * 使用前需要在 Application 中调用 EchoDialog.init(context)
 */
object EchoDialog {
    
    private var appContext: Context? = null
    private var globalTheme: EchoDialogTheme = EchoDialogThemes.DEFAULT
    
    /**
     * 初始化 EchoDialog
     * 在 Application.onCreate() 中调用
     */
    fun init(context: Context) {
        this.appContext = context.applicationContext
    }
    
    /**
     * 设置全局主题
     */
    fun setTheme(theme: EchoDialogTheme) {
        this.globalTheme = theme
    }
    
    /**
     * 获取当前主题
     */
    fun getTheme(): EchoDialogTheme = globalTheme
    
    /**
     * 设置浅色主题
     */
    fun setLightTheme() {
        this.globalTheme = EchoDialogThemes.LIGHT
    }
    
    /**
     * 设置深色主题
     */
    fun setDarkTheme() {
        this.globalTheme = EchoDialogThemes.DARK
    }
    
    /**
     * 设置 Material Design 主题
     */
    fun setMaterialTheme() {
        this.globalTheme = EchoDialogThemes.MATERIAL
    }
    
    /**
     * 设置自定义主题
     */
    fun setCustomTheme(primaryColor: Int, backgroundColor: Int = android.graphics.Color.WHITE, textColor: Int = android.graphics.Color.BLACK) {
        this.globalTheme = EchoDialogThemes.createCustom(primaryColor, backgroundColor, textColor)
    }
    
    /**
     * 设置品牌主题
     */
    fun setBrandTheme(brandColor: Int, isDark: Boolean = false) {
        this.globalTheme = EchoDialogThemes.createBrand(brandColor, isDark)
    }
    
    private fun getContext(): Context {
        return appContext ?: throw IllegalStateException("EchoDialog not initialized. Call EchoDialog.init(context) in Application.onCreate()")
    }
    
    /**
     * 显示确认对话框
     * @param context Activity Context
     * @param title 标题
     * @param message 消息内容
     * @param positiveText 确认按钮文本，默认为"确定"
     * @param negativeText 取消按钮文本，默认为"取消"
     * @param onPositive 点击确认时的回调
     * @param onNegative 点击取消时的回调，可选
     */
    fun showConfirm(
        context: Context,
        title: String,
        message: String,
        positiveText: String = "确定",
        negativeText: String = "取消",
        onPositive: () -> Unit,
        onNegative: (() -> Unit)? = null
    ) {
        val config = DialogConfig(
            title = title,
            message = message,
            positiveText = positiveText,
            negativeText = negativeText
        )
        ConfirmDialog(context).show(config, globalTheme, onPositive, onNegative)
    }
    
    /**
     * 显示输入对话框
     * @param context Activity Context
     * @param title 标题
     * @param hint 输入框提示文本
     * @param positiveText 确认按钮文本，默认为"确定"
     * @param negativeText 取消按钮文本，默认为"取消"
     * @param inputType 输入类型，默认为文本输入
     * @param onResult 输入结果回调，参数为输入的文本
     */
    fun showInput(
        context: Context,
        title: String,
        hint: String = "",
        positiveText: String = "确定",
        negativeText: String = "取消",
        inputType: Int = android.text.InputType.TYPE_CLASS_TEXT,
        onResult: (String) -> Unit
    ) {
        val config = DialogConfig(
            title = title,
            inputHint = hint,
            positiveText = positiveText,
            negativeText = negativeText
        )
        InputDialog(context).show(config, inputType, onResult)
    }
    
    /**
     * 显示 SeekBar 对话框
     * @param context Activity Context
     * @param title 标题
     * @param minValue 最小值，默认为 0
     * @param maxValue 最大值，默认为 100
     * @param defaultValue 默认值，默认为 0
     * @param step 步长，默认为 1
     * @param positiveText 确认按钮文本，默认为"确定"
     * @param negativeText 取消按钮文本，默认为"取消"
     * @param onResult 结果回调，参数为选择的值
     */
    fun showSeekBar(
        context: Context,
        title: String,
        minValue: Int = 0,
        maxValue: Int = 100,
        defaultValue: Int = 0,
        step: Int = 1,
        positiveText: String = "确定",
        negativeText: String = "取消",
        onResult: (Int) -> Unit
    ) {
        val config = DialogConfig(
            title = title,
            positiveText = positiveText,
            negativeText = negativeText
        )
        SeekBarDialog(context).show(config, minValue, maxValue, defaultValue, step, onResult)
    }
    
    /**
     * 显示选择列表对话框
     * @param context Activity Context
     * @param title 标题
     * @param items 选项列表
     * @param positiveText 确认按钮文本，默认为"确定"
     * @param negativeText 取消按钮文本，默认为"取消"
     * @param onResult 结果回调，参数为选中的索引列表
     */
    fun showChecklist(
        context: Context,
        title: String,
        items: List<String>,
        positiveText: String = "确定",
        negativeText: String = "取消",
        onResult: (ChecklistSelection) -> Unit
    ) {
        val config = DialogConfig(
            title = title,
            positiveText = positiveText,
            negativeText = negativeText
        )
        ChecklistDialog(context).show(config, items, onResult)
    }
}
