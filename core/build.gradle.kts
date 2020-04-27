plugins {
    kotlin("multiplatform")
}

kotlin {
    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.SqlDelight.runtime)
        implementation(Deps.Coroutines.common)
    }

    jvm()
    sourceSets["jvmTest"].dependencies {
        implementation(Deps.Coroutines.test)
        implementation(Deps.Android.Test.junit)
        implementation(Deps.Android.Test.assertJ)
    }
    sourceSets["jvmMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Coroutines.jdk)
    }
}