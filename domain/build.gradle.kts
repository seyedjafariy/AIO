plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    kotlin("plugin.serialization")
//    id("com.codingfeline.buildkonfig")
}
val movieDBApiKey : String by rootProject.extra
android {
    compileSdkVersion(prjectCompileSdkVersion)
    defaultConfig {
        minSdkVersion(projectMinSdkVersion)
        targetSdkVersion(projectTargetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"$movieDBApiKey\"")
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

kotlin {
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                useExperimentalAnnotation("kotlinx.serialization.ImplicitReflectionSerializer")
            }
        }
    }
}

//buildkonfig {
//    packageName = "com.worldsnas.domain"

//    defaultConfigs {
//        buildConfigField("String", "API_KEY", "\"$movieDBApiKey\"")
//    }
//}

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

    implementation(Deps.Coroutines.jdk)

    implementation(Deps.ktor.commonCore)

    implementation(Deps.ktor.Serialization.jvm)
    implementation(Deps.ktor.Json.jvm)
    implementation(Deps.ktor.Logger.jvm)
}