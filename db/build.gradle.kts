plugins {
    kotlin("jvm")
    id("java-library")
    kotlin("kapt")
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

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {
    testImplementation(Deps.Coroutines.test)
    testImplementation(Deps.Android.Test.junit)
    testImplementation(Deps.Android.Test.assertJ)
    testImplementation(Deps.SqlDelight.runtimeJdk)

    implementation(kotlin("stdlib", Versions.kotlin))
    implementation(Deps.Coroutines.jdk)
    implementation(Deps.SqlDelight.coroutines)
    implementation(Deps.SqlDelight.runtime)
    implementation(Deps.Dagger.injectAnnotation)
}
