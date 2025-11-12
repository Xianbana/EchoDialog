# EchoDialog

一个简洁、易用的 Android 对话框库，提供统一的 API 和主题配置。

## ✨ 特性

- 🎨 **统一主题配置** - 一次设置，全应用生效
- 🚀 **极简 API** - 一行代码显示对话框
- 📱 **多种对话框** - 确认、输入、选择列表、SeekBar
- 🎯 **类型安全** - 编译时检查，运行时稳定
- 🔧 **高度可定制** - 支持完全自定义样式
- 📦 **轻量级** - 无第三方依赖

## 📦 安装

### 1. 添加 JitPack 仓库

在项目根目录的 `build.gradle.kts` 中添加：

```kotlin
allprojects {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. 添加依赖

在 app 模块的 `build.gradle.kts` 中添加：

```kotlin
dependencies {
    implementation("com.github.Xianbana:EchoDialog:1.0.1")
}
```

## 🚀 快速开始

### 1. 初始化

在 `Application` 类中初始化：

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化 EchoDialog
        EchoDialog.init(this)
        
        // 设置主题
        EchoDialog.setLightTheme()
    }
}
```

### 2. 使用对话框

```kotlin
// 确认对话框
EchoDialog.showConfirm(
    context = this,
    title = "确认删除",
    message = "确定要删除这个项目吗？",
    onPositive = {
        // 用户点击确认
        Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show()
    }
)

// 输入对话框
EchoDialog.showInput(
    context = this,
    title = "输入邮箱",
    hint = "请输入邮箱地址",
    inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
    onResult = { text ->
        Toast.makeText(this, "邮箱: $text", Toast.LENGTH_SHORT).show()
    }
)

// SeekBar 对话框
EchoDialog.showSeekBar(
    context = this,
    title = "音量调节",
    minValue = 0,
    maxValue = 100,
    defaultValue = 50,
    onResult = { value ->
        Toast.makeText(this, "音量设置为: $value", Toast.LENGTH_SHORT).show()
    }
)

// 选择列表对话框
EchoDialog.showChecklist(
    context = this,
    title = "选择项目",
    items = listOf("选项1", "选项2", "选项3"),
    onResult = { selection ->
        val message = "选择了 ${selection.selectedIndices.size} 项:\n${selection.selectedItems.joinToString("\n")}"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
)
```

## 🎨 主题配置

### 预设主题

```kotlin
// 浅色主题
EchoDialog.setLightTheme()

// 深色主题
EchoDialog.setDarkTheme()

// Material Design 主题
EchoDialog.setMaterialTheme()
```

### 自定义主题

```kotlin
// 简单自定义
EchoDialog.setCustomTheme(
    primaryColor = Color.parseColor("#FF6200EE"),
    backgroundColor = Color.WHITE,
    textColor = Color.BLACK
)

// 品牌主题
EchoDialog.setBrandTheme(
    brandColor = Color.parseColor("#FF1976D2"),
    isDark = false
)

// 完全自定义
val customTheme = EchoDialogTheme(
    titleTextColor = Color.parseColor("#FF1976D2"),
    positiveButtonBackground = R.drawable.custom_button,
    seekBarProgressColor = Color.parseColor("#FF6200EE"),
    // ... 其他自定义属性
)
EchoDialog.setTheme(customTheme)
```

## 📚 API 文档

### EchoDialog 主要方法

| 方法 | 描述 |
|------|------|
| `init(context)` | 初始化 EchoDialog |
| `setTheme(theme)` | 设置全局主题 |
| `setLightTheme()` | 设置浅色主题 |
| `setDarkTheme()` | 设置深色主题 |
| `setMaterialTheme()` | 设置 Material Design 主题 |
| `setCustomTheme(...)` | 设置自定义主题 |
| `setBrandTheme(...)` | 设置品牌主题 |
| `updateTheme { ... }` | 更新部分颜色，保留其他设置 |
| `switchThemePreservingCustom(isDark)` | 智能切换深色/浅色主题，保留自定义设置 |
| `loadThemeFromResources(baseTheme)` | 从资源文件自动加载主题（推荐） |
| `getTheme()` | 获取当前主题 |

