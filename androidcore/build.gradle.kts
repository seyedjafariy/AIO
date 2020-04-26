plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
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

    testImplementation(Deps.Coroutines.test)
    testImplementation(Deps.Android.Test.junit)
    testImplementation(Deps.Android.Test.assertJ)

    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.javaxAnnotation)
    implementation(Deps.Dagger.jetbrainsAnnotation)
    implementation(Deps.Dagger.findBugs)
    kapt(Deps.Dagger.daggerCompiler)

    implementation(Deps.Android.Tools.conductor)
    implementation(Deps.Coroutines.jdk)
    implementation(Deps.Android.Networking.retrofit)
    implementation(Deps.Android.Networking.okHttpLogging)
    implementation(Deps.Moshi.moshiRetrofit)
    implementation(Deps.RxJava.rxJavaRetrofit)
    implementation(Deps.Moshi.moshi)
    implementation(Deps.Android.Tools.timber)
    implementation(Deps.RxJava.rxJava)
    implementation(Deps.Android.Support.recyclerView)
}