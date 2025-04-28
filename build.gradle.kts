buildscript {
    extra.apply {
        set("appMinSdk", 24)
        set("appCompileSdk", 35)
        set("appTargetSdk", 35)
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
    id("com.google.gms.google-services") version "4.4.0" apply false
    alias(libs.plugins.ksp) apply false
}
