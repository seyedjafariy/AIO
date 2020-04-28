plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    compileSdkVersion(prjectCompileSdkVersion)
    defaultConfig {
        minSdkVersion(projectMinSdkVersion)
        targetSdkVersion(projectTargetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = isReleaseMinify
            isShrinkResources = isReleaseShrinkResources
            multiDexEnabled = isReleaseMultiDex
            isDebuggable = isReleaseDebuggable
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                proguardFileAddress
            )
        }

        getByName("debug") {
//            ext.alwaysUpdateBuildId = false
            isMinifyEnabled = isDebugMinify
            isShrinkResources = isDebugShrinkResources
            multiDexEnabled = isDebugMultiDex
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                proguardFileAddress
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(kotlin("stdlib-common", Versions.kotlin))

    androidTestImplementation(Deps.Android.Test.runner)
    androidTestImplementation(Deps.Android.Test.espressoCore)
    testImplementation(Deps.Android.Test.jsonTest)
    testImplementation(Deps.Android.Networking.mockWebServer)
    testImplementation(Deps.RxJava.rxJavaRetrofit)
    testImplementation(Deps.Moshi.moshiRetrofit)
    testImplementation(Deps.Android.Networking.okHttp)
    testImplementation(Deps.Coroutines.test)

    testImplementation(Deps.Android.Test.junit)
    testImplementation(Deps.Android.Test.assertJ)
    testImplementation(Deps.Android.Test.mockkUnit)
    testImplementation(project(Deps.Modules.kotlinTestHelper))

    implementation(project(Deps.Modules.core))
    implementation(project(Deps.Modules.db))

    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.javaxAnnotation)
    implementation(Deps.Dagger.jetbrainsAnnotation)
    implementation(Deps.Dagger.findBugs)
    kapt(Deps.Dagger.daggerCompiler)

    implementation(Deps.Android.Networking.retrofit)
    implementation(Deps.Android.Networking.okHttpLogging)
    implementation(Deps.Android.Tools.timber)

    implementation(Deps.Coroutines.jdk)
    implementation(Deps.Coroutines.rxJava)
    implementation(Deps.Coroutines.android)

    implementation(Deps.ktor.androidSerialization)
}