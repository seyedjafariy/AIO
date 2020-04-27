object Versions {
    val min_sdk = 21
    val target_sdk = 29
    val compile_sdk = 29

    val kotlin = "1.3.72"
    val androidXTest = "1.1.0"
    val androidSupport = "1.0.0"
    val googleMaterial = "1.0.0-rc01"
    val constraintLayout = "2.0.0-beta4"
    val multidex = "2.0.0"
    val androidArchComponent = "2.2.0"
    val androidKotlinExt = "1.0.0"
    val retrofit = "2.6.0"
    val okHttp = "4.0.1"
    val dagger = "2.27"
    val moshi = "1.8.0"
    val kotshi = "2.0.1"
    val arrow = "0.9.0"

    val fresco = "2.0.0"
    val epoxy = "3.9.0"
    val flipper = "0.31.1"
    val chucker = "3.1.2"
    val butterKnife = "10.2.1"

    val espresso = "3.1.0-alpha3"
    val mockk = "1.9.3"
    val android_gradle_plugin = "3.6.2"
    val buildToolsVersion = "29.0.0"
    val junit = "4.12"


    val sqlDelight = "1.3.0"
    val ktor = "1.3.2"
    val stately = "1.0.2"
    val multiplatformSettings = "0.6"
    val coroutines = "1.3.5-native-mt"
    val koin = "3.0.0-alpha-9"
    val serialization = "0.20.0"
    val cocoapodsext = "0.6"

}

object Deps {


    object Android {

        val android_gradle_plugin =
            "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"

        object Support {
            val compat = "androidx.appcompat:appcompat:${Versions.androidSupport}"
            val supportLegacy = "androidx.legacy:legacy-support-v4:${Versions.androidSupport}"
            val androidxCore = "androidx.core:core:${Versions.androidSupport}"
            val design = "com.google.android.material:material:${Versions.googleMaterial}"
            val cardView = "androidx.cardview:cardview:${Versions.androidSupport}"
            val annotation = "androidx.annotation:annotation:${Versions.androidSupport}"
            val vectorDrawable = "androidx.vectordrawable:vectordrawable:${Versions.androidSupport}"
            val recyclerView = "androidx.recyclerview:recyclerview:${Versions.androidSupport}"
            val multiDex = "androidx.multidex:multidex:${Versions.multidex}"
            val constraintLayout =
                "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
            val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0-alpha04"
        }

        object Jetpack {
            val lifecycleRuntime =
                "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidArchComponent}"
            val viewModel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidArchComponent}"
            val liveData =
                "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidArchComponent}"
            val coreKts = "androidx.core:core-ktx:${Versions.androidKotlinExt}"
            val fragmentKts = "androidx.fragment:fragment-ktx:${Versions.androidKotlinExt}"
            val collectionKts = "androidx.collection:collection-ktx:${Versions.androidKotlinExt}"
        }

        object Firebase {
            val core = "com.google.firebase:firebase-core:17.0.1"
            val analytics = "com.google.firebase:firebase-analytics:17.0.1"
            val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
            val messaging = "com.google.firebase:firebase-messaging:19.0.1"
            val remoteConfig = "com.google.firebase:firebase-config:18.0.0"
        }

        object Networking {
            val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
            val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
            val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
            val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.0.1"
        }

        object Coroutines {
            val coroutinesAndroid =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
            val coroutinesRx2 =
                "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Versions.coroutines}"
        }

        object Tools {
            val timber = "com.jakewharton.timber:timber:4.7.1"
            val fresco = "com.facebook.fresco:fresco:${Versions.fresco}"
            val frescoOkHttp = "com.facebook.fresco:imagepipeline-okhttp3:${Versions.fresco}"
            val photoDraweeView = "me.relex:photodraweeview:2.0.0"
            val conductor = "com.bluelinelabs:conductor:3.0.0-rc2"
            val leakCanary = "com.squareup.leakcanary:leakcanary-android:1.6.3"
            val leakCanaryNoOp = "com.squareup.leakcanary:leakcanary-android-no-op:1.6.3"
            val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
            val epoxyCompiler = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
            val slider = "com.github.worldsnas:slider:1.0.3"
            val indicator = "com.ryanjeffreybrooks:indefinitepagerindicator:1.0.10"
            val flipper = "com.facebook.flipper:flipper:${Versions.flipper}"
            val flipperNoOp = "com.facebook.flipper:flipper-noop:${Versions.flipper}"
            val flipperNetworkPlugin =
                "com.facebook.flipper:flipper-network-plugin:${Versions.flipper}"
            val flipperFrescoPlugin =
                "com.facebook.flipper:flipper-fresco-plugin:${Versions.flipper}"
            val chucker = "com.github.ChuckerTeam.Chucker:library:${Versions.chucker}"
            val chuckerNoop = "com.github.ChuckerTeam.Chucker:library-no-op:${Versions.chucker}"
            val soLoader = "com.facebook.soloader:soloader:0.5.1"
            val stetho = "com.facebook.stetho:stetho:1.5.1"
            val nineOldAndroidAnim = "com.nineoldandroids:library:2.4.0"