### 对话框方法

| 方法 | 描述 |
|------|------|
| `showConfirm(...)` | 显示确认对话框 |
| `showInput(...)` | 显示输入对话框 |
| `showSeekBar(...)` | 显示 SeekBar 对话框 |
| `showChecklist(...)` | 显示选择列表对话框 |

## 🎯 主题属性

### EchoDialogTheme 配置项

```kotlin
EchoDialogTheme(
    // 对话框背景
    dialogBackground = R.drawable.echo_dialog_background,
    dialogBackgroundColor = Color.parseColor("#FFFFFFFF"), // 背景颜色（优先级高于 dialogBackground）
    
    // 标题样式
    titleTextColor = Color.BLACK,
    titleTextSize = 18f,
    
    // 消息文本样式
    messageTextColor = Color.DKGRAY,
    messageTextSize = 14f,
    
    // 确定按钮样式
    positiveButtonBackground = R.drawable.text_shape,
    positiveButtonTextColor = Color.WHITE,
    positiveButtonTextSize = 16f,
    
    // 取消按钮样式
    negativeButtonBackground = R.drawable.text_cancel_shape,
    negativeButtonTextColor = Color.WHITE,
    negativeButtonTextSize = 16f,
    
    // 输入框样式
    inputBackground = R.drawable.edit_shape,
    inputTextColor = Color.BLACK,
    inputHintColor = Color.GRAY,
    
    // SeekBar样式
    seekBarProgressColor = Color.parseColor("#FF1976D2"),
    seekBarThumbColor = Color.parseColor("#FF1976D2"),
    
    // 列表项样式
    listItemTextColor = Color.BLACK,
    checkboxColor = Color.parseColor("#FF1976D2")
)
```

## 🎨 颜色自定义详解

### 可以设置的所有颜色项

你可以通过 `EchoDialogTheme` 自定义以下所有颜色：

#### 1. 对话框背景
- `dialogBackgroundColor: Int?` - 对话框背景颜色（颜色值，如 `Color.parseColor("#FF1E1E1E")`）
- `dialogBackground: Int?` - 对话框背景 drawable 资源 ID（优先级低于 dialogBackgroundColor）

#### 2. 文本颜色
- `titleTextColor: Int?` - 标题文本颜色
- `titleTextSize: Float?` - 标题文本大小（单位：sp）
- `messageTextColor: Int?` - 消息文本颜色
- `messageTextSize: Float?` - 消息文本大小（单位：sp）

#### 3. 按钮颜色
- `positiveButtonBackground: Int?` - 确定按钮背景（drawable 资源 ID）
- `positiveButtonTextColor: Int?` - 确定按钮文本颜色
- `positiveButtonTextSize: Float?` - 确定按钮文本大小（单位：sp）
- `negativeButtonBackground: Int?` - 取消按钮背景（drawable 资源 ID）
- `negativeButtonTextColor: Int?` - 取消按钮文本颜色
- `negativeButtonTextSize: Float?` - 取消按钮文本大小（单位：sp）

#### 4. 输入框颜色
- `inputBackground: Int?` - 输入框背景（drawable 资源 ID）
- `inputTextColor: Int?` - 输入框文本颜色
- `inputHintColor: Int?` - 输入框提示文本颜色

#### 5. SeekBar 颜色
- `seekBarProgressColor: Int?` - SeekBar 进度条颜色（颜色值）
- `seekBarThumbColor: Int?` - SeekBar 滑块颜色（颜色值）

#### 6. 列表项颜色
- `listItemTextColor: Int?` - 列表项文本颜色
- `checkboxColor: Int?` - 复选框颜色（颜色值）

### 颜色自定义方式

#### 方式1：使用 `updateTheme()` 方法（推荐）

这是最灵活的方式，可以只修改部分颜色，保留其他设置：

