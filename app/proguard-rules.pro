# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.conscrypt.**
-dontwarn retrofit2.Platform$Java8
-dontwarn com.google.**

# GSON
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class sun.misc.Unsafe { *; }

# AdJust
-keep class com.adjust.sdk.** { *; }
-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}

# Branch
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient { com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context); }
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info { java.lang.String getId(); boolean isLimitAdTrackingEnabled(); }
-keep public class io.branch.referral.* { public *; }
-dontwarn io.branch.referral.**

# Tenjin
-keep class com.tenjin.android.** { *; }

# HQSdk
-renamesourcefileattribute SourceFile
-keepattributes MethodParameters,Exceptions,InnerClasses,Signature,SourceFile,LineNumberTable,*Annotation*
-keepparameternames
-keep,allowoptimization class io.humanteq.hq_core.HQSdk {
    public <methods>;
}
-keep,allowoptimization class io.humanteq.hq_core.models.** { *; }
-keep,allowoptimization public interface io.humanteq.hq_core.interfaces.** { *; }
-keep,allowoptimization public interface io.humanteq.hq_core.services.** {
    public <fields>;
}
-keep,allowoptimization class io.humanteq.hq_core.IHqmUuid$** {
    public <fields>;
    public <methods>;
}
-keep,allowoptimization class io.humanteq.hq_core.IHqmUuid$Stub.** {
    public <fields>;
    public <methods>;
}
-keep,allowoptimization interface io.humanteq.hq_core.IHqmUuid$** { *; }
-keep @interface javax.annotation.Nullable
-keep @interface androidx.annotation.Nullable
-keepclassmembernames class io.humanteq.hq_core.HQSdk {
    public <methods>;
}
-keep public enum io.humanteq.hq_core.main.**{
    *;
}