            val butterKnife = "com.jakewharton:butterknife:${Versions.butterKnife}"
            val butterKnifeCompiler = "com.jakewharton:butterknife-compiler:${Versions.butterKnife}"
        }

        object Test {
            val core = "androidx.test:core:${Versions.androidXTest}"
            val junit = "androidx.test.ext:junit:${Versions.androidXTest}"
            val runner = "androidx.test:runner:${Versions.androidXTest}"
            val rules = "androidx.test:rules:${Versions.androidXTest}"
            val assertJ = "org.assertj:assertj-core:3.12.2"
            val mockkUnit = "io.mockk:mockk:${Versions.mockk}"
            val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"

            val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
            val espressoIntents =
                "androidx.test.espresso:espresso-intents:${Versions.espresso}"
            val espressoContrib =
                "androidx.test.espresso:espresso-contrib:${Versions.espresso}"


            val jsonTest = "org.json:json:20140107"

            val robolectric = "org.robolectric:robolectric:4.2"
        }
    }

    object Modules {
        val appModule = ":app"
        val kotlinTestHelper = ":kotlintesthelpers"
        val domain = ":domain"
        val panther = ":panther"
        val daggerCore = ":daggercore"
        val base = ":base"
        val core = ":core"
        val mvi = ":mvi"
        val navigation = ":navigation"
        val home = ":home"
        val movieDetail = ":moviedetail"
        val imageSliderLocal = ":slider"
        val gallery = ":gallery"
        val db = ":db"
        val androidCore = ":androidcore"
        val viewComponent = ":view-component"
    }

    object RxJava {
        val rxJava = "io.reactivex.rxjava2:rxjava:2.1.6"
        val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0-RC3"
        val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.0.1"
        val rxPreferences = "com.f2prateek.rx.preferences2:rx-preferences:2.0.0"
        val rxJavaRetrofit = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

        val rxBindingKotlin = "com.jakewharton.rxbinding3:rxbinding:3.0.0"
        val rxBindingCompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:3.0.0"
        val rxBindingMaterial = "com.jakewharton.rxbinding3:rxbinding-material:3.0.0"
    }

    object Moshi {
        val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        val moshiRetrofit = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        val kotshi = "se.ansman.kotshi:api:${Versions.kotshi}"
        val kotshiCompiler = "se.ansman.kotshi:compiler:${Versions.kotshi}"
    }

    object Dagger {
        val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        val findBugs = "com.google.code.findbugs:jsr305:3.0.2"

        val javaxAnnotation = "javax.annotation:jsr250-api:1.0"
        val injectAnnotation = "javax.inject:javax.inject:1"
        val jetbrainsAnnotation = "org.jetbrains:annotations:17.0.0"
    }

    object KotlinTest {
        val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        val jvm = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    }

    object Coroutines {
        val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutines}"
        val jdk = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        val native = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutines}"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        val rxJava = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Versions.coroutines}"
    }

    object SqlDelight {
        val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        val runtimeJdk = "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelight}"
        val driverIos = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        val coroutines = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
    }

    object ktor {
        val commonCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        val commonJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        val jvmCore = "io.ktor:ktor-client-core-jvm:${Versions.ktor}"
        val androidCore = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
        val jvmJson = "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
        val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
        val iosCore = "io.ktor:ktor-client-core-native:${Versions.ktor}"
        val iosJson = "io.ktor:ktor-client-json-native:${Versions.ktor}"
        val commonSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        val androidSerialization = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
        val iosSerialization = "io.ktor:ktor-client-serialization-native:${Versions.ktor}"
    }

    object Arrow {
        val core = "io.arrow-kt:arrow-core-data:${Versions.arrow}"
        val extensions = "io.arrow-kt:arrow-core-extensions:${Versions.arrow}"
    }

    object Tools{
        val stately = "co.touchlab:stately-common:${Versions.stately}"
        val multiplatformSettings =
            "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
        val multiplatformSettingsTest =
            "com.russhwolf:multiplatform-settings-test:${Versions.multiplatformSettings}"
        val koinCore = "org.koin:koin-core:${Versions.koin}"
        val cocoapodsext = "co.touchlab:kotlinnativecocoapods:${Versions.cocoapodsext}"
    }
}
