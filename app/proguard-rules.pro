# ---------- 通用保留项 ----------
# 保留注解、泛型签名等元数据
-keepattributes *Annotation*, Signature, Exception, SourceFile, LineNumberTable, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations, AnnotationDefault, EnclosingMethod
-keepattributes InnerClasses, EnclosingMethod

# 保留 Kotlin 元数据（反射相关）
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.reflect.**

# 保留 Parcelable（如果使用）
-keep class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# ---------- AndroidX / Jetpack Compose ----------
-keep class androidx.compose.** { *; }
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep class androidx.activity.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.savedstate.** { *; }
-keep class androidx.navigation.** { *; }

# Compose 编译器生成代码保留
-keep class * extends androidx.compose.runtime.ComposerKt { *; }
-keep class *$$androidx.compose.runtime.** { *; }

# ---------- Paging 3 ----------
-keep class androidx.paging.** { *; }
-dontwarn androidx.paging.**

# ---------- Room ----------
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
-keep class * extends androidx.room.RoomDatabase$Callback { *; }
-keep class * extends androidx.room.RoomDatabase$Migration { *; }
-keep class * implements androidx.room.RoomDatabase$PrepackagedDatabaseCallback { *; }

# ---------- Retrofit & OkHttp ----------
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class okio.** { *; }
-keep interface retrofit2.** { *; }
-keep interface okhttp3.** { *; }
-keep class com.squareup.okhttp3.** { *; }

# 保留 Retrofit 接口方法（运行时动态代理）
-keep interface * {
    @retrofit2.http.* <methods>;
}
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}
-dontwarn okhttp3.internal.platform.**
-dontwarn retrofit2.**

# ---------- Gson ----------
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# 保留使用了 @SerializedName 的字段
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
# 保留被 Gson 序列化的数据类（所有字段）
-keepclassmembers class * {
    *** *;
}
-keep class **.model.** { *; }

# ---------- Kotlinx.serialization ----------
-keep class kotlinx.serialization.** { *; }

-keep class **$$serializer { *; }
-keep @kotlinx.serialization.Serializable class *

# ---------- Coil ----------
-keep class coil.** { *; }
-keep class coil3.** { *; }
-keep class okio.** { *; }
-dontwarn coil.**
-dontwarn coil3.**

# ---------- Hilt ----------
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel
-keepclassmembers class * {
    @dagger.hilt.InstallIn <fields>;
    @dagger.hilt.android.lifecycle.HiltViewModel <methods>;
}
-keep class * extends androidx.hilt.work.HiltWorker
-keep class * extends dagger.hilt.components.SingletonComponent

# ---------- Lottie ----------
-keep class com.airbnb.lottie.** { *; }

# ---------- MMKV ----------
-keep class com.tencent.mmkv.** { *; }

# ---------- WorkManager ----------
-keep class androidx.work.** { *; }
-keep class * extends androidx.work.Worker

# ---------- SplashScreen ----------
-keep class androidx.core.splashscreen.** { *; }

# ---------- Retrofit 转换器 Kotlinx Serialization ----------
-keep class com.jakewharton.retrofit2.converter.kotlinx.serialization.** { *; }

# ---------- 其他 ----------
# 避免优化移除 Timber 等日志调用
-keep class timber.log.** { *; }
-dontwarn timber.log.**

# 保留所有自定义 View 的构造函数
-keepclassmembers class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# ---------- 可选：对 Compose UI 测试的调试支持 ----------
# 如果需要 UI 测试，取消注释以下行
# -keep class androidx.compose.ui.test.** { *; }
# -dontwarn androidx.compose.ui.test.**

# ---------- 调试建议 ----------
# 若遇到 ClassNotFoundException 或 MethodNotFoundException，可临时关闭混淆
# -dontobfuscate
# -dontshrink