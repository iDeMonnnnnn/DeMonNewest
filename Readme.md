## DeMonNewest
[![Hex.pm](https://img.shields.io/badge/Jetpack-AndroidX-orange)]()

Android前沿JetPack组件&框架&技术的学习及其使用示例。

1. 拥抱最新的Jetpack，打造一个全新的MVVM。
2. JetPack组件学习及模块化使用，并持续跟进完全使用最新版本。
3. 适合与JetPack组件结合使用的优秀框架使用示例。
4. 模块化代码，参照示例代码可以直接复制使用。
5. 多渠道等线上实用技术。

#### 组件

|组件|说明|链接|备注|
|--|--|--|--|
|KTX|Android官方Kotlin扩展库|[传送门](https://developer.android.google.cn/kotlin/ktx?hl=zh_cn#workmanager)|
|Flow|响应式编程模型|-|
|ViewBinding|视图绑定|[传送门](https://developer.android.google.cn/topic/libraries/view-binding?hl=zh-cn)|
|LiveData|数据观察|[传送门](https://developer.android.google.cn/topic/libraries/architecture/livedata?hl=zh-cn)|
|ViewModel|MVVM|[传送门](https://developer.android.google.cn/topic/libraries/architecture/viewmodel?hl=zh-cn)|
|Room|数据库|[传送门](https://developer.android.google.cn/topic/libraries/architecture/room?hl=zh-cn)|
|Pagging|分页库|[传送门](https://developer.android.google.cn/topic/libraries/architecture/paging?hl=zh-cn)|
|Hilt|依赖注入|[Android 中的依赖项注入](https://developer.android.google.cn/training/dependency-injection/hilt-android?hl=zh-cn)|
|DataStore|数据存储|[传送门](https://developer.android.google.cn/topic/libraries/architecture/datastore?hl=zh-cn)|
|WorkManager|异步任务调度|[传送门](https://developer.android.google.cn/topic/libraries/architecture/workmanager?hl=zh-cn)|
|App Startup|应用启动|[传送门](https://developer.android.google.cn/topic/libraries/app-startup?hl=zh-cn)|
|MotionLayout|运动和微件动画|[传送门](https://developer.android.google.cn/training/constraint-layout/motionlayout?hl=zh_cn)|
|CameraX|相机应用|[传送门](https://developer.android.google.cn/training/camerax?hl=zh-cn)|
|Compose|声明式UI|[传送门](https://developer.android.google.cn/jetpack/compose/tutorial?hl=zh-cn)|
|SplashScreen|启动画面|[传送门](https://developer.android.google.cn/develop/ui/views/launch/splash-screen?hl=zh-cn)|
#### 框架

|框架|说明| 链接                                                              | 备注                                                                           |
|--|--|-----------------------------------------------------------------|------------------------------------------------------------------------------|
|Glide4.x|最优秀的图片加载框架| [传送门](https://blog.csdn.net/demonliuhui/category_9926150.html)  |
|ARouter|阿里路由框架| [传送门](https://github.com/alibaba/ARouter)                       | 针对gardle8.0+ksp需要适配，见[传送门](https://github.com/JailedBird/ArouterKspCompiler) |
|LiveEventBus|美团消息总线| [传送门](https://github.com/JeremyLiao/LiveEventBus)               |
|MMKV|腾讯基于mmap的高性能通用key-value组件| [传送门](https://github.com/Tencent/MMKV/blob/master/readme_cn.md) |
|mars-xlog|腾讯高性能日志模块xlog| [传送门](https://github.com/Tencent/mars)                          |

#### 技术

|技术|说明|链接|备注|
|--|--|--|--|
|多渠道|Walle|[传送门](https://github.com/Meituan-Dianping/walle)|

#### 配置
| 技术  | 说明         |链接|备注|
|-----|------------|--|--|
| kts | kotlin构建脚本 |[传送门](https://developer.android.com/studio/build/migrate-to-kts?hl=zh-cn)|
| ksp | kotlin注解处理器 |[传送门](https://kotlinlang.org/docs/ksp-overview.html)|


#### 废弃

|技术|说明| 链接                                                               | 备注         |
|--|--|------------------------------------------------------------------|------------|
|Activity Result API|代替startActivityForResult| [传送门](https://github.com/iDeMonnnnnn/DeMon-ARA)                  | 复杂且缺陷      |
|多渠道|美团渠道包解决方案| [传送门](https://tech.meituan.com/2014/06/13/mt-apk-packaging.html) | 过时，迁移到Walle |
|ImmersionBar|沉浸式状态栏| [传送门](https://github.com/gyf-dev/ImmersionBar)                   | [Edge-to-edge](https://developer.android.google.cn/develop/ui/views/layout/edge-to-edge?hl=zh-cn)       |

#### 日志
- 2024.4.28: 支持的最高 Android API 级别为 35。
- 2023.12.20: 升级到最新构建kts&catelog
- 2022.9.29：gradle version 7.x
- 2022.8.30：```kotlin_version = '1.7.10'```,引入```compose```
- 2022.3.30：引入腾讯mars-xlog
- 2022.3.4：引入Activity Result API
- 2022.2.28：build:gradle:7.1.2
- 2021.10.21：重构主页面
- 2020.12.22：升级到```kotlin_version = '1.4.21'```，使用```ViewBinding```,移除```kotlin-android-extensions```.
- 2020.11.16：使用Hilt代替Dagger2.
