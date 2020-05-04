plugins {
    kotlin("multiplatform")
    id("com.android.library")
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
}

kotlin {
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            }
        }
    }
    sourceSets["commonMain"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(project(Deps.Modules.domain))
        implementation(project(Deps.Modules.core))
        implementation(project(Deps.Modules.navigation))
        implementation(Deps.Coroutines.common)
    }
    android()
    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))

        implementation(Deps.Android.Support.compat)
        implementation(Deps.Android.Support.constraintLayout)
        implementation(Deps.Android.Support.design)

        implementation(Deps.Android.Jetpack.coreKts)

        implementation(Deps.Android.Tools.conductor)
        implementation(Deps.Android.Tools.timber)
        implementation(Deps.Android.Tools.fresco)

        implementation(Deps.Dagger.dagger)

        configurations.get("kapt").dependencies.add(
            implementation(
                Deps.Dagger.daggerCompiler
            )
        )

        implementation(Deps.RxJava.rxJava)
        implementation(Deps.RxJava.rxAndroid)
        implementation(Deps.RxJava.rxKotlin)
        implementation(Deps.RxJava.rxBindingKotlin)
        implementation(Deps.Coroutines.rxJava)
        implementation(Deps.Coroutines.jdk)

        implementation(project(Deps.Modules.daggerCore))
        implementation(project(Deps.Modules.base))
        implementation(project(Deps.Modules.mvi))
        implementation(project(Deps.Modules.navigation))
        implementation(project(Deps.Modules.imageSliderLocal))
        implementation(Deps.Android.Tools.nineOldAndroidAnim)
    }

    sourceSets["androidTest"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Android.Test.junit)
        implementation(Deps.Android.Test.assertJ)
    }
    jvm()
    sourceSets["jvmTest"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Android.Test.junit)
        implementation(Deps.Android.Test.assertJ)
    }
}
dependencies{
    implementation(kotlin("stdlib", Versions.kotlin))

    implementation(Deps.Android.Support.compat)
    implementation(Deps.Android.Support.constraintLayout)
    implementation(Deps.Android.Support.design)

    implementation(Deps.Android.Jetpack.coreKts)

    implementation(Deps.Android.Tools.conductor)
    implementation(Deps.Android.Tools.timber)
    implementation(Deps.Android.Tools.fresco)

    implementation(Deps.Dagger.dagger)

    configurations.get("kapt").dependencies.add(
        implementation(
            Deps.Dagger.daggerCompiler
        )
    )
//    kapt(Deps.Dagger.daggerCompiler)

    implementation(Deps.RxJava.rxJava)
    implementation(Deps.RxJava.rxAndroid)
    implementation(Deps.RxJava.rxKotlin)
    implementation(Deps.RxJava.rxBindingKotlin)
    implementation(Deps.Coroutines.rxJava)
    implementation(Deps.Coroutines.jdk)

    implementation(project(Deps.Modules.daggerCore))
    implementation(project(Deps.Modules.base))
    implementation(project(Deps.Modules.mvi))
    implementation(project(Deps.Modules.navigation))
    implementation(project(Deps.Modules.imageSliderLocal))
    implementation(Deps.Android.Tools.nineOldAndroidAnim)

}
/*
dependencies {
    testImplementation junit
    testImplementation assertJ
    testImplementation mockkUnit
    testImplementation robolectric
    testImplementation supportTestRunner
    testImplementation supportTestCore
    testImplementation supportTestRule
    testImplementation supportJunitExt
    testImplementation espressoCore

    androidTestImplementation supportTestRunner
    androidTestImplementation supportTestCore
    androidTestImplementation supportTestRule
    androidTestImplementation supportJunitExt
    androidTestImplementation espressoCore
    androidTestImplementation espressoIntents
    testImplementation jsonTest

    testImplementation(project(kotlinTestHelper))
*/