```kotlin
// 设置自定义颜色
EchoDialog.updateTheme { it.copy(
    dialogBackgroundColor = Color.parseColor("#FF2C2C2C"), // 深灰色背景
    titleTextColor = Color.parseColor("#FFFFFFFF"), // 白色标题
    messageTextColor = Color.parseColor("#FFCCCCCC"), // 浅灰色消息
    positiveButtonTextColor = Color.parseColor("#FF4CAF50"), // 绿色确定按钮
    negativeButtonTextColor = Color.parseColor("#FFFF5722"), // 红色取消按钮
    seekBarProgressColor = Color.parseColor("#FF2196F3") // 蓝色进度条
) }
```

#### 方式2：完全自定义主题

```kotlin
val customTheme = EchoDialogTheme(
    dialogBackgroundColor = Color.parseColor("#FF2C2C2C"),
    titleTextColor = Color.parseColor("#FFFFFFFF"),
    messageTextColor = Color.parseColor("#FFCCCCCC"),
    positiveButtonTextColor = Color.parseColor("#FF4CAF50"),
    negativeButtonTextColor = Color.parseColor("#FFFF5722"),
    seekBarProgressColor = Color.parseColor("#FF2196F3"),
    // ... 其他颜色
)
EchoDialog.setTheme(customTheme)
```

#### 方式3：基于现有主题修改

```kotlin
// 获取当前主题
val currentTheme = EchoDialog.getTheme()

// 创建修改后的主题（只修改部分颜色）
val modifiedTheme = currentTheme.copy(
    dialogBackgroundColor = Color.parseColor("#FF2C2C2C"),
    titleTextColor = Color.parseColor("#FFFFFFFF")
)
EchoDialog.setTheme(modifiedTheme)
```

### 深色/浅色模式切换

#### 问题：自定义颜色会被覆盖

当你调用 `setDarkTheme()` 或 `setLightTheme()` 时，会**完全替换**整个主题对象，之前设置的自定义颜色会被覆盖：

```kotlin
// 设置自定义颜色
EchoDialog.updateTheme { it.copy(
    dialogBackgroundColor = Color.parseColor("#FF2C2C2C"),
    titleTextColor = Color.parseColor("#FFFFFFFF")
) }

// ❌ 如果调用 setDarkTheme()，自定义颜色会被覆盖
EchoDialog.setDarkTheme() // 自定义设置丢失
```

#### 解决方案：使用 `switchThemePreservingCustom()`

使用新方法 `switchThemePreservingCustom()` 可以在切换深色/浅色模式时**保留你的自定义设置**：

```kotlin
// 设置自定义颜色
EchoDialog.updateTheme { it.copy(
    dialogBackgroundColor = Color.parseColor("#FF2C2C2C"), // 深灰色背景
    titleTextColor = Color.parseColor("#FFFFFFFF"), // 白色标题
    seekBarProgressColor = Color.parseColor("#FF2196F3") // 蓝色进度条
) }

// ✅ 切换深色/浅色模式时保留自定义设置
EchoDialog.switchThemePreservingCustom(isDark = true)
// 你的自定义颜色会被保留，其他颜色会使用深色/浅色主题的默认值
```

#### 完整示例：在 Switch 中切换主题

```kotlin
// 在 MainActivity 中
val themeSwitch = findViewById<SwitchCompat>(R.id.themeSwitch)

// 设置自定义颜色（可选）
EchoDialog.updateTheme { it.copy(
    dialogBackgroundColor = Color.parseColor("#FF2C2C2C"),
    titleTextColor = Color.parseColor("#FFFFFFFF"),
    seekBarProgressColor = Color.parseColor("#FF2196F3")
) }

// Switch 切换监听
themeSwitch.setOnCheckedChangeListener { _, isChecked ->
    // 使用新方法，保留自定义设置
    EchoDialog.switchThemePreservingCustom(isDark = isChecked)
}
```

### 优先级机制总结

