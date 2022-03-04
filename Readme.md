## DeMonJetPack

[![Hex.pm](https://img.shields.io/badge/Jetpack-AndroidX-orange)]()

拥抱最新的Jetpack，打造一个全新的MVVM

1. JetPack组件学习及模块化使用，并持续跟进完全使用最新版本。
2. 适合与JetPack组件结合使用的优秀框架使用示例。


#### 组件

|组件|说明|备注|
|--|--|--|
|KTX|Android官方Kotlin扩展库|<https://developer.android.google.cn/kotlin/ktx?hl=zh_cn#workmanager>|
|ViewBinding|视图绑定|<https://developer.android.google.cn/topic/libraries/view-binding?hl=zh-cn>|
|LiveData|数据观察|<https://developer.android.google.cn/topic/libraries/architecture/livedata?hl=zh-cn>|
|ViewModel|MVVM|<https://developer.android.google.cn/topic/libraries/architecture/viewmodel?hl=zh-cn>|
|Room|数据库|<https://developer.android.google.cn/topic/libraries/architecture/room?hl=zh-cn>|
|Pagging|分页库|<https://developer.android.google.cn/topic/libraries/architecture/paging?hl=zh-cn>|
|Hilt|依赖注入|[Jetpack新成员，一篇文章带你玩转Hilt和依赖注入](https://guolin.blog.csdn.net/article/details/109787732)|
|DataStore|数据存储|<https://developer.android.google.cn/topic/libraries/architecture/datastore?hl=zh-cn>|
|WorkManager|异步任务调度|<https://developer.android.google.cn/topic/libraries/architecture/workmanager?hl=zh-cn>|
|App Startup|应用启动|<https://developer.android.google.cn/topic/libraries/app-startup?hl=zh-cn>|
|MotionLayout|运动和微件动画|<https://developer.android.google.cn/training/constraint-layout/motionlayout?hl=zh_cn>|
|Activity Result API|startActivityForResult代替|<https://blog.csdn.net/c10WTiybQ1Ye3/article/details/119430078>|

#### 框架

|框架|说明|备注|
|--|--|--|
|ARouter|阿里路由框架|<https://github.com/alibaba/ARouter>|
|LiveEventBus|美团消息总线|<https://github.com/JeremyLiao/LiveEventBus>|
|MMKV|腾讯基于mmap的高性能通用key-value组件|<https://github.com/Tencent/MMKV/blob/master/readme_cn.md>|

####

|技术|说明|备注|
|--|--|--|
|多渠道|美团渠道包解决方案 |<https://tech.meituan.com/2014/06/13/mt-apk-packaging.html>|


#### 日志

- 2020.12.22：升级到```kotlin_version = '1.4.21'```，使用```ViewBinding```,移除```kotlin-android-extensions```.

- 2020.11.16：使用Hilt代替Dagger2.
