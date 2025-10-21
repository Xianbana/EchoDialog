# EchoDialog 混淆规则

# 保持 EchoDialog 核心类
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

# 保持 DialogResult 相关类
-keep class com.xian.echo.core.DialogResult { *; }
-keep class com.xian.echo.core.DialogResult$* { *; }

# 保持 EchoDialogTheme 相关类
-keep class com.xian.echo.core.EchoDialogTheme { *; }
-keep class com.xian.echo.core.EchoDialogThemes { *; }

# 保持对话框类
-keep class com.xian.echo.ConfirmDialog { *; }
-keep class com.xian.echo.InputDialog { *; }
-keep class com.xian.echo.SeekBarDialog { *; }
-keep class com.xian.echo.ChecklistDialog { *; }

# 保持 EchoDialog 主类
-keep class com.xian.echo.EchoDialog { *; }

# 保持 ThemeApplier 工具类
-keep class com.xian.echo.core.ThemeApplier { *; }

# 保持所有 public 方法
-keepclassmembers class com.xian.echo.** {
    public *;
}

# 保持所有 public 字段
-keepclassmembers class com.xian.echo.** {
    public <fields>;
}

# 保持枚举类
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持注解
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# 保持泛型信息
-keepattributes Signature

# 保持行号信息（用于调试）
-keepattributes SourceFile,LineNumberTable

# 保持异常信息
-keepattributes Exceptions