| 方法 | 行为 | 是否保留自定义设置 |
|------|------|-------------------|
| `setDarkTheme()` / `setLightTheme()` | 完全替换主题 | ❌ 不保留 |
| `switchThemePreservingCustom(isDark)` | 智能合并主题 | ✅ 保留自定义设置 |
| `updateTheme { ... }` | 更新部分颜色 | ✅ 保留其他设置 |
| `setTheme(theme)` | 完全替换主题 | ❌ 不保留 |

### 颜色值格式

颜色值使用 Android 的 `Color` 类或 `Color.parseColor()` 方法：

```kotlin
// 方式1：使用 Color 常量
Color.WHITE
Color.BLACK
Color.parseColor("#FF1E1E1E") // ARGB 格式：Alpha-Red-Green-Blue

// 方式2：使用十六进制字符串
Color.parseColor("#FFFFFFFF") // 白色（完全不透明）
Color.parseColor("#FF1E1E1E") // 深灰色（完全不透明）
Color.parseColor("#80FFFFFF") // 半透明白色（50% 透明度）

// 方式3：使用 RGB 值
Color.rgb(30, 30, 30) // RGB(30, 30, 30)
Color.argb(255, 30, 30, 30) // ARGB(255, 30, 30, 30)
```

### 方式4：通过资源文件自动读取（推荐用于生产环境）

这是**最佳实践**，符合 Android 开发规范。你可以在应用的 `colors.xml` 中定义颜色，库会自动读取，系统会根据深色/浅色模式自动切换。

#### 步骤1：在应用中定义颜色资源

在 `app/src/main/res/values/colors.xml` 中定义浅色模式颜色：

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- 对话框背景色 -->
    <color name="echo_dialog_bg">#FFFFFFFF</color>
    
    <!-- 文本颜色 -->
    <color name="echo_dialog_title">#FF212121</color>
    <color name="echo_dialog_message">#FF424242</color>
    
    <!-- 按钮颜色 -->
    <color name="echo_dialog_positive_text">#FFFFFFFF</color>
    <color name="echo_dialog_negative_text">#FF333333</color>
    
    <!-- 输入框颜色 -->
    <color name="echo_dialog_input_text">#FF212121</color>
    <color name="echo_dialog_input_hint">#FF757575</color>
    
    <!-- SeekBar 颜色 -->
    <color name="echo_dialog_seekbar_progress">#FF1976D2</color>
    <color name="echo_dialog_seekbar_thumb">#FF1976D2</color>
    
    <!-- 列表项颜色 -->
    <color name="echo_dialog_list_item_text">#FF212121</color>
    <color name="echo_dialog_checkbox">#FF1976D2</color>
</resources>
```

在 `app/src/main/res/values-night/colors.xml` 中定义深色模式颜色：

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- 对话框背景色 -->
    <color name="echo_dialog_bg">#FF1E1E1E</color>
    
    <!-- 文本颜色 -->
    <color name="echo_dialog_title">#FFFFFFFF</color>
    <color name="echo_dialog_message">#FFB3B3B3</color>
    
    <!-- 按钮颜色 -->
    <color name="echo_dialog_positive_text">#FFFFFFFF</color>
    <color name="echo_dialog_negative_text">#FFFFFFFF</color>
    
    <!-- 输入框颜色 -->
    <color name="echo_dialog_input_text">#FFFFFFFF</color>
    <color name="echo_dialog_input_hint">#FF757575</color>
    
    <!-- SeekBar 颜色 -->
    <color name="echo_dialog_seekbar_progress">#FFBB86FC</color>
    <color name="echo_dialog_seekbar_thumb">#FFBB86FC</color>
    
    <!-- 列表项颜色 -->
    <color name="echo_dialog_list_item_text">#FFFFFFFF</color>
    <color name="echo_dialog_checkbox">#FFBB86FC</color>
</resources>
```

