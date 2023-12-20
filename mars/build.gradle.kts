plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = "com.tencent.mars.xlog"
    compileSdk = "${rootProject.extra["appCompileSdk"]}".toInt()

    defaultConfig {
        minSdk = "${rootProject.extra["appMinSdk"]}".toInt()
    }

    sourceSets {
        getByName("main").jniLibs.srcDirs("libs")
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {}