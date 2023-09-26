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

-dontwarn com.google.dagger.**

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#-----------------OKhttp----------------------------------------
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

#--------------Retrofit----------------------------------

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

#--------------------------------------------------------

-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

#----------------------------------------------------------

-keep class androidx.viewpager.widget.ViewPager** { *; }
-keep interface androidx.viewpager.widget.ViewPager** { *; }

-keep class androidx.fragment.app.** { *; }
-keep interface androidx.fragment.app.** { *; }

-keep class android.widget.LinearLayout** { *; }
-keep interface  android.widget.LinearLayout** { *; }

-keep class androidx.recyclerview.widget.** { *; }
-keep interface androidx.recyclerview.widget.** { *; }

-keep class androidx.databinding.** { *; }
-keep interface androidx.databinding** { *; }

#----------------------------------------------------------

-keep class com.reconosersdk.reconosersdk.http.OlimpiaInterface** { *; }

-keepclassmembers class com.reconosersdk.reconosersdk.http.OlimpiaInterface** { *; }

-keep class com.reconosersdk.reconosersdk.ui.questions.** { *; }

-keep class com.reconosersdk.reconosersdk.http.OlimpiaService** { *; }

-keep class com.reconosersdk.reconosersdk.http.ServiceOlimpiaApi** { *; }

-keep class com.reconosersdk.reconosersdk.ui.** { *; }

-keep class com.reconosersdk.reconosersdk.http.olimpiait.entities.** { *; }

-keep class com.reconosersdk.reconosersdk.http.olimpiait.entities.in.** { *; }

-keep class com.reconosersdk.reconosersdk.http.olimpiait.entities.out.** { *; }

-keep class com.reconosersdk.reconosersdk.http.regula.entities.out.** { *; }

-keep class com.reconosersdk.reconosersdk.http.validateine.** { *; }

-keep class com.reconosersdk.reconosersdk.http.consultAgreementProcess.** { *; }

-keep class com.reconosersdk.reconosersdk.http.consultSteps.** { *; }

-keep class com.reconosersdk.reconosersdk.http.consultValidation.** { *; }

-keep class com.reconosersdk.reconosersdk.http.requestValidation.** { *; }

-keep class com.reconosersdk.reconosersdk.http.saveDocumentSides.** { *; }

-keep class com.reconosersdk.reconosersdk.http.login.** { *; }

-keep class com.reconosersdk.reconosersdk.http.obtainDataEasyTrack.** { *; }

-keep class com.reconosersdk.reconosersdk.citizens.** { *; }

-keep class com.reconosersdk.reconosersdk.entities.** { *; }

-keep class com.reconosersdk.reconosersdk.http.openSource.** { *; }

-keep class com.reconosersdk.reconosersdk.utils.** { *; }

-keep class com.reconosersdk.reconosersdk.utils.custom.** { *; }

-keep class com.cetoco.** { *; }

-keep class com.reconosersdk.reconosersdk.ui.document.views.** { *; }

-keep class com.reconosersdk.reconosersdk.utils.custom.CustomNotSwipeableViewPager** { *; }

-keep class com.reconosersdk.reconosersdk.recognition.** { *; }

-keep public class * extends android.app.Activity

-keep public classcom.reconosersdk.reconosersdk.databinding** { *; }

-keepclassmembernames class com.reconosersdk.reconosersdk.databinding.FragmentQuestionRecyclerBinding.** { *; }

-dontwarn android.databinding.**

-dontwarn android.widget.LinearLayout**

-keep class com.reconosersdk.reconosersdk.DataBinderMapperImpl { *; }

##--------------------------------------------------------------------------------##

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

##-----------OCV-------------------------------------------------------------------##

-keep class org.opencv.** { *; }