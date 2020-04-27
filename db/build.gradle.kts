plugins {
    kotlin("multiplatform")
//    id("com.android.library")
    id("com.squareup.sqldelight")
}
sqldelight(Action<com.squareup.sqldelight.gradle.SqlDelightExtension> {
    database("Main") {
        packageName = "com.worldsnas.db"
        sourceFolders = listOf("sqldelight")

        // The directory where to store '.db' schema files relative to the root of the project.
        // These files are used to verify that migrations yield a database with the latest schema.
        // Defaults to null so the verification tasks will not be created.
        schemaOutputDirectory = file("src/main/sqldelight/db")

    }
    linkSqlite = true
})
/**
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
 */
kotlin {

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.SqlDelight.runtime)
        implementation(Deps.Coroutines.common)
        implementation(Deps.Tools.stately)
    }

    jvm()
    sourceSets["jvmMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Coroutines.jdk)
    }

//    android()
//
//    sourceSets["androidMain"].dependencies {
//        implementation(kotlin("stdlib", Versions.kotlin))
//        implementation(Deps.SqlDelight.driverAndroid)
//        implementation(Deps.Coroutines.jdk)
//        implementation(Deps.Coroutines.android)
//    }
}
