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
    implementation("com.github.Xianbana:EchoDialog:1.0.0")
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
