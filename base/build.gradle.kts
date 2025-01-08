plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.demon.base"
    compileSdk = "${rootProject.extra["appCompileSdk"]}".toInt()

    defaultConfig {
        minSdk = "${rootProject.extra["appMinSdk"]}".toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}

dependencies {
    //api(fileTree(dir='libs', include=['*']))
    //mars-xlog
    api(project(":mars"))
    //multidex
    api(libs.appcompat)
    api(libs.material)
    api(libs.swiperefreshlayout)
    //ktx,https://developer.android.google.cn/kotlin/ktx?hl=zh_cn#use-ktx
    api(libs.core.ktx)
    api(libs.activity.ktx)
    api(libs.fragment.ktx)
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.livedata.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.viewmodel.compose)
    //WorkManager
    api(libs.work.runtime.ktx)
    //Startup
    api(libs.startup.runtime)
    //Retrofit
    api(libs.retrofit)
    api(libs.converter.scalars)
    api(libs.converter.gson)
    api(libs.kotlin.coroutines.retrofit)
    //Gson
    api(libs.gson)
    //okhttp
    api(libs.okhttp)
    api(libs.logging.interceptor)
    //Glide4.x
    api(libs.glide)
    kapt(libs.glide.compiler)
    api(libs.glide.okhttp3.integration)
    //ARouter
    implementation(libs.arouter.api)
    kapt(libs.arouter.compiler)
    //LiveEventBus
    api(libs.liveeventbus)
    //mmkv
    api(libs.mmkv)
    //QFsolution
    api(libs.qfsolution)
    //https://github.com/getActivity/ToastUtils
    api(libs.toastutils)
    //官方流式布局库
    api(libs.flexbox)
    //https://github.com/CymChad/BaseRecyclerViewAdapterHelper
    api(libs.baserecyclerviewadapterhelper)
    api(libs.recyclerview)
    //flowbinding
    api(libs.flowbinding.android)
    api(libs.flowbinding.material)
    api(libs.autodispose)
    //https://github.com/Blankj/AndroidUtilCode
    api(libs.utilcodex)

}