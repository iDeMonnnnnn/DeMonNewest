plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.demon.demonnewest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.demon.demonnewest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        ndk { abiFilters.addAll(listOf("arm64-v8a", "armeabi-v7a")) }
    }

    signingConfigs {
        create("release") {
            storeFile = file("../../DeMon.jks")
            storePassword = "123456"
            keyAlias = "key"
            keyPassword = "123456"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false //是否debug
            isShrinkResources = true //资源压缩
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs["release"]
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}

dependencies {
    //implementation fileTree(dir: 'libs', include: ['*'])
    implementation(project(":base"))
    //room
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.paging.runtime.ktx)
    implementation(libs.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)
    //Preferences DataStore
    implementation(libs.androidx.datastore.preferences)
    //exoplayer
    implementation(libs.exoplayer)
    //ARouter,每个module都需要添加
    implementation(libs.arouter.api)
    kapt(libs.arouter.compiler)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //Glide4.x
    annotationProcessor(libs.compiler)
    implementation(libs.glide.transformations)
    //GPU Filters
    //implementation 'jp.co.cyberagent.android:gpuimage:2.1.0'
    //微光特效
    implementation(libs.shimmer)
    //引导高亮
    implementation(libs.lighter)
    // https://github.com/gyf-dev/ImmersionBar
    implementation(libs.immersionbar)
    // fragment快速实现（可选）
    implementation(libs.immersionbar.components)
    // kotlin扩展（可选）
    implementation(libs.immersionbar.ktx)
    //https://github.com/scwang90/SmartRefreshLayout
    implementation(libs.refresh.layout.kernel)//核心必须依赖
    implementation(libs.refresh.header.material)//谷歌刷新头
    implementation(libs.refresh.footer.classics)
    //https://github.com/guolindev/PermissionX
    implementation(libs.permissionx)

}