#### 步骤2：在代码中加载主题

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        EchoDialog.init(this)
        
        // 方式1：自动检测系统模式（推荐）
        // 系统会根据当前是深色还是浅色模式，自动选择对应的颜色
        EchoDialog.loadThemeFromResources()
        
        // 方式2：手动指定基础主题
        // 如果资源文件中没有定义颜色，使用 LIGHT 主题的默认值
        EchoDialog.loadThemeFromResources(baseTheme = EchoDialogThemes.LIGHT)
        
        // 方式3：指定深色主题作为基础
        EchoDialog.loadThemeFromResources(baseTheme = EchoDialogThemes.DARK)
    }
}
```

**工作原理**：
- 系统会自动根据当前是深色还是浅色模式，从 `values/colors.xml` 或 `values-night/colors.xml` 中读取对应的颜色
- 如果资源文件中定义了颜色，就使用资源文件中的颜色
- 如果资源文件中没有定义某个颜色，使用 `baseTheme` 参数的默认值
- 如果不指定 `baseTheme`，会自动检测系统模式，选择 LIGHT 或 DARK 主题作为基础

#### 支持的资源名称

| 资源名称 | 说明 | 是否必需 |
|---------|------|---------|
| `echo_dialog_bg` | 对话框背景颜色 | 否 |
| `echo_dialog_title` | 标题文本颜色 | 否 |
| `echo_dialog_message` | 消息文本颜色 | 否 |
| `echo_dialog_positive_text` | 确定按钮文本颜色 | 否 |
| `echo_dialog_negative_text` | 取消按钮文本颜色 | 否 |
| `echo_dialog_input_text` | 输入框文本颜色 | 否 |
| `echo_dialog_input_hint` | 输入框提示文本颜色 | 否 |
| `echo_dialog_seekbar_progress` | SeekBar 进度条颜色 | 否 |
| `echo_dialog_seekbar_thumb` | SeekBar 滑块颜色 | 否 |
| `echo_dialog_list_item_text` | 列表项文本颜色 | 否 |
| `echo_dialog_checkbox` | 复选框颜色 | 否 |

**注意**：
- 如果资源文件中定义了颜色，就使用资源文件中的颜色
- 如果没有定义，使用 `baseTheme` 参数的默认值
- 系统会根据当前是深色还是浅色模式，自动从 `values/colors.xml` 或 `values-night/colors.xml` 中选择对应的颜色

#### 优势

1. ✅ **自动适配深色/浅色模式** - 系统自动切换，无需手动处理
2. ✅ **符合 Android 开发规范** - 使用标准的资源系统
3. ✅ **易于维护** - 所有颜色集中管理
4. ✅ **支持多语言/多主题** - 可以轻松扩展支持更多主题变体

### 最佳实践

1. **推荐：使用资源文件方式**（生产环境）：
   ```kotlin
   EchoDialog.init(this)
   // 自动检测系统模式，从资源文件加载
   EchoDialog.loadThemeFromResources()
   ```

2. **需要运行时动态修改时使用 `updateTheme()`**：
   ```kotlin
   EchoDialog.updateTheme { it.copy(
       dialogBackgroundColor = Color.parseColor("#FF2C2C2C"),
       titleTextColor = Color.parseColor("#FFFFFFFF")
   ) }
   ```

3. **切换深色/浅色模式时使用 `switchThemePreservingCustom()`**：
   ```kotlin
   EchoDialog.switchThemePreservingCustom(isDark = true)
   ```

这样可以确保你的自定义颜色在切换主题时不会被覆盖。

## 🔧 混淆配置

在 `proguard-rules.pro` 中添加：

```proguard
# EchoDialog 混淆规则
-keep class com.xian.echo.** { *; }
-keep class com.xian.echo.core.** { *; }

# 保持 Parcelable 相关类
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保持 kotlinx.parcelize 注解
-keep @kotlinx.parcelize.Parcelize class * {
    *;
}
```

## 📄 许可证

```
MIT License

Copyright (c) 2024 EchoDialog

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📞 联系方式

如有问题，请通过以下方式联系：

- GitHub Issues: https://github.com/Xianbana/EchoDialog
- Email: [你的邮箱]

---

**EchoDialog** - 让 Android 对话框开发更简单！ 🚀
