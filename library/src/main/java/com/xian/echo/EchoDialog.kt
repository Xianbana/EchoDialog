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
    
    /**
     * 智能切换深色/浅色主题，保留用户的自定义设置
     * 如果当前主题有自定义颜色，这些颜色会被保留
     * 只有未自定义的颜色会使用预设主题的值
     * 
     * @param isDark true 切换到深色主题，false 切换到浅色主题
     */
    fun switchThemePreservingCustom(isDark: Boolean) {
        val currentTheme = globalTheme
        val baseTheme = if (isDark) EchoDialogThemes.DARK else EchoDialogThemes.LIGHT
        
        // 合并主题：保留用户自定义的设置，使用基础主题的默认值
        globalTheme = EchoDialogTheme(
            dialogBackground = currentTheme.dialogBackground ?: baseTheme.dialogBackground,
            dialogBackgroundColor = currentTheme.dialogBackgroundColor ?: baseTheme.dialogBackgroundColor,
            titleTextColor = currentTheme.titleTextColor ?: baseTheme.titleTextColor,
            titleTextSize = currentTheme.titleTextSize ?: baseTheme.titleTextSize,
            messageTextColor = currentTheme.messageTextColor ?: baseTheme.messageTextColor,
            messageTextSize = currentTheme.messageTextSize ?: baseTheme.messageTextSize,
            positiveButtonBackground = currentTheme.positiveButtonBackground ?: baseTheme.positiveButtonBackground,
            positiveButtonTextColor = currentTheme.positiveButtonTextColor ?: baseTheme.positiveButtonTextColor,
            positiveButtonTextSize = currentTheme.positiveButtonTextSize ?: baseTheme.positiveButtonTextSize,
            negativeButtonBackground = currentTheme.negativeButtonBackground ?: baseTheme.negativeButtonBackground,
            negativeButtonTextColor = currentTheme.negativeButtonTextColor ?: baseTheme.negativeButtonTextColor,
            negativeButtonTextSize = currentTheme.negativeButtonTextSize ?: baseTheme.negativeButtonTextSize,
            inputBackground = currentTheme.inputBackground ?: baseTheme.inputBackground,
            inputTextColor = currentTheme.inputTextColor ?: baseTheme.inputTextColor,
            inputHintColor = currentTheme.inputHintColor ?: baseTheme.inputHintColor,
            seekBarProgressColor = currentTheme.seekBarProgressColor ?: baseTheme.seekBarProgressColor,
            seekBarThumbColor = currentTheme.seekBarThumbColor ?: baseTheme.seekBarThumbColor,
            listItemTextColor = currentTheme.listItemTextColor ?: baseTheme.listItemTextColor,
            checkboxColor = currentTheme.checkboxColor ?: baseTheme.checkboxColor
        )
    }
    
    /**
     * 更新主题的部分颜色，保留其他设置
     * 
     * @param updateBlock 用于更新主题的 lambda，参数是当前主题的 copy
     * 
     * 示例：
     * ```kotlin
     * EchoDialog.updateTheme { it.copy(
     *     dialogBackgroundColor = Color.parseColor("#FF2C2C2C"),
     *     titleTextColor = Color.parseColor("#FFFFFFFF")
     * ) }
     * ```
     */
    fun updateTheme(updateBlock: (EchoDialogTheme) -> EchoDialogTheme) {
        globalTheme = updateBlock(globalTheme)
    }
    
    /**
     * 从资源文件自动加载主题
     * 会从应用的 values/colors.xml 和 values-night/colors.xml 中读取颜色
     * 如果资源文件中定义了颜色，就使用；如果没有定义，使用 baseTheme 的默认值
     * 
     * **系统会自动根据当前是深色还是浅色模式，从对应的资源文件中读取颜色**
     * 
     * 支持的资源名称（在 colors.xml 中定义）：
     * - `echo_dialog_bg`: 对话框背景颜色
     * - `echo_dialog_title`: 标题文本颜色
     * - `echo_dialog_message`: 消息文本颜色
     * - `echo_dialog_positive_text`: 确定按钮文本颜色
     * - `echo_dialog_negative_text`: 取消按钮文本颜色
     * - `echo_dialog_input_text`: 输入框文本颜色
     * - `echo_dialog_input_hint`: 输入框提示文本颜色
     * - `echo_dialog_seekbar_progress`: SeekBar 进度条颜色
     * - `echo_dialog_seekbar_thumb`: SeekBar 滑块颜色
     * - `echo_dialog_list_item_text`: 列表项文本颜色
     * - `echo_dialog_checkbox`: 复选框颜色
     * 
     * @param baseTheme 基础主题，如果资源文件中没有定义颜色，使用此主题的默认值。
     *                  如果为 null，会根据系统当前是深色还是浅色模式自动选择 LIGHT 或 DARK 主题
     * 
     * 示例：
     * ```kotlin
     * // 自动根据系统模式选择基础主题（推荐）
     * EchoDialog.loadThemeFromResources()
     * 
     * // 从资源文件加载，基于浅色主题
     * EchoDialog.loadThemeFromResources(baseTheme = EchoDialogThemes.LIGHT)
     * 
     * // 从资源文件加载，基于深色主题
     * EchoDialog.loadThemeFromResources(baseTheme = EchoDialogThemes.DARK)
     * ```
     */
    fun loadThemeFromResources(baseTheme: EchoDialogTheme? = null) {
        val context = getContext()
        val theme = baseTheme ?: run {
            // 自动检测系统是否为深色模式
            val isDarkMode = (context.resources.configuration.uiMode and 
                android.content.res.Configuration.UI_MODE_NIGHT_MASK) == 
                android.content.res.Configuration.UI_MODE_NIGHT_YES
            if (isDarkMode) EchoDialogThemes.DARK else EchoDialogThemes.LIGHT
        }
        globalTheme = EchoDialogTheme.fromResources(context, theme)
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
