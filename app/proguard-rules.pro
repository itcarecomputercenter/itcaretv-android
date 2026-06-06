# ProGuard Rules for ITCareTV
-keep class com.itcaretv.app.** { *; }

# Keep WebView JS interfaces (for future use)
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Standard Android
-keepattributes *Annotation*
-dontwarn android.webkit.**
