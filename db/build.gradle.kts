plugins {
    kotlin("multiplatform")
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
}
