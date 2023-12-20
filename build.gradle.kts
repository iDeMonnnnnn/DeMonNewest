buildscript {
    extra.apply {
        set("appMinSdk", 21)
        set("appCompileSdk", 34)
        set("appTargetSdk", 34)
        set("appVersionCode", 100)
        set("appVersionName", "1.0.0")
        set("appPackageName", "com.demon.demonnewest")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false
    //alias(libs.plugins.ksp) apply false
}
