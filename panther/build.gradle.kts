plugins {
    id("java-library")
    kotlin("jvm")
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies{
    testImplementation(Deps.Coroutines.test)
    testImplementation(Deps.Android.Test.junit)
    testImplementation(Deps.Android.Test.assertJ)

    implementation(kotlin("stdlib-common", Versions.kotlin))
    implementation(Deps.Coroutines.jdk)
    implementation(Deps.RxJava.rxJava)
    implementation(Deps.Android.Networking.retrofit)
}
